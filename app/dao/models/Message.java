package dao.models;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;

@Entity
@Table(name = "message")
public class Message {

    public static final String ID = "id";
    public static final String RECIPIENT = "recipient";
    public static final String VIEWED = "viewed";
    public static final String SENT = "sent";
    public static final String TEXT = "text";

    @Id
    @Column(name = ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = RECIPIENT, nullable = false)
    private String recipient;
    @Column(name = VIEWED)
    private boolean viewed;
    @Column(name = SENT, nullable = false)
    @Type(type="org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    private DateTime sent;
    @Column(name = TEXT, nullable = false)
    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public boolean isViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public DateTime getSent() {
        return sent;
    }

    public void setSent(DateTime sent) {
        this.sent = sent;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
