package com.classifierscomparision.classifierscomparisiontool.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.services.MethodService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/method")
public class MethodController {

    @Autowired
    private MethodService methodService;

    @PostMapping("/{dataset_id}")
    public ResponseEntity<?> addMethodToDataset(@Valid @RequestBody Method method, BindingResult result, @PathVariable Long dataset_id){

        if(result.hasErrors()){
            return new ResponseEntity<String>("Invalid Method Object", HttpStatus.BAD_REQUEST);
        }

        Method newMethod = methodService.addMethod(dataset_id, method);

        return new ResponseEntity<Method>(newMethod,HttpStatus.CREATED);
    }

    @GetMapping("/methods/{dataset_id}")
    public Iterable<Method> getDatasetMethods(@PathVariable Long dataset_id){

        return methodService.findDatasetById(dataset_id);
    }

    @GetMapping("/methods/crossValidation/{dataset_id}")
    public Iterable<Method> getDatasetMethodsCrossValidationSplit(@PathVariable Long dataset_id){

        return methodService.findCrossValidationMethodsByDatasetId(dataset_id);
    }

    @GetMapping("/methods/bagging/{dataset_id}")
    public Iterable<Method> getDatasetMethodsBaggingSplit(@PathVariable Long dataset_id){

        return methodService.findBaggingMethodsByDatasetId(dataset_id);
    }

    @GetMapping("/methods/boosting/{dataset_id}")
    public Iterable<Method> getDatasetMethodsBoostingSplit(@PathVariable Long dataset_id){

        return methodService.findBoostingMethodsByDatasetId(dataset_id);
    }

    @GetMapping("/methods/bestResult/{dataset_id}")
    public Method getDatasetMethodWithBestResult(@PathVariable Long dataset_id){

    	return methodService.findMethodWithTheBestF1ResultByDatasetId(dataset_id);
    }
}
