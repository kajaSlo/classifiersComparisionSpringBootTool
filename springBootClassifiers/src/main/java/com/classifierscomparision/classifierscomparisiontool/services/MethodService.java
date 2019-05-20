package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.exceptions.DatasetIdException;
import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.repositories.DatasetRepository;
import com.classifierscomparision.classifierscomparisiontool.repositories.MethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
        //set method to dataset
    }
}
