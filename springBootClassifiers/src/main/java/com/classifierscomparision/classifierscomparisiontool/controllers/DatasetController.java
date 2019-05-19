package com.classifierscomparision.classifierscomparisiontool.controllers;

import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.services.DatasetService;
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
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/dataset")
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @PostMapping("/addDataset")
    public ResponseEntity<?> addNewDataset(@Valid @RequestBody Dataset dataset, BindingResult result){

        if(result.hasErrors()){
           return new ResponseEntity<String>("Invalid Dataset Object", HttpStatus.BAD_REQUEST);
        }
        Dataset newDataset = datasetService.saveOrUpdateDataset(dataset);
        return new ResponseEntity<Dataset>(dataset, HttpStatus.CREATED);
    }


    @GetMapping("/{datasetId}")
    public ResponseEntity<?> getDatasetById(@PathVariable Long datasetId){

        Optional<Dataset> dataset = datasetService.findDatasetById(datasetId);

        if(dataset.isPresent()){
            return new ResponseEntity<Dataset>(dataset.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<String>("Dataset with id: " + datasetId + " doesn't exist", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("all")
    public Iterable<Dataset> getAllDatasets(){
        return datasetService.findAllDatasets();
    }

    @DeleteMapping("/{datasetId}")
    public ResponseEntity<?> deleteDataset(@PathVariable Long datasetId){
        datasetService.deleteDatasetById(datasetId);

        return new ResponseEntity<String>("Dataset with id: " + datasetId + "deleted succesfully", HttpStatus.OK);
    }


    @RequestMapping(value="/upload", method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile (@RequestParam("file") MultipartFile file) {
        try {
            byte[] bytes = file.getBytes();
            Path path = Paths.get("/home/kaja/Pulpit/PracaMagisterska/testFileUpload/" + file.getOriginalFilename());
            Files.write(path, bytes);

        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Double result = 3.14567789;
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
