package model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class Department {
    private int id;
    private String name;

    @Override
    public String toString() {
        return name + " department";
    }
}
