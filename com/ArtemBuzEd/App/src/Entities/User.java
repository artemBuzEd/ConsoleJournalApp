package Entities;

import com.ArtemBuzEd.journalApp.TagColor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class User {
    private String id;
    private String name;
    private int birthYear;
    private ArrayList<JournalEntry> journalList;
    private Map<String, Tag> tagMap;

    public User(String name, int birthYear) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.birthYear = birthYear;
        journalList = new ArrayList<>();
        tagMap = new HashMap<>();
    }
    public String getName() {return name;}
    public int getBirthYear() {return birthYear;}
    public ArrayList<JournalEntry> getJournalList() {return journalList;}

    public void addJournalEntry(JournalEntry journalEntry) {
        journalList.add(journalEntry);
    }

    public void createTag(String tagName, TagColor color) {
        Tag tag = new Tag(tagName, color);
        tagMap.put(tagName, tag);
    }

    public Tag getTag(String tagName) {
        return tagMap.get(tagName);
    }

    public boolean checkTag(String tagName) {
        return tagMap.containsKey(tagName);
    }

    public boolean removeTag(String tagName) {
        if(checkTag(tagName)){
            tagMap.remove(tagName);
            return true;
        }
        return false;
    }
}
