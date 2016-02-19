package service;

import api.SendMessageRequest;
import dao.MessageDAO;
import dao.models.Message;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;
import java.util.stream.Collectors;

@Named
public class MessageService {

    @Inject
    public MessageService(MessageDAO messageDAO) {
        this.messageDAO = messageDAO;
    }

    private MessageDAO messageDAO;

    public Message store(SendMessageRequest sendMessageRequest) {
        Message message = new Message();
        message.setRecipient(sendMessageRequest.getRecipient());
        message.setText(sendMessageRequest.getText());
        message.setSent(DateTime.now());
        return messageDAO.store(message);
    }

    public List<Message> retrieve(final String recipient, int start, int stop) {
        final List<Message> messages = messageDAO.listAll(recipient, start, stop);
        markAsViewed(messages);
        return messages;
    }

    public List<Message> retrieveNew(String recipient) {
        final List<Message> messages = messageDAO.listNew(recipient);
        markAsViewed(messages);
        return messages;
    }

    private void markAsViewed(List<Message> messages) {
        final List<Long> messageIds = messages.stream().mapToLong(Message::getId).boxed().collect(Collectors.toList());
        messageDAO.markAsViewed(messageIds);
    }

    public void remove(List<String> ids) {
        final List<Long> longs = ids.stream().mapToLong(Long::valueOf).boxed().collect(Collectors.toList());
        messageDAO.remove(longs);
    }
}
