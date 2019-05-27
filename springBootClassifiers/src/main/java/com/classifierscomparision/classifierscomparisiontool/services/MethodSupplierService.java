package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.classifiers.DTCassifier;
import com.classifierscomparision.classifierscomparisiontool.classifiers.SVMLibSVM;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.services.DatasetService;
import com.classifierscomparision.classifierscomparisiontool.services.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class MethodSupplierService {

    @Autowired
    private MethodService methodService;

    public List<Method> addMethodsForDataset(Long dataset_id, String fileName) throws InterruptedException {
//        Method decisionTreeMethod = new Method();
//
//        decisionTreeMethod.setMethodName("Decision tree");
//        decisionTreeMethod.setResult(0.99999);
//
//        Method SVMMethod = new Method();
//        SVMMethod.setMethodName("SVM");
//        SVMMethod.setResult(0.12345);

//        Method DTMethod = methodService.addMethod(dataset_id, decisionTreeMethod);
//
//        Method sVMMethod = methodService.addMethod(dataset_id, SVMMethod);

        String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();

        List<Thread> threads = new ArrayList<>();
        DTCassifier decisionTreeClassifier = new DTCassifier(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifier);

        SVMLibSVM svmClassifier = new SVMLibSVM(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmClassifier);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        double value = decisionTreeClassifier.getResult();
        System.out.println("AAAAAAAAAA: " + value);

        double value1 = svmClassifier.getResult();
        System.out.println("BBBBBB: " + value1);


        Method decisionTreeMethod = new Method();

        decisionTreeMethod.setMethodName("Decision tree");
        decisionTreeMethod.setResult(value);

        Method SVMMethod = new Method();
        SVMMethod.setMethodName("SVM");
        SVMMethod.setResult(value1);

        Method DTMethod = methodService.addMethod(dataset_id, decisionTreeMethod);

        Method sVMMethod = methodService.addMethod(dataset_id, SVMMethod);

//        List<Double> resultsList = new ArrayList<>();
//        resultsList.add(value);
//        resultsList.add(value1);

        List<Method> methodsList = new ArrayList<>();
        methodsList.add(DTMethod);
        methodsList.add(SVMMethod);
        return methodsList;

    }
}
