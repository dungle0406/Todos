package spring.rest.caches;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheDataRepository extends CrudRepository<CacheData, String> {
}