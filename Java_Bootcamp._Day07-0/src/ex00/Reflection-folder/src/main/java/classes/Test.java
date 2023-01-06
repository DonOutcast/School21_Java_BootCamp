package classes;

public class Test {
    private Integer f1;
    private Double f2;
    private Long f3;
    private String f4;
    private Boolean f5;

    public Test() {
    }

    public Test(Integer f1, Double f2, Long f3, String f4, Boolean f5) {
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
    }

    public Double m1(Double p1, Integer p2) {
        return p1*p2;
    }

    public Boolean m2(Boolean p1) {
        return !p1;
    }

    public Boolean m2(Integer p1) {
        return p1 * -1 > 0;
    }

    public void m3() {
        System.out.println("HAHAHAHHAHA");
    }

    @Override
    public String toString() {
        return "Test{" +
                "f1=" + f1 +
                ", f2=" + f2 +
                ", f3=" + f3 +
                ", f4='" + f4 + '\'' +
                ", f5=" + f5 +
                '}';
    }
}
