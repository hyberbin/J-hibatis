/*
 * Copyright 2014 Hyberbin.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * Email:hyberbin@qq.com
 */
package org.jplus.hibatis.core;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.app.event.ReferenceInsertionEventHandler;
import org.apache.velocity.context.Context;
import org.jplus.annotation.Hibatis;
import org.jplus.hibatis.bean.HibatisClassBean;
import org.jplus.hibatis.bean.HibatisMethodBean;
import org.jplus.hibatis.bean.OptType;
import org.jplus.hyb.database.config.ConfigCenter;
import org.jplus.hyb.database.crud.BaseDbTool;
import org.jplus.hyb.database.crud.DatabaseAccess;
import org.jplus.hyb.database.crud.Hyberbin;
import org.jplus.util.ObjectHelper;
import org.jplus.util.Reflections;

import java.io.StringWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * @author hyberbin
 */
public class HibatisProxy implements InvocationHandler,ReferenceInsertionEventHandler {

    private static final Map<Class, ConfigManager> CONFIG_MANAGER = new HashMap<Class, ConfigManager>();
    private static final Map EMPTY_MAP = new HashMap();

    private HibatisClassBean hibatisBean;
    protected final static VelocityEngine velocity = new VelocityEngine();

    static {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        prop.put("eventhandler.referenceinsertion.class", "org.jplus.hibatis.core.HibatisProxy");
        prop.put("eventhandler.eventHandlerMethodExecutor.class", "org.jplus.hibatis.core.HibatisProxy");
        prop.put("input.encoding", "UTF-8");
        prop.put("output.encoding", "UTF-8");
        velocity.init(prop);
    }

    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param clazz
     * @return
     */
    public Object bind(Class clazz) {
        if (clazz.isAnnotationPresent(Hibatis.class)) {
            Hibatis annotation = (Hibatis) clazz.getAnnotation(Hibatis.class);
            ConfigManager configManager = getConfigManager(annotation.configManager());
            hibatisBean = configManager.getHibatisBean(clazz);
        } else {
            throw new IllegalArgumentException("不能代理非Hibatis对象");
        }
        return Proxy.newProxyInstance(HibatisProxy.class.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        HibatisMethodBean methodBean = hibatisBean.getMethodBean(method.getName());
        if (methodBean.getType().equals(OptType.SELECT)) {//查询操作
            if (Collection.class.isAssignableFrom(method.getReturnType())) {
                if (methodBean.getResultGenericType() == null) {//没有泛型说明是Map
                    Hyberbin hyberbin = new Hyberbin();
                    return hyberbin.getMapList(methodBean.getSql());
                } else {//指定泛型类型
                    Hyberbin hyberbin = new Hyberbin(Reflections.instance(methodBean.getResultGenericType().getName()));
                    return hyberbin.showList(getSqlUseVelocity(methodBean, args, hyberbin));
                }
            } else if (Map.class.isAssignableFrom(method.getReturnType())) {
                Hyberbin hyberbin = new Hyberbin();
                List mapList = hyberbin.getMapList(getSqlUseVelocity(methodBean, args, hyberbin));
                return mapList.isEmpty() ? EMPTY_MAP : mapList.get(0);
            } else if (Reflections.isSimpleType(method.getReturnType())) {//如果是简单类型
                DatabaseAccess databaseAccess = new DatabaseAccess(ConfigCenter.INSTANCE.getManager());
                return databaseAccess.queryUnique(getSqlUseVelocity(methodBean, args, databaseAccess));
            } else {
                Hyberbin hyberbin = new Hyberbin(Reflections.instance(method.getReturnType().getName()));
                return hyberbin.showOne(getSqlUseVelocity(methodBean, args, hyberbin));
            }
        } else if (methodBean.getType().equals(OptType.UPDATE)) {
            DatabaseAccess databaseAccess = new DatabaseAccess(ConfigCenter.INSTANCE.getManager());
            return databaseAccess.update(getSqlUseVelocity(methodBean, args, databaseAccess));
        } else if (methodBean.getType().equals(OptType.EXECUTE)) {
            DatabaseAccess databaseAccess = new DatabaseAccess(ConfigCenter.INSTANCE.getManager());
            return databaseAccess.update(getSqlUseVelocity(methodBean, args, databaseAccess));
        }else if (methodBean.getType().equals(OptType.SAVE)) {
            Object po=args[0];
            Hyberbin hyberbin = new Hyberbin(po);
            Object id = Reflections.getFieldValue(po, hyberbin.getPrimaryKey());
            return ObjectHelper.isEmpty(id)?hyberbin.insert(hyberbin.getPrimaryKey()):hyberbin.insert("");
        }else if (methodBean.getType().equals(OptType.SAVE_OR_UPDATE)) {
            Object po=args[0];
            Hyberbin hyberbin = new Hyberbin(po);
            Object id = Reflections.getFieldValue(po, hyberbin.getPrimaryKey());
            return ObjectHelper.isEmpty(id)?hyberbin.insert(hyberbin.getPrimaryKey()):hyberbin.updateByKey(hyberbin.getPrimaryKey());
        }
        return null;
    }

    public String getSqlUseVelocity(HibatisMethodBean methodBean, Object[] args, final BaseDbTool baseDbTool) {
        //取得velocity的上下文context
        Map<String,Object> varMap=new HashMap<String, Object>();
        VelocityContext context = new VelocityContext();
        List parameters = baseDbTool.getAdapter().getParmeters();
        context.put("hibatisParameters",parameters);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                context.put(methodBean.getArgNames()[i],args[i]);
                varMap.put(methodBean.getArgNames()[i],args[i]);
            }
        }
        context.put("p", varMap);
        StringWriter writer = new StringWriter();
        velocity.evaluate(context, writer, "", methodBean.getSql());
        return writer.toString();
    }

    private ConfigManager getConfigManager(Class clazz) {
        ConfigManager get = CONFIG_MANAGER.get(clazz);
        if (get == null) {
            get = (ConfigManager) Reflections.instance(clazz.getName());
            CONFIG_MANAGER.put(clazz, get);
        }
        return get;
    }

    @Override
    public Object referenceInsert(Context context, String key, Object o) {
        if(key.startsWith("$p.")){
            List hibatisParameters = (List)context.get("hibatisParameters");
            hibatisParameters.add(o);
            return "?";
        }
        return o;
    }
}
