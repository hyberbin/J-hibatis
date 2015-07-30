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
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author hyberbin
 */
public class HibatisMethodBean {

    /** 方法ID */
    private String id;
    /** 操作类型 */
    private String type;
    /** SQL语句 */
    private String sql;
    /** 如果返回类型是List集合则要申明集合内的元素类型 */
    private Class resultGenericType;

    private String argNames;

    public HibatisMethodBean() {
    }

    public HibatisMethodBean(String id, String type) {
        this.id = id;
        this.type = type;
    }

    @XmlAttribute(required = true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @XmlAttribute(required = true)
    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public String[] getArgNameArrays() {
        return argNames.split(",");
    }

    @XmlAttribute(required = false)
    public String getArgNames() {
        return argNames;
    }

    public void setArgNames(String argNames) {
        this.argNames = argNames;
    }
}
