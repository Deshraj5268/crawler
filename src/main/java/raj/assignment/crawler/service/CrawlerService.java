package raj.assignment.crawler.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import raj.assignment.crawler.to.RequestTo;

import java.util.HashMap;

@Service
public class CrawlerService {

    @Autowired
    private UrlsService urlsService;

    public HashMap search(RequestTo requestTo) {
        HashMap errorMap = validation(requestTo);
        if(errorMap.size() != 0){
            return errorMap;
        }
        return urlsService.urlDetailsSearch(requestTo.getBaseUrl(),requestTo.getDepth());
    }

    public HashMap searchOptimization(RequestTo requestTo) {
        HashMap errorMap = validation(requestTo);
        if(errorMap.size() != 0){
            return errorMap;
        }
        return urlsService.urlDetailsSearchOptimization(requestTo.getBaseUrl(),requestTo.getDepth());
    }

    private HashMap validation(RequestTo requestTo) {
        HashMap<String,Object> errorMap = new HashMap<>();
        if (requestTo == null){
            errorMap.put("error","request can not be null");
            return errorMap;
        }else {
            if(requestTo.getBaseUrl()== null){
                errorMap.put("error","baseUrl can not be null");
            }
            if(requestTo.getDepth() < 0){
                errorMap.put("error","depth can not be negative");
            }
        }
        return errorMap;
    }
}
