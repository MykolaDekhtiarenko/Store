package model;


import lombok.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class PaidSalary implements SortableByDate{
    private int id;
    private double salary;
    private Date date;
    private int employeeId;

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, y ");
        return simpleDateFormat.format(date)+"SALARY "+salary;
    }
}
