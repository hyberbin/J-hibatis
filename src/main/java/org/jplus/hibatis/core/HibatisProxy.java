/*
 * Copyright 2015 www.hyberbin.com.
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

import org.jplus.annotation.Hibatis;
import org.jplus.annotation.HibatisMethod;
import org.jplus.hibatis.bean.HibatisClassBean;
import org.jplus.hibatis.bean.HibatisMethodBean;
import org.jplus.hibatis.core.executors.IExecutor;
import org.jplus.util.ObjectHelper;
import org.jplus.util.Reflections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * 代理Hibatis的类，所有的Hibatis接口都由此类代理.
 * @author hyberbin
 */
public class HibatisProxy implements InvocationHandler {

    private static final Map<Class, ConfigManager> CONFIG_MANAGER = new HashMap<Class, ConfigManager>();
    private static final Map<Method, HibatisMethodBean> HIBATIS_METHOD_MANAGER = new HashMap<Method, HibatisMethodBean>();
    private Class target;
    /**
     * 绑定委托对象并返回一个代理类
     *
     * @param clazz
     * @return
     */
    public Object bind(Class clazz) {
        if (!clazz.isAnnotationPresent(Hibatis.class)) {
            throw new IllegalArgumentException("不能代理非Hibatis对象");
        }
        this.target=clazz;
        return Proxy.newProxyInstance(HibatisProxy.class.getClassLoader(), new Class[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> declaringClass = method.getDeclaringClass();
        if (declaringClass.equals(Object.class)) {
            if(method.getName().equals("getClass")){
                return target;
            }
            return method.invoke(this,args);
        } else if (!declaringClass.isAnnotationPresent(Hibatis.class)) {
            return method.invoke(proxy,args);
        }
        HibatisMethodBean methodBean=getHibatisMethodBean(method);
        return ExecutorFactory.getExecutor(methodBean.getType()).execute(methodBean,method,args);
    }

    /**
     * 获取Hibatis的配置管理器.
     * @param clazz
     * @return
     */
    private ConfigManager getConfigManager(Class clazz) {
        ConfigManager get = CONFIG_MANAGER.get(clazz);
        if (get == null) {
            get = (ConfigManager) Reflections.instance(clazz.getName());
            CONFIG_MANAGER.put(clazz, get);
        }
        return get;
    }

    /**
     * 获取一个Hibatis方法的配置信息.
     * @param method
     * @return
     */
    private HibatisMethodBean getHibatisMethodBean(Method method) {
        HibatisMethodBean methodBean = HIBATIS_METHOD_MANAGER.get(method);
        if (methodBean == null) {
            Class<?> declaringClass = method.getDeclaringClass();
            Hibatis annotation = declaringClass.getAnnotation(Hibatis.class);
            ConfigManager configManager = getConfigManager(annotation.configManager());
            HibatisClassBean hibatisBean = configManager.getHibatisBean(declaringClass);
            String methodID = method.getName();//默认methodID就是方法名
            if (method.isAnnotationPresent(HibatisMethod.class)) {
                HibatisMethod hibatisMethod = method.getAnnotation(HibatisMethod.class);
                if (!ObjectHelper.isNullOrEmptyString(methodID)) {
                    methodID = hibatisMethod.methodID();//如果方法上带有HibatisMethod注解则以注解中的ID为准
                }
            }
            methodBean = hibatisBean.getMethodBean(methodID);
            if (methodBean == null) {
                throw new IllegalArgumentException("can't find xml for class:" + declaringClass.getName() + " method:" + method.getName());
            }
            HIBATIS_METHOD_MANAGER.put(method, methodBean);
        }
        return methodBean;
    }
}
