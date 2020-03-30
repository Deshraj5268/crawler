package raj.assignment.crawler.repository;

import org.springframework.data.jpa.repository.Query;
import raj.assignment.crawler.entity.UrlsEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UrlsRepository extends CrudRepository<UrlsEntity,Integer> {

   List<UrlsEntity> findByHierarchyName(String hierarchyName);

    List<UrlsEntity> findByParentUrlIdAndHierarchyLevel(int parentUrlId,int hierarchyLevel);
}
