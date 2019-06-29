package com.classifierscomparision.classifierscomparisiontool.services;


import com.classifierscomparision.classifierscomparisiontool.exceptions.DatasetIdException;
import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.repositories.DatasetRepository;
import com.classifierscomparision.classifierscomparisiontool.repositories.MethodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SplitResultsService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private MethodRepository methodRepository;


    public Iterable<Method> findDatasetById(Long id){
        return methodRepository.findAllByDataset_IdOrderByResultDesc(id);
    }


//    public Iterable<Method> findMethodsWithCrossValidationUsedByDatasetId(Long id){
//
//        Iterable<Method> allMethodsByDatasetId = methodRepository.findAllByDataset_IdOrderByResultDesc(id);
//
//        ((List<Method>) allMethodsByDatasetId).stream()
//
//                //sprawdzic czy metoda findByyMethod_IdAndSplitNameEquals cos zwroci, jesli nie bedzie to pusyu optional to do zwwracanej listy dodac metode z allMethodsByDatasetId, dla ktorej w polu splitName w tabeli SplitResults bylo pole rowne cerossvalidation
//
//    }
}
