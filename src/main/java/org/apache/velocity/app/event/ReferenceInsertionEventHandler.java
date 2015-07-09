//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

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
