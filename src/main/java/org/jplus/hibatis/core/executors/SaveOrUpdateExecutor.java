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
import org.jplus.util.ObjectHelper;
import org.jplus.util.Reflections;

import java.lang.reflect.Method;

/**
 * 保存或更新的执行器.
 * PO中必须申明主键.
 * Created by hyberbin on 2015/7/10.
 */
public class SaveOrUpdateExecutor extends AExecutor{
    public SaveOrUpdateExecutor() {
        super(OptTypeConstants.SAVE_OR_UPDATE);
    }
    @Override
    public Object execute(HibatisMethodBean methodBean, Method method, Object[] args) throws Throwable {
        Object po=args[0];
        Hyberbin hyberbin = new Hyberbin(po);
        Object id = Reflections.getFieldValue(po, hyberbin.getPrimaryKey());
        return ObjectHelper.isEmpty(id)?hyberbin.insert(hyberbin.getPrimaryKey()):hyberbin.updateByKey(hyberbin.getPrimaryKey());
    }
}
