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

import org.jplus.hibatis.bean.OptTypeConstants;
import org.jplus.hibatis.core.executors.*;

import java.util.HashMap;
import java.util.Map;

/**
 *执行器工厂类。
 * 用户可以用此类定义自己的执行器.
 * Created by hyberbin on 2015/7/10.
 */
public class ExecutorFactory {
    public static Map<String, IExecutor> EXECUTE_MAP = new HashMap<String, IExecutor>();
    private ExecutorFactory(){

    }
    static {
        putExecutor(OptTypeConstants.SELECT,new SelectExecutor());
        putExecutor(OptTypeConstants.UPDATE, new UpdateExecutor());
        putExecutor(OptTypeConstants.EXECUTE, new ExecuteExecutor());
        putExecutor(OptTypeConstants.SAVE, new SaveExecutor());
        putExecutor(OptTypeConstants.SAVE_OR_UPDATE, new SaveOrUpdateExecutor());
        putExecutor(OptTypeConstants.GET_BY_KEY, new GetByKeyExecutor());
        putExecutor(OptTypeConstants.DELETE_BY_KEY, new DeleteByKeyExecutor());
    }

    public static IExecutor getExecutor(String type) {
        return EXECUTE_MAP.get(type.toLowerCase());
    }

    public static void putExecutor(String type,IExecutor executor) {
        EXECUTE_MAP.put(type.toLowerCase(),executor);
    }
}
