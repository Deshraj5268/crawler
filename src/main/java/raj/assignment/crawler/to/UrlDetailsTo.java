package raj.assignment.crawler.to;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UrlDetailsTo {

    private String pageTitle;

    private String pageLink;

    private int imageCount;

    public UrlDetailsTo() {
    }

    public UrlDetailsTo(String pageTitle, String pageLink, int imageCount) {
        this.pageTitle = pageTitle;
        this.pageLink = pageLink;
        this.imageCount = imageCount;
    }
}
