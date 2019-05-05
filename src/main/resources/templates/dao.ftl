
import ${MyRepositoryPackageName}.MyRepository;
import ${EntityPackageName}.${EntityName};
import org.springframework.stereotype.Repository;

@Repository
public interface ${EntityName}Repository extends MyRepository<${EntityName},Long> {

}