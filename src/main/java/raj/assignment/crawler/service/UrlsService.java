package raj.assignment.crawler.service;

import org.springframework.context.annotation.Scope;
import raj.assignment.crawler.entity.UrlsEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import raj.assignment.crawler.repository.UrlsImageRepository;
import raj.assignment.crawler.repository.UrlsRepository;
import raj.assignment.crawler.to.UrlDetailsTo;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

@Component
public class UrlsService {

    @Autowired
    private UrlsRepository urlsRepository;

    @Autowired
    private UrlsImageRepository urlsImageRepository;

    public HashMap<String,Object> urlDetailsSearch(String baseUrl, int depth){
        List<UrlsEntity> list = urlsRepository.findByHierarchyName(baseUrl);
        UrlsBuilder urlsBuilder = new UrlsBuilder(urlsRepository,urlsImageRepository);
        List<UrlDetailsTo> resultList =  urlsBuilder.findAllUrlList(list,depth);

        HashMap resultMap = new HashMap<>();
        resultMap.put("total_links",resultList.size());
        resultMap.put("total_images", urlsBuilder.getTotalImageCount());
        resultMap.put("details",resultList);
        return resultMap;
    }

    public HashMap<String,Object> urlDetailsSearchOptimization(String baseUrl, int depth){
        List<UrlsEntity> list = urlsRepository.findByHierarchyName(baseUrl);
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        UrlsComputation urlsComputation = new UrlsComputation(urlsRepository,urlsImageRepository,list,depth);
        List<UrlDetailsTo> resultList = forkJoinPool.invoke(urlsComputation);
        //UrlsBuilder urlsBuilder = new UrlsComputation(urlsRepository,urlsImageRepository,list,depth);
        //List<UrlDetailsTo> resultList =  urlsBuilder.findAllUrlList(list,depth);

        HashMap resultMap = new HashMap<>();
        resultMap.put("total_links",resultList.size());
        resultMap.put("total_images", urlsComputation.getTotalImageCount());
        resultMap.put("details",resultList);
        return resultMap;
    }

   /* public List<UrlsEntity> getAllChildUrls(int parentUrlId,int hierarchyLevel){
        return urlsRepository.findByParentUrlIdAndHierarchyLevel(parentUrlId,hierarchyLevel);
    }*/
}
