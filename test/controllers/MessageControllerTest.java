package controllers;

import api.SendMessageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dao.models.Message;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import play.mvc.Http;
import service.MessageService;

import javax.validation.ValidationException;
import java.util.Arrays;

public class MessageControllerTest {

    private static final String RECIPIENT = "user";
    private static final String MSG = "message";
    private static final String LONG_MSG = "messagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessagemessage";
    private MessageService mock;
    private MessageController controller;

    @Before
    public void setup() {

        mock = Mockito.mock(MessageService.class);
        controller = new MessageController(mock);
    }

    @Test
    public void testList() {
        controller.list(RECIPIENT, 0, 5);
        Mockito.verify(mock).retrieve(RECIPIENT, 0, 5);
    }

    @Test(expected = ValidationException.class)
    public void testListWithoutRecipient() {
        controller.list(null, 0, 5);
    }

    @Test(expected = ValidationException.class)
    public void testListInvalidNumbers() {
        controller.list(RECIPIENT, 0, 0);
    }

    @Test(expected = ValidationException.class)
    public void testListNegativeNumbers() {
        controller.list(RECIPIENT, -1, 10);
    }

    @Test(expected = ValidationException.class)
    public void testListNegativeSpan() {
        controller.list(RECIPIENT, 10, 5);
    }

    @Test
    public void testListNew() {
        controller.listNew(RECIPIENT);
        Mockito.verify(mock).retrieveNew(RECIPIENT);
    }

    @Test
    public void testRemove() {
        controller.remove("1,3,4");
        Mockito.verify(mock).remove(Arrays.asList("1", "3", "4"));
    }

    @Test(expected = ValidationException.class)
    public void testRemoveWithoutParams() {
        controller.remove(null);
    }

    @Test
    public void testSend() {
        final SendMessageRequest request = getSendMessageRequest(RECIPIENT, MSG);

        controller.send();
        Mockito.verify(mock).store(request);
    }

    @Test(expected = ValidationException.class)
    public void testSendTooLongText() {
        final SendMessageRequest request = getSendMessageRequest(RECIPIENT, LONG_MSG);

        controller.send();
        Mockito.verify(mock, Mockito.never()).store(request);
    }


    private SendMessageRequest getSendMessageRequest(String recipient, String message) {
        final ObjectMapper objectMapper = new ObjectMapper();
        final SendMessageRequest request = new SendMessageRequest();
        request.setRecipient(recipient);
        request.setText(message);
        final JsonNode jsonNode = objectMapper.convertValue(request, JsonNode.class);
        Http.RequestBuilder builder = new Http.RequestBuilder().bodyJson(jsonNode);
        final Http.Context context = new Http.Context(builder.build());
        Http.Context.current.set(context);

        Mockito.when(mock.store(request)).thenReturn(new Message());
        return request;
    }
}