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
package org.jplus.hibatis.scanner;

import org.jplus.contex.core.ObjectContext;
import org.jplus.hibatis.bean.HibatisClassBean;
import org.jplus.hibatis.core.ConfigManagerImpl;
import org.jplus.hibatis.core.HibatisProxy;
import org.jplus.hyb.log.Logger;
import org.jplus.hyb.log.LoggerManager;
import org.jplus.scanner.AScannerHandler;
import org.jplus.scanner.ScannerInitializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.InputStream;

/**
 * Hibatis配置文件扫描器.
 * @author hyberbin
 */
public class HibatisXmlScanHandler extends AScannerHandler {

    private static final Logger log = LoggerManager.getLogger(HibatisXmlScanHandler.class);

    public HibatisXmlScanHandler(ScannerInitializer scannerInitializer) {
        super(scannerInitializer);
    }

    /**
     * 用JAXB技术将XML文件转换成HibatisClassBean.
     * 转换完成后将实例放入ObjectContext中.
     * @param is
     * @throws Exception
     */
    @Override
    public void dealWith(InputStream is) throws Exception {
        JAXBContext context = JAXBContext.newInstance(HibatisClassBean.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        HibatisClassBean unmarshal = (HibatisClassBean) unmarshaller.unmarshal(is);
        ConfigManagerImpl.put(unmarshal.getMapperClass(), unmarshal);
        ObjectContext.CONTEXT.setService(unmarshal.getMapperClass(), new HibatisProxy().bind(unmarshal.getMapperClass()));
        log.debug("load hibatis xml .mapperClassName:{}", unmarshal.getMapperClass().getName());
    }
}
