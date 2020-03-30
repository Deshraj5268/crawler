package raj.assignment.crawler.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import raj.assignment.crawler.constant.Constants;
import raj.assignment.crawler.service.CrawlerService;
import raj.assignment.crawler.to.RequestTo;


@RestController
@RequestMapping(path = Constants.API_CRAWLER_V1,produces = MediaType.APPLICATION_JSON_VALUE,consumes = MediaType.APPLICATION_JSON_VALUE)
public class Controller {

    private static final Logger LOGGER = LoggerFactory.getLogger(Controller.class);

    @Autowired
    private CrawlerService crawlerService;

    @PostMapping(path = "/v1/search")
    public ResponseEntity<?> search(@RequestBody RequestTo requestTo){
        LOGGER.error("requestTo : {} ",requestTo);
        return new ResponseEntity(crawlerService.search(requestTo), HttpStatus.OK);
    }

    @PostMapping(path = "/v2/search")
    public ResponseEntity<?> searchOptimization(@RequestBody RequestTo requestTo){
        LOGGER.error("requestTo : {} ",requestTo);
        return new ResponseEntity(crawlerService.searchOptimization(requestTo), HttpStatus.OK);
    }

    @GetMapping(path = "/test",produces = MediaType.ALL_VALUE,consumes = MediaType.ALL_VALUE)
    public String test(){
        return "Crawler Application is Up now";
    }


}
