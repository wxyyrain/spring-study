package org.example.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("my.service.student100")
@Setter
@Getter
public class Student100Properties {

    private int id;
    private String name;
}
