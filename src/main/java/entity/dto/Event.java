package entity.dto;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id")
    int id;

    @Column(name = "events_text")
    String text;

    public Event() {}

    public Event(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", text='" + text + '\'' +
                '}';
    }
}
