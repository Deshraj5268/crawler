package raj.assignment.crawler.to;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequestTo {

    @JsonProperty("baseUrl")
    private String baseUrl;

    @JsonProperty("depth")
    private int depth;

}
