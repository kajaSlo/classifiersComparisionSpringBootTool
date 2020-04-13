package com.classifierscomparision.classifierscomparisiontool.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.classifierscomparision.classifierscomparisiontool.models.Dataset;

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
