
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

public class Program {
	private static List<String> classes;
	private static Class chosenClass;
	private static Field[] fields;
	private static Method[] methods;
	private static Object instance;

	private static void parseParams(Field field, Scanner scan) {
		try {
			if (field.getType().getSimpleName().equals("Integer")) {
				Integer tmp = scan.nextInt();
				field.set(instance, tmp);
			} else if (field.getType().getSimpleName().equals("String")) {
				String tmp = scan.next();
				field.set(instance, tmp);
			} else if (field.getType().getSimpleName().equals("Long")) {
				Long tmp = scan.nextLong();
				field.set(instance, tmp);
			} else  if (field.getType().getSimpleName().equals("Double")) {
				Double tmp = scan.nextDouble();
				field.set(instance, tmp);
			} else if (field.getType().getSimpleName().equals("Boolean")) {
				Boolean tmp = scan.nextBoolean();
				field.set(instance, tmp);
			} else {
				System.err.println("No such field!");
				System.exit(-1);
			}
		} catch (IllegalAccessException e) {}
	}

	private static Set<Class<? extends Object>> scanClasses() {
		Reflections reflections = new Reflections("classes", new SubTypesScanner(false));
		Set<Class<? extends Object>> allClasses =
				reflections.getSubTypesOf(Object.class);
		classes = new LinkedList<>();
		System.out.println("Classes:");
		for (Class<? extends Object> tmp : allClasses) {
			classes.add(tmp.getName());
			System.out.println(tmp.getSimpleName());
		}
		System.out.println("----------------------------");
		return allClasses;
	}

	private static String chooseClass(Scanner scan) {
		System.out.println("Enter class name:");
		if (!scan.hasNext()) {
			System.err.println("Illegal argument!");
			System.exit(-1);
		}
		String value = scan.next();
		System.out.println("----------------------------");
		return value;
	}

	private static String getMethod(Method method) {
		String res =  String.format("%s %s", method.getReturnType().getSimpleName(),
				method.getName());
		res += "(";
		Class<?>[] parametersTypes = method.getParameterTypes();
		for (int i = 0; i < parametersTypes.length; i++) {
			if (i == parametersTypes.length - 1) {
				res += parametersTypes[i].getSimpleName();
			} else {
				res += parametersTypes[i].getSimpleName() + ", ";
			}
		}
		res += ")";
		return res;
	}

	private static void printInfoAboutClass(Set<Class<? extends Object>> allClasses, String value) {
		for (Class<? extends Object> tmp : allClasses) {
			if (tmp.getSimpleName().equals(value)) {
				chosenClass = tmp;
				System.out.println("fields:");
				fields = tmp.getDeclaredFields();
				for (Field field : fields) {
					System.out.printf("\t%s %s\n", field.getType().getSimpleName(), field.getName());
				}
				System.out.println("methods:");
				methods = tmp.getDeclaredMethods();
				for (Method method : methods) {
					System.out.printf("\t%s\n", getMethod(method));
				}
				break;
			}
		}
		System.out.println("----------------------------");
	}

	private static void createInstance(Scanner scan) {
		System.out.println("Let's create an object.");
		try {
			instance = Class.forName(chosenClass.getName()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | NullPointerException e) {
			System.err.println("No such class!");
			System.exit(-1);
		}
		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			System.out.println(fields[i].getName());
			if (!scan.hasNext()) {
				System.err.println("Illegal argument!");
				System.exit(-1);
			}
			parseParams(fields[i], scan);
		}
		System.out.printf("Object created: %s\n", instance);
		System.out.println("----------------------------");
	}

	private static void fillInstanceFields(Scanner scan) {
		System.out.println("Enter name of the field for changing:");
		if (!scan.hasNext()) {
			System.err.println("Illegal argument!");
			System.exit(-1);
		}
		String tmp = scan.next();
		for (Field field : fields) {
			if (field.getName().equals(tmp)) {
				System.out.printf("Enter %s value\n", field.getType().getSimpleName());
				if (!scan.hasNext()) {
					System.err.println("Illegal argument!");
					System.exit(-1);
				}
				parseParams(field, scan);
				System.out.printf("Object updated; %s\n", instance);
				break;
			}
		}
		System.out.println("----------------------------");
	}

	private static void callChosenMethod(Scanner scan) {
		System.out.println("Enter name of the method for call:");
		if (!scan.hasNext()) {
			System.err.println("Illegal argument!");
			System.exit(-1);
		}
		scan.nextLine();
		String tmp1 = scan.nextLine();
		for (Method method : methods) {
//			System.out.println(getMethod(method).split(" ", 1)[0]);
			if (getMethod(method).split(" ", 2)[1].equals(tmp1)) {
				Class<?>[] parametersTypes = method.getParameterTypes();
				if (parametersTypes.length > 0) {
					Object[] params = new Object[parametersTypes.length];
					for (int i = 0; i < parametersTypes.length; i++) {
						System.out.printf("Enter %s value:\n", parametersTypes[i].getSimpleName());
						if (!scan.hasNext()) {
							System.err.println("Illegal argument!");
							System.exit(-1);
						}
						if (parametersTypes[i].getSimpleName().equals("Integer")) {
							params[i] = scan.nextInt();
						} else if (parametersTypes[i].getSimpleName().equals("String")) {
							params[i] = scan.next();
						} else if (parametersTypes[i].getSimpleName().equals("Long")) {
							params[i] = scan.nextLong();
						} else  if (parametersTypes[i].getSimpleName().equals("Double")) {
							params[i] = scan.nextDouble();
						} else if (parametersTypes[i].getSimpleName().equals("Boolean")) {
							params[i] = scan.nextBoolean();
						} else {
							System.err.println("No such parameter!");
							System.exit(-1);
						}
					}
					try {
						System.out.println("Method returned:");
						System.out.println(method.invoke(instance, params));
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				} else {
					try {
						System.out.println("Method returned:");
						System.out.println(method.invoke(instance));
					} catch (IllegalAccessException | InvocationTargetException e) {
						e.printStackTrace();
					}
				}
				break;
			}
		}
	}

	public static void main(String[] args) {
		Set<Class<? extends Object>> allClasses = scanClasses();
		Scanner scan = new Scanner(System.in);
		String value = chooseClass(scan);
		printInfoAboutClass(allClasses, value);
		createInstance(scan);
		fillInstanceFields(scan);
		callChosenMethod(scan);
		scan.close();
	}
}