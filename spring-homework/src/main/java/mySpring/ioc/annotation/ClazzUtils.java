package mySpring.ioc.annotation;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClazzUtils {

    public static List<Class<?>> getAllClasses(String packageName) {
        List<String> result = new ArrayList<>();
        String suffixPath = packageName.replaceAll("\\.", "/");
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try {
            Enumeration<URL> urls = loader.getResources(suffixPath);
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    String protocol = url.getProtocol();
                    if ("file".equals(protocol)) {
                        getAllClassesNames(new File(url.getPath()), result);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.stream().map(className -> {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                return null;
            }
        }).collect(Collectors.toList());
    }

    private static void getAllClassesNames(File file, List<String> list) {
        if (!file.exists()) {
            return;
        }
        if (file.isDirectory()) {
            for (File listFile : Objects.requireNonNull(file.listFiles())) {
                getAllClassesNames(listFile, list);
            }
        } else {
            list.add(file.getPath().split("classes.")[1].replace("\\", ".").replace(".class", ""));
        }
    }
}
