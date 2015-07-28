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
package org.jplus.hibatis.core.executors;

import org.jplus.hibatis.bean.HibatisMethodBean;
import org.jplus.hibatis.bean.OptTypeConstants;
import org.jplus.hyb.database.config.ConfigCenter;
import org.jplus.hyb.database.crud.DatabaseAccess;
import org.jplus.hyb.database.crud.Hyberbin;
import org.jplus.util.Reflections;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 查询执行器.
 * Created by hyberbin on 2015/7/10.
 */
public class SelectExecutor extends AExecutor {
    public SelectExecutor() {
        super(OptTypeConstants.SELECT);
    }

    @Override
    public Object execute(HibatisMethodBean methodBean, Method method, Object[] args) throws Throwable {
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
    }
}
