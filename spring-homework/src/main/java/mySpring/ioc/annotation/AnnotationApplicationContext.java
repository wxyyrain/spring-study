package mySpring.ioc.annotation;

import mySpring.ioc.BeanFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationApplicationContext implements BeanFactory {

    private  Map<String, Object> singletonMap = new HashMap<>();

    public AnnotationApplicationContext(String packageName) {
        List<Class<?>> classes = ClazzUtils.getAllClasses(packageName);
        for (Class<?> clazz : classes) {
            // 如果加了@MyComponent注解，则实例化
            Annotation annotation = clazz.getAnnotation(MyComponent.class);
            if (annotation != null) {
                createBean(clazz);
            }
        }
        // 依赖注入
        for (Class<?> clazz : classes) {
            autowired(clazz);
        }
    }


    @Override
    public Object getBean(String name) {
        return singletonMap.get(name);
    }

    /**
     * 利用反射创建实体
     *
     * @param clazz
     */
    private void createBean(Class<?> clazz) {
        String name = clazz.getSimpleName();
        Object o = singletonMap.get(name);
        if (o == null) {
            try {
                o = clazz.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }
            singletonMap.put(name, o);
        }
    }

    /**
     * 给实例化后的对象，依赖注入
     *
     * @param clazz
     */
    private void autowired(Class<?> clazz) {
        Object o = singletonMap.get(clazz.getSimpleName());
        List<Field> fields = getAutowired(clazz);
        if (fields.size() > 0) {
            for (Field field : fields) {
                field.setAccessible(true);
                Object diObject = singletonMap.get(field.getType().getSimpleName());
                if (diObject == null) {
                    throw new RuntimeException();
                }
                try {
                    field.set(o, diObject);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取全部需要注入的字段
     *
     * @param clazz
     * @return
     */
    private List<Field> getAutowired(Class<?> clazz) {
        List<Field> list = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        if (fields.length > 0) {
            for (Field field : fields) {
                MyAutowired annotation = field.getAnnotation(MyAutowired.class);
                if (annotation != null) {
                    list.add(field);
                }
            }
        }
        return list;
    }
}
