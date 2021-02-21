package mySpring.ioc.xml;

import mySpring.ioc.BeanFactory;
import mySpring.ioc.xml.reader.ConfigurationReader;
import mySpring.ioc.xml.tagEntity.Bean;
import mySpring.ioc.xml.tagEntity.Property;
import org.apache.commons.beanutils.BeanUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPathXmlApplicationContext implements BeanFactory {

    /**
     * 存放单例对象的map
     */
    private static Map<String, Object> singletonMap = new HashMap<>();

    /**
     * 用于解决循环依赖
     */
    private static Map<String, Object> earlySingletonMap = new HashMap<>();

    private static Map<String, Bean> config;

    public ClassPathXmlApplicationContext(String path) {
        config = ConfigurationReader.getBeanConfig(path);
        for (Map.Entry<String, Bean> e : config.entrySet()) {
            // 获取bean信息
            Bean bean = e.getValue();
            // 如果设置成单例的才创建好bean对象放进容器中
            createBeanByConfig(bean);
        }
    }

    public Object getBean(String name) {
        return singletonMap.get(name);
    }

    private Object createBeanByConfig(Bean bean) {
        // 如果已经单例对象已经在缓存中，则直接返回
        Object beanObj = singletonMap.get(bean.getName());
        if (beanObj != null) {
            return beanObj;
        }
        try {
            Class<?> clazz = Class.forName(bean.getClassName());
            // 创建bean对象
            beanObj = clazz.newInstance();
            // 把半成品对象放入，以备后续使用
            earlySingletonMap.put(bean.getName(), beanObj);
            // 获取bean对象中的property配置
            List<Property> properties = bean.getProperties();
            // 遍历bean对象中的property配置,并将对应的value或者ref注入到bean对象中
            for (Property property : properties) {
                Map<String, Object> params = new HashMap<>();
                if (property.getValue() != null) {
                    params.put(property.getName(), property.getValue());
                    // 将value值注入到bean对象中
                    BeanUtils.populate(beanObj, params);
                }
                if (property.getRef() != null) {
                    Object ref = singletonMap.get(property.getRef());
                    if (ref == null) {
                        // 查看半成品缓存中是否有需要的实体
                        ref = earlySingletonMap.get(property.getRef());
                        if (ref == null) {
                            ref = createBeanByConfig(config.get(property.getRef()));
                        }
                    }
                    params.put(property.getName(), ref);
                    // 将ref对象注入bean对象中
                    BeanUtils.populate(beanObj, params);
                }
            }
            earlySingletonMap.remove(bean.getName());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        singletonMap.put(bean.getName(), beanObj);
        return beanObj;
    }
}
