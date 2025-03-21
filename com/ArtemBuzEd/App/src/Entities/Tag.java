package Entities;

import com.ArtemBuzEd.journalApp.TagColor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Tag {
    private String id;
    private String name;
    private TagColor color;
    List<JournalEntry> journalEntryList;

    //Color change for console
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    public Tag(String name, TagColor color) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.color = color;
        journalEntryList = new ArrayList<>();
    }

    public String getName() {return name;}
    public TagColor getColor() {return color;}
    public List<JournalEntry> getJournalEntryList() {return journalEntryList;}

    public void addJournalEntry(JournalEntry journalEntry) {
        if(!journalEntryList.contains(journalEntry)) {
            journalEntryList.add(journalEntry);
        }
    }

    @Override
    public String toString() {
        String tag;
        switch (color){
            case BLUE -> tag = (ANSI_BLUE + name + ANSI_RESET);
            case RED -> tag = (ANSI_RED + name + ANSI_RESET);
            case YELLOW -> tag = (ANSI_YELLOW + name + ANSI_RESET);
            case GREEN -> tag = (ANSI_GREEN + name + ANSI_RESET);
            case PURPLE -> tag = (ANSI_PURPLE + name + ANSI_RESET);
            default -> throw new IllegalArgumentException("Unknown color");
        }
        return tag;
    }
}
