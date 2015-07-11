### J-hibatis
---
#### POJO定义
```java
package org.jplus.hibatis.beans;

import javax.persistence.Id;

/**
 *
 * @author hyberbin
 */
public class Properties {
@Id
    private String key;
    private String value;

    public Properties() {
    }

    public Properties(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

```
#### 接口定义

```java
package org.jplus.hibatis.dao;

import org.jplus.annotation.Hibatis;
import org.jplus.hibatis.beans.Properties;
import org.jplus.hibatis.core.BaseDao;

import java.util.List;
import java.util.Map;
/**
 *
 * @author hyberbin
 */
@Hibatis
public interface PropertiesDao extends BaseDao<Properties>{

    List<Properties> getAllPropertis();

    Properties getProperty(Map key);

    Properties getPropertyByKey(String key);
}
```

#### mapper配置文件
sql语句用velocity模板引擎生成，支持动态SQL语句和条件判断
如果要用预处理参数必需用$.p.  获取

```xml
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<hibatisClassBean mapperClass="org.jplus.hibatis.dao.PropertiesDao">
    <methodBeans type="SELECT" resultGenericType="org.jplus.hibatis.beans.Properties" id="getAllPropertis">
        <sql>select * from Properties</sql>
    </methodBeans>
    <methodBeans type="SELECT"  id="getProperty" argNames="map">
        <sql>select * from Properties where key=$p.map.key</sql>
    </methodBeans>
    <methodBeans type="SELECT"  id="getPropertyByKey" argNames="key">
        <sql>select * from Properties where key=$p.key</sql>
    </methodBeans>
</hibatisClassBean>
```

#### 测试类

```java
package org.jplus.hibatis;

import org.jplus.contex.core.ObjectContext;
import org.jplus.hibatis.beans.Properties;
import org.jplus.hibatis.dao.PropertiesDao;
import org.jplus.hyb.database.config.ConfigCenter;
import org.jplus.hyb.database.config.DbConfig;
import org.jplus.hyb.database.config.SimpleConfigurator;
import org.jplus.hyb.database.sqlite.SqliteUtil;
import org.jplus.hyb.database.transaction.SimpleManager;
import org.jplus.hyb.log.LocalLogger;
import org.junit.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author hyberbin
 */
public class PropertiesDaoTest {
    private static PropertiesDao propertiesDao;
    public PropertiesDaoTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        SimpleConfigurator.addConfigurator(new DbConfig(DbConfig.DRIVER_SQLITE, "jdbc:sqlite:data.db", "", "", DbConfig.DEFAULT_CONFIG_NAME));
        ConfigCenter.INSTANCE.setManager(new SimpleManager(DbConfig.DEFAULT_CONFIG_NAME));
        LocalLogger.setLevel(LocalLogger.INFO);
        HibatisInit.init();
        propertiesDao = ObjectContext.CONTEXT.getResource(PropertiesDao.class);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        tearDown();
        SqliteUtil.setProperty("name", "hyb");
        SqliteUtil.setProperty("age", "8");
    }
    
    @After
    public void tearDown() {
        SqliteUtil.clearProperties();
    }

    /**
     * Test of getAllPropertis method, of class PropertiesDao.
     */
    @Test
    public void testGetAllPropertis() {
        System.out.println("getAllPropertis");
        List<Properties> result = propertiesDao.getAllPropertis();
        assertEquals(result.size(), 2);
    }

    /**
     * Test of getProperty method, of class PropertiesDao.
     */
    @Test
    public void testGetProperty() {
        System.out.println("getProperty");
        Map map = new HashMap();
        map.put("key","name");
        Properties property = propertiesDao.getProperty(map);
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");
    }

    /**
     * Test of getPropertyByKey method, of class PropertiesDao.
     */
    @Test
    public void testGetPropertyByKey() {
        System.out.println("getPropertyByKey");
        String key = "name";
        Properties property = propertiesDao.getPropertyByKey(key);
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");
    }
    @Test
    public void testSave(){
        System.out.println("testSave");
        Properties property = new Properties("site","http://www.hyberbin.com");
        propertiesDao.save(property);
        Properties site = propertiesDao.getPropertyByKey("site");
        assertEquals(site.getKey(), "site");
        assertEquals(site.getValue(), "http://www.hyberbin.com");
    }

    @Test
    public void testSaveOrUpdate(){
        System.out.println("testSaveOrUpdate");
        Properties property = new Properties("site","http://www.hyberbin.com");
        propertiesDao.save(property);
        Properties update = new Properties("site","www.hyberbin.com");
        propertiesDao.saveOrUpdate(update);
        Properties site = propertiesDao.getPropertyByKey("site");
        assertEquals(site.getKey(), "site");
        assertEquals(site.getValue(), "www.hyberbin.com");
    }

    @Test
    public void testGetByKey(){
        System.out.println("testGetByKey");
        Properties property =new Properties ("name",null);
        propertiesDao.getByKey(property);
        assertEquals(property.getKey(), "name");
        assertEquals(property.getValue(), "hyb");

        Properties property2 =new Properties (null,"hyb");
        propertiesDao.getByKey(property2,"value");
        assertEquals(property2.getKey(), "name");
        assertEquals(property2.getValue(), "hyb");
    }

    @Test
    public void testDeleteByKey(){
        System.out.println("testDeleteByKey");
        Properties property =new Properties ("name",null);
        propertiesDao.deleteByKey(property);
        List<Properties> allPropertis = propertiesDao.getAllPropertis();
        assertEquals(allPropertis.size(), 1);

        Properties property2 =new Properties (null,"8");
        propertiesDao.deleteByKey(property2, "value");
        List<Properties> allPropertis2 = propertiesDao.getAllPropertis();
        assertEquals(allPropertis2.size(),0);
    }
}
```
