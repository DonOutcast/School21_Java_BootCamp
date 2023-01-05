package edu.school21.reflection.app;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    private static Class clasS;
    private static Object object;
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> packages = Arrays.asList(
            "edu.school21.reflection.models",
            "edu.school21.reflection.asset"
    );
    private static final Set<Class> allClasses = new HashSet<>();
    private static List<Field> fields;
    private static List<Method> methods;

    public static void main(String[] args) {
        try {
            while(true) {
                packages.forEach(s -> allClasses.addAll(Utils.findAllClasses(s)));
                System.out.println("Classes:");
                allClasses.forEach(c -> System.out.println(Utils.getClassName(c)));
                defineClass();
                defineFieldsAndMethods();
                createObject();
                changeField();
                callMethod();
                if(scanner.nextLine().equals("exit"))
                    break;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static void defineClass() {
        while (true) {
            System.out.println("-----------------");
            System.out.println("Enter class name:");
            String str = scanner.nextLine().trim();

            List<Class> list = allClasses.stream()
                    .filter(c -> Utils.getClassName(c).equals(str))
                    .collect(Collectors.toList());

            if (list.isEmpty()) {
                System.out.println("Invalid class name for: " + str);
            } else {
                clasS = list.get(0);
                break;
            }
        }
    }

    private static void defineFieldsAndMethods() {
        System.out.println("-----------------");
        fields = Arrays.asList(clasS.getDeclaredFields());
        methods = Arrays.stream(clasS.getMethods())
                .filter(m -> m.toString().contains(Utils.getPackageName(clasS)))
                .filter(m -> !m.toString().contains("toString()"))
                .collect(Collectors.toList());

        System.out.println("fields: ");
        fields.forEach(f -> System.out.println("\t" + Utils.getFieldName(f)));
        System.out.println("methods: ");
        methods.forEach(m -> System.out.println("\t" + Utils.getMethodSignature(m)));
    }

    private static void createObject() throws InvocationTargetException,
            InstantiationException, IllegalAccessException {
        List<Object> parameters = new ArrayList<>();
        System.out.println("-----------------");
        System.out.println("Let's create an object.");
        Constructor constructor = Arrays.stream(clasS.getConstructors()).
                filter(c -> (c.getParameterCount() > 0))
                .collect(Collectors.toList()).get(0);

        for (Field field : fields) {
            addParameters(field.getName() + ":", field.getType(), parameters);
        }
        object = constructor.newInstance(parameters.toArray());
        System.out.println("Object created: " + object);
    }

    private static void changeField() throws IllegalAccessException {
        while (true) {
            System.out.println("-----------------");
            System.out.println("Enter name of the field for changing:");
            String name = scanner.nextLine().trim();
            List<Field> list = fields.stream()
                    .filter(f -> name.equalsIgnoreCase(f.getName()))
                    .collect(Collectors.toList());

            if (list.isEmpty()) {
                System.out.println("Field not found: " + name);
            } else {
                Field field = list.get(0);
                field.setAccessible(true);
                System.out.println("Enter " + Utils.getFieldTypeName(field) + " value:");
                String value = scanner.nextLine().trim();
                String str = field.getType().getName();

                if (str.contains("String")) {
                    field.set(object, value);
                } else if (str.contains("int") || str.contains("Integer")) {
                    field.set(object, Integer.parseInt(value));
                } else if (str.contains("boolean") || str.contains("Boolean")) {
                    field.set(object, Boolean.parseBoolean(value));
                } else if (str.contains("double") || str.contains("Double")) {
                    field.set(object, Double.parseDouble(value));
                } else if (str.contains("long") || str.contains("Long")) {
                    field.set(object, Long.parseLong(value));
                } else {
                    System.out.println("UNKNOWN PARAMETER TYPE");
                    System.exit(-1);
                }
                System.out.println("Object updated: " + object);
                break;
            }
        }
    }

    private static void callMethod() throws InvocationTargetException, IllegalAccessException {
        while (true) {
            List<Object> parameters = new ArrayList<>();
            System.out.println("-----------------");
            System.out.println("Enter name of the method for call:");
            String name = scanner.nextLine().trim();
            List<Method> list = methods.stream()
                    .filter(m -> name.equalsIgnoreCase(Utils.getMethodName(m)))
                    .collect(Collectors.toList());

            if (list.isEmpty()) {
                System.out.println("Method not found: " + name);
            } else {
                Method method = list.get(0);

                if (!method.isAccessible())
                    method.setAccessible(true);

                for (Class param : method.getParameterTypes()) {
                    addParameters("Enter " + Utils.getValue(param.getName()) + " value:",
                            param, parameters);
                }
                returnedValue(method, method.invoke(object, parameters.toArray()));
                break;
            }
        }
    }

    private static void addParameters(String title, Class param, List<Object> parameters) {
        while (true) {
            System.out.println(title);
            String value = scanner.nextLine().trim();

            try {
                if (param.getName().contains("String")) {
                    parameters.add(value);
                } else if (param.getName().contains("int") || param.getName().contains("Integer")) {
                    parameters.add(Integer.parseInt(value));
                } else if (param.getName().contains("boolean") || param.getName().contains("Boolean")) {
                    parameters.add(Boolean.parseBoolean(value));
                } else if (param.getName().contains("double") || param.getName().contains("Double")) {
                    parameters.add(Double.parseDouble(value));
                } else if (param.getName().contains("long") || param.getName().contains("Long")) {
                    parameters.add(Long.parseLong(value));
                } else {
                    System.out.println("UNKNOWN PARAMETER TYPE");
                    System.exit(-1);
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid value: " + e.getMessage());
            }
        }
    }

    private static void returnedValue(Method method, Object obj) {
        if (!method.getReturnType().getName().equalsIgnoreCase("void")) {
            System.out.println("Method returned: ");
            System.out.println(obj);
        }
    }
}

