package org.example.config;

import lombok.Setter;
import org.example.pojo.Klass;
import org.example.pojo.School;
import org.example.pojo.Student;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
// 当classpath下发现该类的情况下进行自动配置
@ConditionalOnClass({Klass.class, Student.class, School.class})
@Setter
public class SchoolAutoConfigure implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Bean
    // 当Spring Context中不存在该Bean时
    @ConditionalOnMissingBean
    // 当配置文件中example.service.enabled=true
    @ConditionalOnProperty(prefix = "my.service.school", value = "enabled", havingValue = "true")
    @ConditionalOnBean({Student.class, Klass.class})
    School school() {
        Object klass = applicationContext.getBean("klass");
        Object bean100 = applicationContext.getBean("student100");
        School school = new School();
        school.setClass1((Klass) klass);
        school.setStudent100((Student) bean100);
        return school;
    }

}
