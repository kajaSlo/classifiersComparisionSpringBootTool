package com.classifierscomparision.classifierscomparisiontool.repositories;

import com.classifierscomparision.classifierscomparisiontool.models.Method;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodRepository extends CrudRepository<Method,Long>{

    List<Method> findAllByDataset_IdOrderByResultDesc(Long id);

    List<Method> findAllByDataset_IdAndSplitNameEqualsOrderByResultDesc(Long id,String splitName);
    
    Method findFirstByDataset_IdOrderByResultDesc(Long id);

}
