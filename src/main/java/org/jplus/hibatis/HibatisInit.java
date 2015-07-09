/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
