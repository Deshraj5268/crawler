package raj.assignment.crawler.to;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Response {

    private String totlLinks;

    private int totalImageCount;

    private List<UrlDetailsTo> urlDetailsTos;
}
