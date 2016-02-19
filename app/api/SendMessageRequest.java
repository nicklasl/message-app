package api;


import javax.validation.constraints.Size;

public class SendMessageRequest {

    @Size(min = 0, max = 255)
    private String recipient;
    @Size(min = 0, max = 255)
    private String text;

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
