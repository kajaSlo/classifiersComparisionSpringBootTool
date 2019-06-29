package com.classifierscomparision.classifierscomparisiontool.controllers;

import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.services.DatasetService;
import com.classifierscomparision.classifierscomparisiontool.services.MethodService;
import com.classifierscomparision.classifierscomparisiontool.services.MethodSupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.io.File;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dataset")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @Autowired
    private MethodSupplierService methodSupplier;


    @PostMapping("/addDataset")
    public ResponseEntity<?> addNewDataset(@Valid @RequestBody Dataset dataset, BindingResult result){

        if(result.hasErrors()){
           return new ResponseEntity<String>("Invalid Dataset Object", HttpStatus.BAD_REQUEST);
        }
        Dataset newDataset = datasetService.saveOrUpdateDataset(dataset);
        return new ResponseEntity<Dataset>(dataset, HttpStatus.CREATED);
    }


    @GetMapping("/{dataset_id}")
    public ResponseEntity<?> getDatasetById(@PathVariable Long dataset_id){

        Optional<Dataset> dataset = datasetService.findDatasetById(dataset_id);

        if(dataset.isPresent()){
            return new ResponseEntity<Dataset>(dataset.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Dataset with id: " + dataset_id + " doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all")
    public Iterable<Dataset> getAllDatasets(){
        return datasetService.findAllDatasets();
    }

    @DeleteMapping("/{dataset_id}")
    public ResponseEntity<?> deleteDataset(@PathVariable Long dataset_id){
        datasetService.deleteDatasetById(dataset_id);

        return new ResponseEntity<String>("Dataset with id: " + dataset_id + "deleted succesfully", HttpStatus.OK);
    }


    @RequestMapping(value="/upload", method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile (@RequestParam("file") MultipartFile file) throws InterruptedException{
        try {
            byte[] bytes = file.getBytes();

            String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();
            Path path = Paths.get(projectDir + "/datasets/CSVDatasets/" + file.getOriginalFilename());
//            Path path = Paths.get("/home/kaja/Pulpit/PracaMagisterska/classifiersSpringBootApp/datasets/CSVDatasets/" + file.getOriginalFilename());
            Files.write(path, bytes);

            //Double result = 3.14567789;


            Dataset dataset = new Dataset(file.getOriginalFilename());

            Dataset newDataset = datasetService.saveOrUpdateDataset(dataset);

            Long uploadedDatasetId = newDataset.getId();


            List<Method> results = methodSupplier.addMethodsForDataset(uploadedDatasetId, file.getOriginalFilename());


            return new ResponseEntity<>(uploadedDatasetId, HttpStatus.OK);

        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

