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

/**
 * 基础的dao接口.
 * 继承这个接口的dao中不必再在配置文件中申明方法.
 * Created by hyberbin on 2015/7/10.
 */
@Hibatis
public interface BaseDao<T> {
    void save(T object);

    void saveOrUpdate(T object);

    void deleteByKey(T po);

    void deleteByKey(T po,String key);

    void getByKey(T po);

    void getByKey(T po,String key);
}
