/*
 * Copyright 2014 Hyberbin.
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

import org.jplus.contex.core.ObjectContex;
import org.jplus.hibatis.scanner.HibatisXmlScanHandler;
import org.jplus.scanner.ContexClassScanHandler;
import org.jplus.scanner.IScanHandler;
import org.jplus.scanner.ScannerImpl;

import java.util.Properties;

/**
 * @author hyberbin
 */
public class HibatisInit {

    private static final Properties config = new Properties();


    static {
        setProperties(IScanHandler.VAR_SCAN_JAR, "false");
        setProperties(IScanHandler.VAR_SCAN_JAR_REGEX, ".*hibatis.*.\\.xml");
        setProperties(IScanHandler.VAR_SCAN_CLASSPATH_REGEX, ".*hibatis.*.\\.xml");
    }

    public static void init() {
        ScannerImpl.INSTANCE.addScanHandler(new HibatisXmlScanHandler());
        ScannerImpl.INSTANCE.addScanHandler(new ContexClassScanHandler());
        ObjectContex.init();
    }

    public static void setProperties(String key, String value) {
        config.setProperty(key, value);
    }

    public static boolean getBooleanProperty(String key) {
        return Boolean.valueOf(config.getProperty(key));
    }

    public static String getProperty(String key) {
        return config.getProperty(key);
    }
}
