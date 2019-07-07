package com.classifierscomparision.classifierscomparisiontool.repositories;

import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DatasetRepository extends CrudRepository<Dataset, Long>{

    @Override
    Iterable<Dataset> findAllById(Iterable<Long> iterable);

    @Override
    Optional<Dataset> findById(Long aLong);

    @Override
    Iterable<Dataset> findAll();

    @Override
    void deleteById(Long aLong);


}
