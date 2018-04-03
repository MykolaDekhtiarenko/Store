package utils;

import model.SortableByDate;

import java.sql.Date;
import java.util.Comparator;

public class DateComparator implements Comparator<SortableByDate> {
    @Override
    public int compare(SortableByDate o1, SortableByDate o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
