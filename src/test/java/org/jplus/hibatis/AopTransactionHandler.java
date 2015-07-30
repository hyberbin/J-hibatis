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
package org.jplus.hibatis;

import org.jplus.contex.aop.AopHandler;
import org.jplus.hyb.database.config.ConfigCenter;

import java.lang.reflect.Method;
import java.sql.Connection;

/**
 * Created by hyberbin on 15/7/30.
 */
public class AopTransactionHandler implements AopHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("我切入了,在这里我做了一件事,提交了事务");
        ConfigCenter.INSTANCE.getManager().commit();
        return null;
    }
}
