package controllers;

import api.MessageResponse;
import api.SendMessageRequest;
import dao.models.Message;
import play.db.jpa.Transactional;
import play.mvc.BodyParser;
import play.mvc.Result;
import service.MessageService;

import javax.inject.Inject;
import javax.validation.ValidationException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MessageController extends JsonController {

    @Inject
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    private MessageService messageService;

    @Transactional
    @BodyParser.Of(BodyParser.Json.class)
    public Result send() {
        final SendMessageRequest sendMessageRequest = getBodyAs(SendMessageRequest.class);
        validate(sendMessageRequest);
        final Message message = messageService.store(sendMessageRequest);
        final MessageResponse messageResponse = MessageResponse.constructFromMessage(message);
        return okJson(messageResponse);
    }

    @Transactional
    public Result listNew(String recipient) {
        validateParamIsSet(recipient);
        final List<Message> messages = messageService.retrieveNew(recipient);
        return okJson(mapToResponseObjects(messages));
    }


    @Transactional
    public Result list(String recipient, int start, int stop) {
        validateParamIsSet(recipient);
        validateStartAndStop(start, stop);
        final List<Message> retrieve = messageService.retrieve(recipient, start, stop);
        return okJson(mapToResponseObjects(retrieve));
    }

    @Transactional
    public Result remove(String ids) {
        validateParamIsSet(ids);
        final String[] splitIds = ids.split(",");
        messageService.remove(Arrays.asList(splitIds));
        return ok();
    }

    private List<MessageResponse> mapToResponseObjects(List<Message> messages) {
        final Stream<MessageResponse> messageResponseStream = messages.stream().map(MessageResponse::constructFromMessage);
        return messageResponseStream.collect(Collectors.toList());
    }

    private void validateParamIsSet(String param) {
        if (param == null || param.isEmpty()) {
            throw new ValidationException("Parameter not specified");
        }
    }

    private void validateStartAndStop(int start, int stop) {
        if (start < 0 || stop < 0) {
            throw new ValidationException("start/stop cannot be a negative number");
        }
        if (stop < start) {
            throw new ValidationException("stop must be larger than start");
        }
        if (start == stop) {
            throw new ValidationException("start and stop cannot be equal");
        }
    }
}