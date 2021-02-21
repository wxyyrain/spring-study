package mySpring.ioc.xml.tagEntity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Bean {

    private String name;
    private String className;
    private List<Property> properties = new ArrayList<Property>();
}
