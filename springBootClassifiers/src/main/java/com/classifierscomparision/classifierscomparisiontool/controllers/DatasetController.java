package com.classifierscomparision.classifierscomparisiontool.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.classifierscomparision.classifierscomparisiontool.models.Dataset;
import com.classifierscomparision.classifierscomparisiontool.services.DatasetService;
import com.classifierscomparision.classifierscomparisiontool.services.MethodSupplierService;

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
        datasetService.saveOrUpdateDataset(dataset);
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

        return new ResponseEntity<String>("Dataset deleted succesfully", HttpStatus.OK);
    }

    @RequestMapping(value="/upload", method= RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadFile (@RequestParam("file") MultipartFile file) throws InterruptedException{
        try {
            byte[] bytes = file.getBytes();

            String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();
            Path path = Paths.get(projectDir + "/datasets/CSVDatasets/" + file.getOriginalFilename());
            Files.write(path, bytes);

            Dataset dataset = new Dataset(file.getOriginalFilename());

            datasetService.saveOrUpdateDataset(dataset);

            Long uploadedDatasetId = dataset.getId();

            methodSupplier.addMethodsForDataset(uploadedDatasetId, file.getOriginalFilename());

            return new ResponseEntity<>(uploadedDatasetId, HttpStatus.OK);

        }catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

