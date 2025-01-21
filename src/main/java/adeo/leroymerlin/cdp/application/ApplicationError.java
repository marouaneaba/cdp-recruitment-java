package adeo.leroymerlin.cdp.application;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationError {
    @JsonProperty("error")
    private String error;

    @JsonProperty("error_description")
    private String errorDescription;


    public ApplicationError(String error, String errorDescription) {
        this.error = error;
        this.errorDescription = errorDescription;
    }
}
