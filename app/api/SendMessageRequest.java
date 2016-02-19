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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SendMessageRequest request = (SendMessageRequest) o;

        if (!recipient.equals(request.recipient)) return false;
        return text.equals(request.text);

    }

    @Override
    public int hashCode() {
        int result = recipient.hashCode();
        result = 31 * result + text.hashCode();
        return result;
    }
}
