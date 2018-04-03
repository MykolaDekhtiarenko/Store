package model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class Employee {
    private int id;
    private String firstName;
    private String lastName;
    private int departmentId;
    private double salary;

    @Override
    public String toString() {
        return firstName+" "+lastName;
    }
}
