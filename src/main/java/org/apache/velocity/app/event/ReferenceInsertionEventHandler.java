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
package org.apache.velocity.app.event;

import org.apache.velocity.context.Context;
import org.apache.velocity.util.ContextAware;

public interface ReferenceInsertionEventHandler extends EventHandler {

    Object referenceInsert(Context context,String var1, Object var2);

    public static class referenceInsertExecutor implements EventHandlerMethodExecutor {
        private Context context;
        private String reference;
        private Object value;

        referenceInsertExecutor(Context context, String reference, Object value) {
            this.context = context;
            this.reference = reference;
            this.value = value;
        }

        public void execute(EventHandler handler) {
            ReferenceInsertionEventHandler eh = (ReferenceInsertionEventHandler)handler;
            if(eh instanceof ContextAware) {
                ((ContextAware)eh).setContext(this.context);
            }
            this.value = ((ReferenceInsertionEventHandler)handler).referenceInsert(this.context,this.reference, this.value);
        }

        public Object getReturnValue() {
            return this.value;
        }

        public boolean isDone() {
            return false;
        }
    }
}
