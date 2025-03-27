package Entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JournalEntry {
    @JsonProperty("journal_id")
    private String id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("content")
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("wrote_date")
    private final LocalDate wroteDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty("wrote_time")
    private final LocalTime wroteTime;

    @JsonProperty("tags")
    List<Tag> tags;

    public JournalEntry() {
        this.id = UUID.randomUUID().toString();
        this.wroteDate = LocalDate.now();
        this.wroteTime = LocalTime.now();
        this.tags = new ArrayList<>();
    }

    public JournalEntry(String title, String content){
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        wroteDate = LocalDate.now();
        wroteTime = LocalTime.now();
        tags = new ArrayList<>();

    }

    //getters
    @JsonProperty("journal_id")
    public String getId() {return id;}
    @JsonProperty("title")
    public String getTitle() {return title;}
    @JsonProperty("content")
    public String getContent() {return content;}
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("wrote_date")
    public LocalDate getWroteDate() {return wroteDate;}
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonProperty("wrote_time")
    public LocalTime getWroteTime() {return wroteTime;}
    @JsonProperty("tags")
    public List<Tag> getTags() {return tags;}

    //setters
    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
    public void setTags(List<Tag> tags) {this.tags.addAll(tags);}

    public void addTag(Tag tag){
        if(!tags.contains(tag)){
            tags.add(tag);
        }
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
    }
}
