package model;

import lombok.*;

import java.sql.Date;
import java.text.SimpleDateFormat;

@Getter
@Setter
@Builder

@EqualsAndHashCode(exclude = {"id"})
public class PaidBonus implements SortableByDate{
    private int id;
    private double size;
    private Date date;
    private int employeeId;

    @Override
    public String toString() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMMM d, y ");
        return simpleDateFormat.format(date)+"BONUS "+size;
    }
}
