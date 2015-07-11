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
 * 如果方法后带有两个参数那么默认是第一个参数是说明键名，第二个参数是键值.
 * 用该执行器的方法必须有返回类型，不能是Object
 * Created by hyberbin on 2015/7/10.
 */
public class GetOneByKeyExecutor extends AExecutor{
    public GetOneByKeyExecutor() {
        super(OptTypeConstants.GET_ONE_BY_KEY);
    }
    @Override
    public Object execute(HibatisMethodBean methodBean, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        if(returnType.equals(Object.class)||Reflections.isSimpleType(returnType)||Map.class.isAssignableFrom(returnType)){
            throw new IllegalArgumentException("must have a PO returnType");
        }else{
            Object po= Reflections.instance(returnType.getName());
            Hyberbin hyberbin = new Hyberbin(po);
            if(args.length==1){
                Reflections.setFieldValue(po, hyberbin.getPrimaryKey(),args[0]);
                return hyberbin.showOnebyKey(hyberbin.getPrimaryKey());
            }else if(args.length==2){
                String key=args[0]+"";
                Reflections.setFieldValue(po, key,args[1]);
                return hyberbin.showOnebyKey(key);
            }
        }
        throw new IllegalArgumentException("must have 2 arguments!");
    }
}
