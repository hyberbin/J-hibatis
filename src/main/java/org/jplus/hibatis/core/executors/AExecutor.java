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

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.jplus.hibatis.bean.HibatisMethodBean;
import org.jplus.hibatis.bean.OptType;
import org.jplus.hyb.database.crud.BaseDbTool;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by hyberbin on 2015/7/10.
 */
public abstract class AExecutor implements IExecutor{
    static final Map EMPTY_MAP = new HashMap();
    private final OptType type;

    public AExecutor(OptType type) {
        this.type = type;
    }

    public final static VelocityEngine velocity = new VelocityEngine();

    static {
        Properties prop = new Properties();
        prop.put("file.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        prop.put("eventhandler.referenceinsertion.class", "org.jplus.hibatis.core.ReferenceInsertionEventHandler");
        prop.put("input.encoding", "UTF-8");
        prop.put("output.encoding", "UTF-8");
        velocity.init(prop);
    }

    public static String getSqlUseVelocity(HibatisMethodBean methodBean, Object[] args, final BaseDbTool baseDbTool) {
        //取得velocity的上下文context
        Map<String, Object> varMap = new HashMap<String, Object>();
        VelocityContext context = new VelocityContext();
        List parameters = baseDbTool.getAdapter().getParmeters();
        context.put("hibatisParameters", parameters);
        if (args != null) {
            for (int i = 0; i < args.length; i++) {
                context.put(methodBean.getArgNames()[i], args[i]);
                varMap.put(methodBean.getArgNames()[i], args[i]);
            }
        }
        context.put("p", varMap);
        StringWriter writer = new StringWriter();
        velocity.evaluate(context, writer, "", methodBean.getSql());
        return writer.toString();
    }

    public OptType getType() {
        return type;
    }
}
