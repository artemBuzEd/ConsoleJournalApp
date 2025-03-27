package Entities;

import com.ArtemBuzEd.journalApp.TagColor;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class User {
    @JsonProperty("user_id")
    private String id;

    @JsonProperty("user_name")
    private String name;

    @JsonProperty("user_birthYear")
    private int birthYear;

    @JsonProperty("user_journals")
    private ArrayList<JournalEntry> journalList;

    private Map<String, Tag> tagMap;

    public User() {
        this.id = UUID.randomUUID().toString();
        journalList = new ArrayList<>();
    }

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
    public JournalEntry getJournalEntryByIndex(int index) {return journalList.get(index);}

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
