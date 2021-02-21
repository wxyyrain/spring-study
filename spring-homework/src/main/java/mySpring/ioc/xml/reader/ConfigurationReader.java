package mySpring.ioc.xml.reader;

import mySpring.ioc.xml.tagEntity.Bean;
import mySpring.ioc.xml.tagEntity.Property;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigurationReader {

    public static Map<String, Bean> getBeanConfig(String path) {
        // 存放配置信息，返回结果
        Map<String, Bean> result = new HashMap<String, Bean>();
        // 创建解析器
        SAXReader reader = new SAXReader();
        // 加载配置文件，不以/开头时默认是此类所在包下面取资源，以/开头则是从classpath下获取
        InputStream is = ConfigurationReader.class.getResourceAsStream(path);
        Document doc;
        try {
            doc = reader.read(is);
        } catch (DocumentException e) {
            throw new RuntimeException("加载配置文件出错");
        }
        List<Element> beanNodes = doc.selectNodes("//bean");
        // 遍历所有bean节点,并将信息封装到Bean对象中
        for (Element ele : beanNodes) {
            Bean bean = new Bean();
            bean.setName(ele.attributeValue("name"));
            bean.setClassName(ele.attributeValue("class"));
            // 获取bean节点下所有的property节点
            List<Element> propNodes = ele.elements("property");
            if (propNodes != null) {
                for (Element prop : propNodes) {
                    Property p = new Property();
                    p.setName(prop.attributeValue("name"));
                    p.setValue(prop.attributeValue("value"));
                    p.setRef(prop.attributeValue("ref"));
                    // 将property添加到所属bean中
                    bean.getProperties().add(p);
                }
            }
            result.put(bean.getName(), bean);
        }
        return result;
    }
}
