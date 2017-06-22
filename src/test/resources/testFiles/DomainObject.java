package testFiles;

import java.time.DayOfWeek;

public class DomainObject {
    private long id;
    private int age;
    private String name;
    private DayOfWeek day;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public DayOfWeek getDay() {
        return this.day;
    }
}