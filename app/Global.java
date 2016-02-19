import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import play.Application;
import play.GlobalSettings;
import play.libs.F;
import play.libs.Json;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Results;

import javax.validation.ValidationException;

public class Global extends GlobalSettings {

    @Override
    public void onStart(Application app) {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JodaModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.enable(DeserializationFeature.FAIL_ON_READING_DUP_TREE_KEY);
        Json.setObjectMapper(objectMapper);
    }

    @Override
    public F.Promise<Result> onError(Http.RequestHeader request, Throwable t) {
        if (t instanceof ValidationException) {
            return F.Promise.<Result>pure(
                    //TODO return as a json object?
                    Results.status(400, t.getMessage())
            );
        } else {
            return super.onError(request, t);
        }
    }
}
