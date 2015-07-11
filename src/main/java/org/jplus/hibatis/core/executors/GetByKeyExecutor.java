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
import org.jplus.hyb.database.crud.Hyberbin;
import org.jplus.util.Reflections;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * 根据一个键获取一个对象.
 * 如果方法后只带有一个参数那么默认按照主键获取.
 * 如果方法后带有两个参数那么默认是第一个参数是申明对象，第二个参数是键.
 * 用该执行器的方法的第一个参数必须是PO。
 * Created by hyberbin on 2015/7/10.
 */
public class GetByKeyExecutor extends AExecutor{
    public GetByKeyExecutor() {
        super(OptTypeConstants.GET_BY_KEY);
    }
    @Override
    public Object execute(HibatisMethodBean methodBean, Method method, Object[] args) throws Throwable {
        Object pojo= args[0];
        Class<?> po = args[0].getClass();
        if(po.equals(Object.class)||Reflections.isSimpleType(po)||Map.class.isAssignableFrom(po)){
            throw new IllegalArgumentException("1st arg must be a PO!");
        }else{
            Hyberbin hyberbin = new Hyberbin(pojo);
            if(args.length==1){
                return hyberbin.showOnebyKey(hyberbin.getPrimaryKey());
            }else if(args.length==2){
                return hyberbin.showOnebyKey(args[1]+"");
            }
        }
        throw new IllegalArgumentException("must have 2 arguments!");
    }
}
