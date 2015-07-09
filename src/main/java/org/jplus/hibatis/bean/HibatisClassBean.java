/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hyberbin
 */
@XmlRootElement
public class HibatisClassBean {

    /** 映射的类 */
    private Class mapperClass;
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
            for (HibatisMethodBean methodBean : methodBeans) {
                Method_Map.put(methodBean.getId(), methodBean);
            }
        }
        return Method_Map.get(id);
    }

}
