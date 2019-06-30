package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.DTBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.SVMBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.DTCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.SVMCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class MethodSupplierService {

    @Autowired
    private MethodService methodService;

//    public List<Method> addMethodsForDataset(Long dataset_id, String fileName) throws InterruptedException {
////        Method decisionTreeMethod = new Method();
////
////        decisionTreeMethod.setMethodName("Decision tree");
////        decisionTreeMethod.setResult(0.99999);
////
////        Method SVMMethod = new Method();
////        SVMMethod.setMethodName("SVM");
////        SVMMethod.setResult(0.12345);
//
////        Method DTMethod = methodService.addMethod(dataset_id, decisionTreeMethod);
////
////        Method sVMMethod = methodService.addMethod(dataset_id, SVMMethod);
//
//        String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();
//
//        List<Thread> threads = new ArrayList<>();
//        DTCassifier decisionTreeClassifier = new DTCassifier(projectDir + "/datasets/CSVDatasets/" + fileName);
//        threads.add(decisionTreeClassifier);
//
//        SVMLibSVM svmClassifier = new SVMLibSVM(projectDir + "/datasets/CSVDatasets/" + fileName);
//        threads.add(svmClassifier);
//
//        for (Thread thread : threads) {
//            thread.start();
//            thread.join();
//        }
//
//        double value = decisionTreeClassifier.getResult();
//        System.out.println("AAAAAAAAAA: " + value);
//
//        double value1 = svmClassifier.getResult();
//        System.out.println("BBBBBB: " + value1);
//
//
//        Method decisionTreeMethod = new Method();
//
//        decisionTreeMethod.setMethodName("Decision tree");
//        decisionTreeMethod.setResult(value);
//
//        Method SVMMethod = new Method();
//        SVMMethod.setMethodName("SVM");
//        SVMMethod.setResult(value1);
//
//        Method DTMethod = methodService.addMethod(dataset_id, decisionTreeMethod);
//
//        Method sVMMethod = methodService.addMethod(dataset_id, SVMMethod);
//
////        List<Double> resultsList = new ArrayList<>();
////        resultsList.add(value);
////        resultsList.add(value1);
//
//        List<Method> methodsList = new ArrayList<>();
//        methodsList.add(DTMethod);
//        methodsList.add(SVMMethod);
//        return methodsList;
//
//    }




    public List<Method> addMethodsForDataset(Long dataset_id, String fileName) throws InterruptedException {


        String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();

        List<Thread> threads = new ArrayList<>();



        DTCrossValidation decisionTreeClassifierCrossValidation = new DTCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierCrossValidation);

        DTBagging decisionTreeClassifierBagging = new DTBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierBagging);


//        SVMLibSVM svmClassifier = new SVMLibSVM(projectDir + "/datasets/CSVDatasets/" + fileName);
//        threads.add(svmClassifier);

        SVMCrossValidation svmCrossValidation = new SVMCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmCrossValidation);

        SVMBagging svmClassifierBagging = new SVMBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmClassifierBagging);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        Double F1scoreDTCrossValidation = decisionTreeClassifierCrossValidation.getF1Score();
        Double AccuracyDTCrossValidation = decisionTreeClassifierCrossValidation.getAccuracy();
        Double SensivityDTCrossValidation = decisionTreeClassifierCrossValidation.getSensivity();
        Double SpecificityDTCrossValidation = decisionTreeClassifierCrossValidation.getSpecificity();

       Double weightedResultDTCrossValidation = (F1scoreDTCrossValidation + AccuracyDTCrossValidation + SensivityDTCrossValidation + SpecificityDTCrossValidation)/4;


        Double F1scoreDTBagging= decisionTreeClassifierBagging.getF1Score();
        Double AccuracyDTBagging = decisionTreeClassifierBagging.getAccuracy();
        Double SensivityDTBagging = decisionTreeClassifierBagging.getSensivity();
        Double SpecificityDTBagging = decisionTreeClassifierBagging.getSpecificity();

        Double weightedResultDTBagging = (F1scoreDTBagging + AccuracyDTBagging + SensivityDTBagging + SpecificityDTBagging)/4;


        Double F1scoreSVMCrossValidation = svmCrossValidation.getF1Score();
        Double AccuracySVMCrossValidation = svmCrossValidation.getAccuracy();
        Double SensivitySVMCrossValidation = svmCrossValidation.getSensivity();
        Double SpecificitySVMCrossValidation = svmCrossValidation.getSpecificity();

        Double weightedResultSVMCrossValidation = (F1scoreSVMCrossValidation + AccuracySVMCrossValidation + SensivitySVMCrossValidation + SpecificitySVMCrossValidation)/4;


        Double F1scoreSVMBagging= svmClassifierBagging.getF1Score();
        Double AccuracySVMBagging = svmClassifierBagging.getAccuracy();
        Double SensivitySVMBagging = svmClassifierBagging.getSensivity();
        Double SpecificitySVMBagging = svmClassifierBagging.getSpecificity();

        Double weightedResultSVMBagging = (F1scoreSVMBagging + AccuracySVMBagging + SensivitySVMBagging + SpecificitySVMBagging)/4;

        System.out.println("AAAAAAAA");

      ///  splitResultsCrossValidation.stream().forEach(System.out::println);


