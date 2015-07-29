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

import org.jplus.contex.core.ObjectContext;
import org.jplus.hibatis.scanner.HibatisXmlScanHandler;
import org.jplus.scanner.ScannerImpl;
import org.jplus.scanner.ScannerInitializer;

/**
 * 初始化扫描器的类.
 * 默认只扫描路径中包含hibatis并且以xml结尾的文件.
 * @author hyberbin
 */
public class HibatisInit {

    public static void init() {
        init(false, ".*", ".*hibatis.*xml");
    }

    public static void init(boolean needScanJar, String scanJarRegex, String scanClassPathRegex) {
        ScannerImpl.INSTANCE.addScanHandler(new HibatisXmlScanHandler(new ScannerInitializer(needScanJar, scanJarRegex, scanClassPathRegex)));
        ObjectContext.CONTEXT.init();
    }
}
