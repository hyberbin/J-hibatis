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
package org.jplus.hibatis.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Hibatis对象的配置信息.
 * 一般是从配置文件读取的该配置.
 * @author hyberbin
 */
@XmlRootElement
public class HibatisClassBean {

    /** 映射的类 */
    private Class mapperClass;
    /**类中的方法*/
    private List<HibatisMethodBean> methodBeans;
    private final Map<String, HibatisMethodBean> Method_Map = new HashMap<String, HibatisMethodBean>();

    @XmlAttribute(required = true)
    public Class getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(Class mapperClass) {
        this.mapperClass = mapperClass;
    }

    public List<HibatisMethodBean> getMethodBeans() {
        return methodBeans;
    }

    public void setMethodBeans(List<HibatisMethodBean> methodBeans) {
        this.methodBeans = methodBeans;
    }

    public HibatisMethodBean getMethodBean(String id) {
        if(Method_Map.isEmpty()){
            //加载从配置文件获取的方法
            for (HibatisMethodBean methodBean : methodBeans) {
                Method_Map.put(methodBean.getId(), methodBean);
            }
        }
        return Method_Map.get(id);
    }

}