//        double value1 = svmClassifier.getResult();
//        System.out.println("BBBBBB: " + value1);


        Method decisionTreeMethodCrossValidation = new Method();

        decisionTreeMethodCrossValidation.setMethodName("Decision tree");


        decisionTreeMethodCrossValidation.setResult(weightedResultDTCrossValidation);
        decisionTreeMethodCrossValidation.setSplitName("CrossValidation");
        decisionTreeMethodCrossValidation.setF1Score(F1scoreDTCrossValidation);
        decisionTreeMethodCrossValidation.setAccuracy(AccuracyDTCrossValidation);
        decisionTreeMethodCrossValidation.setSensivity(SensivityDTCrossValidation);
        decisionTreeMethodCrossValidation.setSpecificity(SpecificityDTCrossValidation);


        Method decisionTreeMethodBagging = new Method();

        decisionTreeMethodBagging.setMethodName("Decision tree");


        decisionTreeMethodBagging.setResult(weightedResultDTBagging);
        decisionTreeMethodBagging.setSplitName("Bagging");
        decisionTreeMethodBagging.setF1Score(F1scoreDTBagging);
        decisionTreeMethodBagging.setAccuracy(AccuracyDTBagging);
        decisionTreeMethodBagging.setSensivity(SensivityDTBagging);
        decisionTreeMethodBagging.setSpecificity(SpecificityDTBagging);


        Method SVMMethodCrossValidation = new Method();

        SVMMethodCrossValidation.setMethodName("SVM");
        SVMMethodCrossValidation.setResult(weightedResultSVMCrossValidation);
        SVMMethodCrossValidation.setSplitName("CrossValidation");
        SVMMethodCrossValidation.setF1Score(F1scoreSVMCrossValidation);
        SVMMethodCrossValidation.setAccuracy(AccuracySVMCrossValidation);
        SVMMethodCrossValidation.setSensivity(SensivitySVMCrossValidation);
        SVMMethodCrossValidation.setSpecificity(SpecificitySVMCrossValidation);

        Method SVMMethodBagging = new Method();

        SVMMethodBagging.setMethodName("SVM");
        SVMMethodBagging.setResult(weightedResultSVMBagging);
        SVMMethodBagging.setSplitName("Bagging");
        SVMMethodBagging.setF1Score(F1scoreSVMBagging);
        SVMMethodBagging.setAccuracy(AccuracySVMBagging);
        SVMMethodBagging.setSensivity(SensivitySVMBagging);
        SVMMethodBagging.setSpecificity(SpecificitySVMBagging);



        Method DTMethodCrossVal = methodService.addMethod(dataset_id, decisionTreeMethodCrossValidation);

        Method DTMethodBagging = methodService.addMethod(dataset_id, decisionTreeMethodBagging);

        Method svmMethodCrossVal = methodService.addMethod(dataset_id, SVMMethodCrossValidation);

        Method svmMethodBagging = methodService.addMethod(dataset_id, SVMMethodBagging);

       // Method sVMMethod = methodService.addMethod(dataset_id, SVMMethod);



        List<Method> methodsList = new ArrayList<>();
        methodsList.add(DTMethodCrossVal);
        methodsList.add(DTMethodBagging);
        methodsList.add(svmMethodCrossVal);
        methodsList.add(svmMethodBagging);
    //    methodsList.add(SVMMethod);
        return methodsList;

    }




}
