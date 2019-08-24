package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.exceptions.DatasetIdException;
import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.repositories.DatasetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    public Dataset saveOrUpdateDataset(Dataset dataset){

        return datasetRepository.save(dataset);
    }

    public Optional<Dataset> findDatasetById(Long id){
        return datasetRepository.findById(id);
    }

    public Iterable<Dataset> findAllDatasets(){
        return datasetRepository.findAll();
    }

    public void deleteDatasetById(Long id){
       Optional<Dataset> dataset = datasetRepository.findById(id);

       if(dataset.isPresent()){
           datasetRepository.deleteById(id);
       }else{
           throw new DatasetIdException("Dataset with id: "  + id + "doesn't exist");
       }
    }
}
