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
import org.jplus.hibatis.bean.HibatisClassBean;
import org.jplus.hibatis.bean.HibatisMethodBean;
import org.jplus.util.Reflections;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author hyberbin
 */
public class HibatisProxy implements InvocationHandler {

    private static final Map<Class, ConfigManager> CONFIG_MANAGER = new HashMap<Class, ConfigManager>();

    private HibatisClassBean hibatisBean;

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
        return ExecutorFactory.getExecutor(methodBean.getType()).execute(methodBean,method,args);
    }

    private ConfigManager getConfigManager(Class clazz) {
        ConfigManager get = CONFIG_MANAGER.get(clazz);
        if (get == null) {
            get = (ConfigManager) Reflections.instance(clazz.getName());
            CONFIG_MANAGER.put(clazz, get);
        }
        return get;
    }
}
