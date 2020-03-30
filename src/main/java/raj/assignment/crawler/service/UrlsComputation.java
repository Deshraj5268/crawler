package raj.assignment.crawler.service;

import raj.assignment.crawler.entity.UrlsEntity;
import raj.assignment.crawler.repository.UrlsImageRepository;
import raj.assignment.crawler.repository.UrlsRepository;
import raj.assignment.crawler.to.UrlDetailsTo;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class UrlsComputation extends RecursiveTask<List<UrlDetailsTo>> {

    private UrlsRepository urlsRepository;

    private UrlsImageRepository urlsImageRepository;

    private List<UrlsEntity> baseUrlsList;

    private int depth;

    LinkedList<UrlDetailsTo> urlDetailsToArrayListResult = new LinkedList<>();

    public UrlsComputation(UrlsRepository urlsRepository, UrlsImageRepository urlsImageRepository, List<UrlsEntity> baseUrlsList, int depth) {
        this.urlsRepository = urlsRepository;
        this.urlsImageRepository = urlsImageRepository;
        this.baseUrlsList = baseUrlsList;
        this.depth = depth;
    }

    public UrlsComputation(List<UrlsEntity> baseUrlsList, int depth) {
        this.baseUrlsList = baseUrlsList;
        this.depth = depth;

    }

    private int totalImageCount;

    public int getTotalImageCount() {
        return totalImageCount;
    }

    public void setTotalImageCount(int totalImageCount) {
        this.totalImageCount = totalImageCount;
    }
    List<UrlsComputation> urlsComputationList = new LinkedList<>();
    @Override
    protected List<UrlDetailsTo> compute() {
        //LinkedList<UrlDetailsTo> urlDetailsToArrayList = new LinkedList<>();


        if(depth ==0){
            return urlDetailsToArrayListResult;
        }

        for (int i=0;i<baseUrlsList.size();i++){
            UrlsEntity urlsEntity = baseUrlsList.get(i);
            List<UrlsEntity> urlList = urlsRepository.findByParentUrlIdAndHierarchyLevel(urlsEntity.getUrlId(),urlsEntity.getHierarchyLevel()+1);
            UrlsComputation urlsComputationTask = new UrlsComputation(urlsRepository,urlsImageRepository,urlList,depth-1);
            urlsComputationTask.fork();
            urlsComputationList.add(urlsComputationTask);
            urlsComputationTask.compute();
        }

        return addedTaskComputation(urlsComputationList);

      //  return urlDetailsToArrayListResult;

    }

    private List<UrlsEntity> createSubTask(UrlsEntity urlsEntity){
         return  urlsRepository.findByParentUrlIdAndHierarchyLevel(urlsEntity.getUrlId(),urlsEntity.getHierarchyLevel()+1);
    }

    private List<UrlDetailsTo> addedTaskComputation(List<UrlsComputation> urlsComputationList){
        for(int i=0;i<urlsComputationList.size();i++) {
            UrlsComputation urlsComputationObj = urlsComputationList.get(i);
            urlsComputationObj.join();
            List<UrlDetailsTo> lisDetailsTo = new LinkedList<>();
            if(urlsComputationObj.baseUrlsList != null){
                for(int j=0;j<urlsComputationObj.baseUrlsList.size();j++){
                    UrlsEntity urlsEntityObj = urlsComputationObj.baseUrlsList.get(j);
                    UrlDetailsTo urlDetailsTo = new UrlDetailsTo();
                    urlDetailsTo.setPageTitle(urlsEntityObj.getName());
                    urlDetailsTo.setPageLink(urlsEntityObj.getHierarchyName());
                    urlDetailsTo.setImageCount(urlsImageRepository.countByUrlId(urlsEntityObj.getUrlId()));

                    setTotalImageCount(getTotalImageCount() + urlDetailsTo.getImageCount());
                    lisDetailsTo.add(urlDetailsTo);
                }
            }
            urlDetailsToArrayListResult.addAll(lisDetailsTo);
        }
        return urlDetailsToArrayListResult;

    }
}
