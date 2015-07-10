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
