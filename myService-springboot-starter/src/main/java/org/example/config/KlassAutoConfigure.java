package org.example.config;

import lombok.Setter;
import org.example.pojo.Klass;
import org.example.pojo.Student;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
// 当classpath下发现该类的情况下进行自动配置
@ConditionalOnClass({Klass.class, Student.class})
@Setter
public class KlassAutoConfigure implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    // 当Spring Context中不存在该Bean时
    @ConditionalOnMissingBean
    @ConditionalOnProperty(prefix = "my.service.klass", value = "enabled", havingValue = "true")
    Klass klass() {
        Object bean123 = applicationContext.getBean("student123");
        Object bean100 = applicationContext.getBean("student100");
        Klass klass = new Klass();
        klass.setStudents(new ArrayList<>());
        klass.getStudents().add((Student) bean100);
        klass.getStudents().add((Student) bean123);
        return klass;
    }

}
