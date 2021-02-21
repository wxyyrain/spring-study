package mySpring.pojo;

import lombok.Getter;
import lombok.Setter;
import mySpring.ioc.annotation.MyAutowired;
import mySpring.ioc.annotation.MyComponent;

@Getter
@Setter
@MyComponent
public class A {

    private String name;

    @MyAutowired
    private B b;
}
