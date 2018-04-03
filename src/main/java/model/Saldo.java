package model;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"id"})

public class Saldo {
    private int amount;
    private Date date;

    @Override
    public String toString() {
        return "amount - " + amount + ", date - " + date.toString();
    }
}
