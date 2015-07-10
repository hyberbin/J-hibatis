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

import org.jplus.hibatis.bean.OptType;
import org.jplus.hibatis.core.executors.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hyberbin on 2015/7/10.
 */
public class ExecutorFactory {
    public static Map<OptType, IExecutor> EXECUTE_MAP = new HashMap<OptType, IExecutor>();

    static {
        putExecutor(OptType.SELECT,new SelectExecutor());
        putExecutor(OptType.UPDATE, new UpdateExecutor());
        putExecutor(OptType.EXECUTE, new ExecuteExecutor());
        putExecutor(OptType.SAVE, new SaveExecutor());
        putExecutor(OptType.SAVE_OR_UPDATE, new SaveOrUpdateExecutor());
        putExecutor(OptType.GET_ONE_BY_KEY, new GetOneByKeyExecutor());
        putExecutor(OptType.DELETE_BY_KEY, new DeleteByKeyExecutor());
    }

    public static IExecutor getExecutor(OptType type) {
        return EXECUTE_MAP.get(type);
    }

    public static void putExecutor(OptType type,IExecutor executor) {
        EXECUTE_MAP.put(type,executor);
    }
}
