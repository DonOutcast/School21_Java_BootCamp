package edu.school21.reflection.app;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class Utils {
    private static FileSystem system = null;

    public static Set<Class> findAllClasses(String packageName) {
        Set<Class> classes = null;
        Path path;

        try {
            URI uri = Utils.class.getClassLoader()
                    .getResource(packageName.replaceAll("[.]", "/")).toURI();

            if (uri.getScheme().equals("jar")) {
                if (system == null) {
                    system = FileSystems.newFileSystem(uri, Collections.emptyMap());
                }
                path = system.getPath(packageName.replaceAll("[.]", "/"));
            } else {
                path = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(path, 1);
            classes = walk.filter(s -> s.toString().endsWith(".class")).map(p -> {
                try {
                    String s = p.toString();
                    s = s.substring(s.lastIndexOf('/') + 1, s.lastIndexOf('.'));
                    return Class.forName(packageName + "." + s);
                } catch (ClassNotFoundException e) {
                    System.out.println("ClassNotFoundException: " + e.getMessage());
                }
                return null;
            }).filter(Objects::nonNull).collect(Collectors.toSet());
            walk.close();
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
        }
        return Optional.ofNullable(classes).orElse(new HashSet<>());
    }

    public static String getClassName(Class clazz) {
        return getValue(clazz.getName());
    }

    public static String getPackageName(Class clazz) {
        String name = clazz.getName();
        name = name.substring(0, name.lastIndexOf('.'));
        return name;
    }

    public static String getFieldName(Field field) {
        String str = field.getType().getName();
        return getValue(str) + " " + field.getName();
    }

    public static String getFieldTypeName(Field field) {
        String str = field.getType().getName();
        return getValue(str);
    }

    public static String getMethodSignature(Method method) {
        String rType = method.getReturnType().getName();
        return getValue(rType) + " " + getMethodName(method);
    }

    public static String getMethodName(Method method) {
        String type = method.toString().substring(0, method.toString().indexOf('(') + 1);
        type = getValue(type);

        Class[] params = method.getParameterTypes();
        for (int i = 0; i < params.length; i++) {
            type += getValue(params[i].getName());

            if (i + 1 < params.length) {
                type += ", ";
            }
        }
        type += ")";
        return type;
    }

    public static String getValue(String str) {
        if (str.contains(".")) {
            str = str.substring(str.lastIndexOf('.') + 1);
        }
        return str;
    }
}
