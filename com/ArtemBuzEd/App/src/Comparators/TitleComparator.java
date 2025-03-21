package Comparators;

import Entities.JournalEntry;

import java.util.Comparator;

public class TitleComparator implements Comparator<JournalEntry> {
    @Override
    public int compare(JournalEntry o1, JournalEntry o2) {
        return o1.getTitle().compareTo(o2.getTitle());
    }
}
