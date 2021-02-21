package org.example.config;

import org.example.pojo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 当classpath下发现该类的情况下进行自动配置
@ConditionalOnClass(Student.class)
@EnableConfigurationProperties({Student100Properties.class, Student123Properties.class})
public class StudentAutoConfigure {

    @Autowired
    private Student123Properties properties123;

    @Autowired
    private Student100Properties properties100;

    @Bean("student123")
    // 当Spring Context中不存在该Bean时
    @ConditionalOnMissingBean(name = {"student123"})
    // 当配置文件中example.service.enabled=true
    @ConditionalOnProperty(prefix = "my.service.student123", value = "enabled", havingValue = "true")
    Student student123() {
        return new Student(properties123.getId(), properties123.getName());
    }

    @Bean("student100")
    // 当Spring Context中不存在该Bean时
    @ConditionalOnMissingBean(name = {"student100"})
    // 当配置文件中example.service.enabled=true
    @ConditionalOnProperty(prefix = "my.service.student100", value = "enabled", havingValue = "true")
    Student student100() {
        return new Student(properties100.getId(), properties100.getName());
    }
}
