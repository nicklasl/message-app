package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import javax.validation.*;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class JsonController extends Controller {

    protected Result okJson(Object response) {
        return ok(Json.toJson(response));
    }

    protected JsonNode getBodyAsJson() {
        return request().body().asJson();
    }

    protected <T> T getBodyAs(Class<T> clazz) {
        final JsonNode jsonNode = request().body().asJson();
        return Json.fromJson(jsonNode, clazz);
    }

    protected void validate(Object bean) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        final Set<ConstraintViolation<Object>> violations = validator.validate(bean);
        if (!violations.isEmpty()) {
            Logger.info("Violations when validating bean: " + bean);
            String msg = violations.stream()
                    .map(violation -> violation.getPropertyPath().toString() + " - " + violation.getMessage()).collect(Collectors.joining("\n"));
            throw new ValidationException(msg);
        }
    }
}
