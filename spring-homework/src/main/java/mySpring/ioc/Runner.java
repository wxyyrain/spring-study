package mySpring.ioc;

import mySpring.ioc.annotation.AnnotationApplicationContext;
import mySpring.ioc.xml.ClassPathXmlApplicationContext;
import mySpring.pojo.A;
import mySpring.pojo.B;
import mySpring.pojo.C;

public class Runner {

    public static void main(String[] args) {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("/applicationContext-ioc.xml");
        AnnotationApplicationContext context = new AnnotationApplicationContext("mySpring.pojo");
        A a = (A) context.getBean("A");
        A a1 = (A) context.getBean("A");
        B b = (B) context.getBean("B");
        C c = (C) context.getBean("C");
        System.out.println(a);
        System.out.println(b);
        System.out.println(b);
        System.out.println(a == a1);
        System.out.println(a.getB() == b);
        System.out.println(b.getC() == c);
        System.out.println(c.getA() == a);
    }
}
