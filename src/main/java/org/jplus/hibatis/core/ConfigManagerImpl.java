/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.core;

import java.util.HashMap;
import java.util.Map;
import org.jplus.hibatis.bean.HibatisClassBean;

/**
 *
 * @author hyberbin
 */
public class ConfigManagerImpl implements ConfigManager {

    private static final Map<Class, HibatisClassBean> HIBATI_MAP = new HashMap<Class, HibatisClassBean>();

    @Override
    public HibatisClassBean getHibatisBean(Class clazz) {
        HibatisClassBean get = HIBATI_MAP.get(clazz);
        if (get != null) {
            return get;
        }
        throw new IllegalArgumentException("找不到类的映射，类名：" + clazz.getName());
    }

    public static void put(Class clazz, HibatisClassBean hibatisClassBean) {
        HIBATI_MAP.put(clazz, hibatisClassBean);
    }
}
