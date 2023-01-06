package orm;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.reflections.Reflections;

import javax.lang.model.element.Element;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrmManager {
    private Connection connection;
    private String tableName;

    public OrmManager(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        Reflections reflections = new Reflections("models");
        Set<Class<?>> elements = reflections.getTypesAnnotatedWith(OrmEntity.class);
        List<String> classes = new ArrayList<>();
        for (Class element : elements) {
            classes.add(element.getCanonicalName());
        }
        for (String clazz : classes) {
            try {
                Class<?> tmp = Class.forName(clazz);
                OrmEntity entity = tmp.getAnnotation(OrmEntity.class);
                this.tableName = entity.table();
                Statement stm = this.connection.createStatement();
                String query = String.format("DROP TABLE IF EXISTS %s CASCADE;", this.tableName);
                stm.execute(query);
                System.out.println(query);
                Field[] fields = tmp.getDeclaredFields();
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(String.format("CREATE TABLE IF NOT EXISTS %s (", this.tableName ));
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].isAnnotationPresent(OrmColumnId.class)) {
                        stringBuilder.append(String.format("%s SERIAL PRIMARY KEY", fields[i].getName()));
                    }
                    if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
                        stringBuilder.append(String.format("%s ", column.name()));
                        if (fields[i].getType().getSimpleName().equals("String")) {
                            stringBuilder.append(String.format("VARCHAR(%d)", column.length()));
                        } else if (fields[i].getType().getSimpleName().equals("Integer")) {
                            stringBuilder.append("INTEGER");
                        } else if (fields[i].getType().getSimpleName().equals("Long")) {
                            stringBuilder.append("BIGINT");
                        } else if (fields[i].getType().getSimpleName().equals("Boolean")) {
                            stringBuilder.append("BOOLEAN");
                        }
                    }
                    if (i < fields.length - 1) {
                        stringBuilder.append(",");
                    }
                }
                stringBuilder.append(");");
                stm.execute(stringBuilder.toString());
                System.out.println(stringBuilder);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void save(Object entity) {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("INSERT INTO %s (", this.tableName));
        for (int i = 1; i < fields.length; i++) {
            OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
            stringBuilder.append(column.name());
            if (i != fields.length - 1) {
                stringBuilder.append(", ");
            }
        }
        stringBuilder.append(") VALUES(");
        for (int i = 1; i < fields.length; i++) {
            fields[i].setAccessible(true);
            try {
                Object obj = fields[i].get(entity);
                if (fields[i].getType().getSimpleName().equals("String")) {
                    stringBuilder.append(String.format("'%s'", obj));
                } else {
                    stringBuilder.append(String.format("'%s'", obj));
                }
                if (i != fields.length - 1) {
                    stringBuilder.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fields[i].setAccessible(false);
        }
        stringBuilder.append(");");
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(stringBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder);
    }

    public void update(Object entity) {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not annotated!");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("UPDATE %s SET ", this.tableName));
        for(int i = 1; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
                stringBuilder.append(String.format("%s = ", column.name()));
            }
            fields[i].setAccessible(true);
            try {
                Object obj = fields[i].get(entity);
                if (fields[i].getType().getSimpleName().equals("String")) {
                    stringBuilder.append(String.format("'%s'", obj));
                } else {
                    stringBuilder.append(String.format("'%s'", obj));
                }
                if (i != fields.length - 1) {
                    stringBuilder.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            fields[i].setAccessible(false);
        }
        fields[0].setAccessible(true);
        try {
            Object obj = fields[0].get(entity);
            OrmColumnId column = fields[0].getAnnotation(OrmColumnId.class);
            stringBuilder.append(String.format(" WHERE %s = %s;", column.name(), obj));
            fields[0].setAccessible(false);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = this.connection.createStatement();
            statement.execute(stringBuilder.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(stringBuilder);
    }

    public <T> T findById(Long id, Class<T> clazz) {
        if (!clazz.isAnnotationPresent(OrmEntity.class)) {
            return null;
        }
        Field[] fields = clazz.getDeclaredFields();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("SELECT * FROM %s WHERE id = %d", this.tableName, id));
        T object = null;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(stringBuilder.toString());
            System.out.println(stringBuilder);
            if (!resultSet.next()) {
                System.err.println("No such entity!");
                return null;
            }
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].isAnnotationPresent(OrmColumnId.class)) {
                    fields[i].set(object, resultSet.getInt(1));
                } else if (fields[i].isAnnotationPresent(OrmColumn.class)) {
                    OrmColumn column = fields[i].getAnnotation(OrmColumn.class);
                    fields[i].set(object, resultSet.getObject(column.name()));
                }
            }
        } catch (SQLException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;
    }
}
