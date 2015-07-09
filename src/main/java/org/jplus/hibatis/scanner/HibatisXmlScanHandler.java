/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.scanner;

import org.jplus.contex.core.ObjectContex;
import org.jplus.hibatis.HibatisInit;
import org.jplus.hibatis.bean.HibatisClassBean;
import org.jplus.hibatis.core.ConfigManagerImpl;
import org.jplus.hibatis.core.HibatisProxy;
import org.jplus.hyb.log.Logger;
import org.jplus.hyb.log.LoggerManager;
import org.jplus.scanner.IScanHandler;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 *
 * @author hyberbin
 */
public class HibatisXmlScanHandler implements IScanHandler {

    private static final Logger log = LoggerManager.getLogger(HibatisXmlScanHandler.class);
    @Override
    public boolean filterJar(String path) {
        return HibatisInit.getBooleanProperty(VAR_SCAN_JAR)&&path.matches(HibatisInit.getProperty(VAR_SCAN_JAR_REGEX));
    }

    @Override
    public boolean filterPath(String path) {
        return path.matches(HibatisInit.getProperty(VAR_SCAN_CLASSPATH_REGEX));
    }

    @Override
    public void dealWith(InputStream is) throws Exception {
        JAXBContext context = JAXBContext.newInstance(HibatisClassBean.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        HibatisClassBean unmarshal = (HibatisClassBean) unmarshaller.unmarshal(is);
        ConfigManagerImpl.put(unmarshal.getMapperClass(), unmarshal);
        ObjectContex.CONTEX.setService(unmarshal.getMapperClass(), new HibatisProxy().bind(unmarshal.getMapperClass()));
        log.debug("load hibatis xml .mapperClassName:{}", unmarshal.getMapperClass().getName());
    }
}
