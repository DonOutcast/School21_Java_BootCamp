public class UserIdsGenerator {
    private static UserIdsGenerator Object;
    private static Integer Identifier = 0;

    public static UserIdsGenerator getInstance() {
        if(Object == null) {
            Object = new UserIdsGenerator();
        }
        return Object;
    }

    public int generateId() {
        return ++Identifier;
    }

    public static Integer getIdentifier() {
        return Identifier;
    }
}