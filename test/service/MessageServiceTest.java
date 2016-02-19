package service;

import api.SendMessageRequest;
import dao.MessageDAO;
import dao.models.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;

import java.util.Arrays;

public class MessageServiceTest {

    private static final String RECIPIENT = "user";
    private static final String MSG = "message";

    private MessageService service;
    private MessageDAO mock;

    @Before
    public void setup() {
        mock = Mockito.mock(MessageDAO.class);
        service = new MessageService(mock);
    }

    @Test
    public void testStore() {
        SendMessageRequest request = new SendMessageRequest();
        request.setRecipient(RECIPIENT);
        request.setText(MSG);
        service.store(request);
        Mockito.verify(mock).store(Matchers.any(Message.class));
    }

    @Test
    public void testThatMarkAsReadWasDoneWhenRetrieving() {
        service.retrieve(RECIPIENT, 0, 10);
        Mockito.verify(mock).markAsViewed(Matchers.anyList());
    }

    @Test
    public void testThatMarkAsReadWasDoneWhenRetrievingNew() {
        service.retrieveNew(RECIPIENT);
        Mockito.verify(mock).markAsViewed(Matchers.anyList());
    }

    @Test
    public void testThatRemoveIsCalledWithCorrectParams() {
        service.remove(Arrays.asList("1", "2", "3"));
        Mockito.verify(mock).remove(Arrays.asList(1L, 2L, 3L));
    }
}