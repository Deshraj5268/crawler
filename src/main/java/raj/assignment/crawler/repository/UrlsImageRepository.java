package raj.assignment.crawler.repository;

import org.springframework.data.repository.CrudRepository;
import raj.assignment.crawler.entity.UrlsImagesEntity;

public interface UrlsImageRepository extends CrudRepository<UrlsImagesEntity,Integer> {

    int countByUrlId(int urlId);
}
