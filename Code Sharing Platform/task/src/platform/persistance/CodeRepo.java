package platform.persistance;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import platform.model.Code;

@Repository
public interface CodeRepo extends CrudRepository<Code, String> {

}
