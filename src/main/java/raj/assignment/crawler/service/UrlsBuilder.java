package raj.assignment.crawler.service;

import raj.assignment.crawler.entity.UrlsEntity;
import raj.assignment.crawler.repository.UrlsImageRepository;
import raj.assignment.crawler.repository.UrlsRepository;
import raj.assignment.crawler.to.UrlDetailsTo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class UrlsBuilder {

    private ArrayList<UrlDetailsTo> urlDetailsToArrayList = new ArrayList<>();

    private int totalImageCount;

    UrlsRepository urlsRepository;

    UrlsImageRepository urlsImageRepository;

    public UrlsBuilder(UrlsRepository urlsRepository,UrlsImageRepository urlsImageRepository) {
        this.urlsRepository = urlsRepository;
        this.urlsImageRepository = urlsImageRepository;
    }

    public int getTotalImageCount() {
        return totalImageCount;
    }

    public void setTotalImageCount(int totalImageCount) {
        this.totalImageCount = totalImageCount;
    }

    public List<UrlDetailsTo> findAllUrlList(List<UrlsEntity> baseUrlsList,int depth){
        if(depth == 0){
            return urlDetailsToArrayList;
        }
        for (int i=0;i<baseUrlsList.size();i++){
            UrlsEntity urlsEntity = baseUrlsList.get(i);
            List<UrlsEntity> urlList = urlsRepository.findByParentUrlIdAndHierarchyLevel(urlsEntity.getUrlId(),urlsEntity.getHierarchyLevel()+1);
            List<UrlDetailsTo> urlToList =  urlList.stream().map(
                    urlsEntityObj -> {
                        UrlDetailsTo urlDetailsTo = new UrlDetailsTo();
                        urlDetailsTo.setPageTitle(urlsEntityObj.getName());
                        urlDetailsTo.setPageLink(urlsEntityObj.getHierarchyName());
                        urlDetailsTo.setImageCount(urlsImageRepository.countByUrlId(urlsEntityObj.getUrlId()));

                        setTotalImageCount(getTotalImageCount()+urlDetailsTo.getImageCount());
                        return urlDetailsTo;
                    }

            ).collect(Collectors.toList());
            urlDetailsToArrayList.addAll(urlToList);
            findAllUrlList(urlList,depth-1);
        }
        return urlDetailsToArrayList;
    }

}
