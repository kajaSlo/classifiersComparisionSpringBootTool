package com.classifierscomparision.classifierscomparisiontool.services;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.classifierscomparision.classifierscomparisiontool.exceptions.DatasetIdException;
import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.repositories.DatasetRepository;
import com.classifierscomparision.classifierscomparisiontool.repositories.MethodRepository;

@Service
public class MethodService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private MethodRepository methodRepository;

    public Method addMethod(Long id, Method method){

        Optional<Dataset> dataset = datasetRepository.findById(id);

        if(dataset.isPresent()){
            method.setDataset(dataset.get());
            return methodRepository.save(method);
        }else{
            throw new DatasetIdException("Dataset with id: "  + id + "doesn't exist");
        }
    }

    public Iterable<Method> findDatasetById(Long id){
        return methodRepository.findAllByDataset_IdOrderByResultDesc(id);
    }

    public Iterable<Method> findCrossValidationMethodsByDatasetId(Long id){
        return methodRepository.findAllByDataset_IdAndSplitNameEqualsOrderByResultDesc(id, "CrossValidation");
    }

    public Iterable<Method> findBaggingMethodsByDatasetId(Long id){
        return methodRepository.findAllByDataset_IdAndSplitNameEqualsOrderByResultDesc(id, "Bagging");
    }

    public Iterable<Method> findBoostingMethodsByDatasetId(Long id){
        return methodRepository.findAllByDataset_IdAndSplitNameEqualsOrderByResultDesc(id, "Boosting");
    }
    
    public Method findMethodWithTheBestResultByDatasetId(Long id) {
    	
    	return methodRepository.findFirstByDataset_IdOrderByResultDesc(id);
    }
}
