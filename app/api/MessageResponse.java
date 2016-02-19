package api;

import dao.models.Message;
import org.joda.time.DateTime;

public class MessageResponse {
    private Long id;
    private String recipient;
    private DateTime sent;
    private String text;

    public static MessageResponse constructFromMessage(Message message) {
        if(message == null) {
            return null;
        }
        return new MessageResponse(message);
    }

    private MessageResponse(Message message) {
        this.id = message.getId();
        this.recipient = message.getRecipient();
        this.sent = message.getSent();
        this.text = message.getText();
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageResponse that = (MessageResponse) o;

        return id.equals(that.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
