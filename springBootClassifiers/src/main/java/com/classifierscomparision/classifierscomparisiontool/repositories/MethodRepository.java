package com.classifierscomparision.classifierscomparisiontool.repositories;

import com.classifierscomparision.classifierscomparisiontool.models.Method;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodRepository extends CrudRepository<Method,Long>{
}
