package Comparators;

import Entities.JournalEntry;

import java.util.Comparator;

public class DateComparator implements Comparator<JournalEntry> {
    @Override
    public int compare(JournalEntry o1, JournalEntry o2) {
        return o1.getWroteDate().compareTo(o2.getWroteDate());
    }
}
