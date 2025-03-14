import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class JournalEntry {
    private String id;
    private String title;
    private String content;
    private final LocalDate wroteDate;
    private final LocalTime wroteTime;

    List<Tag> tags;

    public JournalEntry(String title, String content){
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.content = content;
        wroteDate = LocalDate.now();
        wroteTime = LocalTime.now();
        tags = new ArrayList<>();

    }

    //getters
    public String getId() {return id;}
    public String getTitle() {return title;}
    public String getContent() {return content;}
    public LocalDate getWroteDate() {return wroteDate;}
    public LocalTime getWroteTime() {return wroteTime;}
    public List<Tag> getTags() {return tags;}

    //setters
    public void setTitle(String title) {this.title = title;}
    public void setContent(String content) {this.content = content;}
    public void setTags(List<Tag> tags) {this.tags.addAll(tags);}

    public void addTag(Tag tag){
        if(!tags.contains(tag)){
            tags.add(tag);
            tag.addJournalEntry(this);
        }
    }

    public void removeTag(Tag tag){
        tags.remove(tag);
    }
}
