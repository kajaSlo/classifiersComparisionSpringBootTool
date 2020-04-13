package com.classifierscomparision.classifierscomparisiontool.repositories;

import com.classifierscomparision.classifierscomparisiontool.models.Method;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MethodRepository extends CrudRepository<Method,Long>{

    List<Method> findAllByDataset_IdOrderByF1ScoreDesc(Long id);

    List<Method> findAllByDataset_IdAndSplitNameEqualsOrderByF1ScoreDesc(Long id,String splitName);
    
    Method findFirstByDataset_IdOrderByF1ScoreDesc(Long id);
}
