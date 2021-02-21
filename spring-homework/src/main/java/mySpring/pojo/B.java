package mySpring.pojo;

import lombok.Getter;
import lombok.Setter;
import mySpring.ioc.annotation.MyAutowired;
import mySpring.ioc.annotation.MyComponent;

@Getter
@Setter
@MyComponent
public class B {

    private String name;

    @MyAutowired
    private C c;
}
