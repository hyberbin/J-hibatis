### J-hibatis
---

#### 接口定义

```java
@Hibatis
public interface PropertiesDao {

    List<Properties> getAllPropertis();

    Properties getProperty(Map key);

    Properties getPropertyByKey(String key);

    void save(Properties properties);

    void saveOrUpdate(Properties properties);
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
    <methodBeans type="SAVE"  id="save" />
    <methodBeans type="SAVE_OR_UPDATE"  id="saveOrUpdate" />
</hibatisClassBean>
```

#### 测试类

```java
public class Test {

    public static void main(String[] args) {
        SimpleConfigurator.addConfigurator(new DbConfig(DbConfig.DRIVER_SQLITE, "jdbc:sqlite:data.db", "", "", DbConfig.DEFAULT_CONFIG_NAME));
        ConfigCenter.INSTANCE.setManager(new SimpleManager(DbConfig.DEFAULT_CONFIG_NAME));
        SqliteUtil.setProperty("name", "hyb");
        LocalLogger.setLevel(LocalLogger.INFO);
        HibatisInit.init();
        PropertiesDao propertiesDao = ObjectContex.CONTEX.getResource(PropertiesDao.class);
        List<Properties> allPropertis = propertiesDao.getAllPropertis();
        for(Properties properties:allPropertis){
            System.out.println(properties.getKey()+":"+properties.getValue());
        }
        Map map=new HashMap();
        map.put("key", "name");
        Properties property = propertiesDao.getProperty(map);
        System.out.println(property.getKey()+":"+property.getValue());
        Properties properties=new Properties("age","8");
        propertiesDao.save(properties);
        Properties age = propertiesDao.getPropertyByKey("age");
        System.out.println(age.getKey()+":"+age.getValue());
    }
}
```
