package dao;

import com.google.inject.ImplementedBy;
import dao.models.Message;

import java.util.List;

@ImplementedBy(MessageDAOImpl.class)
public interface MessageDAO {

    Message store(Message message);

    void markAsViewed(List<Long> messageIds);

    void remove(List<Long> messageIds);

    List<Message> listAll(String recipient, int start, int stop);

    List<Message> listNew(String recipient);
}
