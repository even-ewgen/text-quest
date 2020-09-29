package entities;

import javax.persistence.*;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @Column(name = "id")
    int id;

    @Column(name = "events_text")
    String text;
}
