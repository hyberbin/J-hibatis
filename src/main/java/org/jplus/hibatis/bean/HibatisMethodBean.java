/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jplus.hibatis.bean;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author hyberbin
 */
public class HibatisMethodBean {

    /** 方法ID */
    private String id;
    /** 操作类型 */
    private OptType type;
    /** SQL语句 */
    private String sql;
    /** 如果返回类型是List集合则要申明集合内的元素类型 */
    private Class resultGenericType;

    private String[] argNames;

    @XmlAttribute(required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(required = true)
    public OptType getType() {
        return type;
    }

    public void setType(OptType type) {
        this.type = type;
    }

    @XmlElement(required = false)
    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    @XmlAttribute(required = false)
    public Class getResultGenericType() {
        return resultGenericType;
    }

    public void setResultGenericType(Class resultGenericType) {
        this.resultGenericType = resultGenericType;
    }
    @XmlAttribute(required = false)
    public String[] getArgNames() {
        return argNames;
    }

    public void setArgNames(String[] argNames) {
        this.argNames = argNames;
    }
}
