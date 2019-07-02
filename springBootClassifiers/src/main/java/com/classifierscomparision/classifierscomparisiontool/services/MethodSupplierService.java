package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.DTBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.KNNBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.SVMBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.DTCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.KNNCrossValidation;
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


    public void addMethodsForDataset(Long dataset_id, String fileName) throws InterruptedException {


        String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();

        List<Thread> threads = new ArrayList<>();
        

        DTCrossValidation decisionTreeClassifierCrossValidation = new DTCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierCrossValidation);

        DTBagging decisionTreeClassifierBagging = new DTBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierBagging);

        SVMCrossValidation svmCrossValidation = new SVMCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmCrossValidation);

        SVMBagging svmClassifierBagging = new SVMBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmClassifierBagging);

        KNNCrossValidation knnClassifierCrossValidation = new KNNCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(knnClassifierCrossValidation);

        KNNBagging knnClassifierBagging = new KNNBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(knnClassifierBagging);

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

        Double F1scoreKNNCrossValidation = knnClassifierCrossValidation.getF1Score();
        Double AccuracyKNNCrossValidation = knnClassifierCrossValidation.getAccuracy();
        Double SensivityKNNCrossValidation = knnClassifierCrossValidation.getSensivity();
        Double SpecificityKNNCrossValidation = knnClassifierCrossValidation.getSpecificity();

        Double weightedResultKNNCrossValidation = (F1scoreKNNCrossValidation + AccuracyKNNCrossValidation + SensivityKNNCrossValidation + SpecificityKNNCrossValidation)/4;


        Double F1scoreKNNBagging= knnClassifierBagging.getF1Score();
        Double AccuracyKNNBagging = knnClassifierBagging.getAccuracy();
        Double SensivityKNNBagging = knnClassifierBagging.getSensivity();
        Double SpecificityKNNBagging = knnClassifierBagging.getSpecificity();

        Double weightedResultKNNBagging = (F1scoreKNNBagging + AccuracyKNNBagging + SensivityKNNBagging + SpecificityKNNBagging)/4;


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

        Method KNNMethodCrossValidation = new Method();

        KNNMethodCrossValidation.setMethodName("K nearest neighbour");
        KNNMethodCrossValidation.setResult(weightedResultKNNCrossValidation);
        KNNMethodCrossValidation.setSplitName("CrossValidation");
        KNNMethodCrossValidation.setF1Score(F1scoreKNNCrossValidation);
        KNNMethodCrossValidation.setAccuracy(AccuracyKNNCrossValidation);
        KNNMethodCrossValidation.setSensivity(SensivityKNNCrossValidation);
        KNNMethodCrossValidation.setSpecificity(SpecificityKNNCrossValidation);

        Method KNNMethodBagging = new Method();

        KNNMethodBagging.setMethodName("K nearest neighbour");
        KNNMethodBagging.setResult(weightedResultKNNBagging);
        KNNMethodBagging.setSplitName("Bagging");
        KNNMethodBagging.setF1Score(F1scoreKNNBagging);
        KNNMethodBagging.setAccuracy(AccuracyKNNBagging);
        KNNMethodBagging.setSensivity(SensivityKNNBagging);
        KNNMethodBagging.setSpecificity(SpecificityKNNBagging);



        methodService.addMethod(dataset_id, decisionTreeMethodCrossValidation);

        methodService.addMethod(dataset_id, decisionTreeMethodBagging);

        methodService.addMethod(dataset_id, SVMMethodCrossValidation);

        methodService.addMethod(dataset_id, SVMMethodBagging);

        methodService.addMethod(dataset_id, KNNMethodCrossValidation);

        methodService.addMethod(dataset_id,KNNMethodBagging);


    }

}
