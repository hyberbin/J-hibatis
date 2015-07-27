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

import org.apache.velocity.context.Context;

import java.util.List;

/**
 * velocity解析变量的监听器.
 * 如果变量名是以$p.开头的就会将该参数进行预处理.
 * Created by hyberbin on 2015/7/10.
 */
public class ReferenceInsertionEventHandler implements org.apache.velocity.app.event.ReferenceInsertionEventHandler{
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
