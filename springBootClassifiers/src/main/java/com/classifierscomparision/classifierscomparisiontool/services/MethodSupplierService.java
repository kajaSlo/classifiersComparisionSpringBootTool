package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.DTBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.KNNBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.RandomForestBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.SVMBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.DTCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.KNNCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.RandomForestCrossVal;
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

        addMethodsCrossValidation(dataset_id, fileName, projectDir);
        addMethodsBagging(dataset_id, fileName, projectDir);

    }


    public void addMethodsCrossValidation(Long dataset_id, String fileName, String projectDir) throws InterruptedException{


        List<Thread> threads = new ArrayList<>();


        DTCrossValidation decisionTreeClassifierCrossValidation = new DTCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierCrossValidation);

        SVMCrossValidation svmCrossValidation = new SVMCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmCrossValidation);

        KNNCrossValidation knnClassifierCrossValidation = new KNNCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(knnClassifierCrossValidation);

        RandomForestCrossVal randomForestClassifierCrossValidation = new RandomForestCrossVal(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(randomForestClassifierCrossValidation);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        addDecisionTreeCrossValidation(dataset_id, decisionTreeClassifierCrossValidation);
        addSVMCrossValidation(dataset_id, svmCrossValidation);
        addKNNCrossValidation(dataset_id, knnClassifierCrossValidation);
        addRandomForestCrossValidation(dataset_id, randomForestClassifierCrossValidation);


    }


    public void addDecisionTreeCrossValidation(Long dataset_id, DTCrossValidation decisionTreeClassifierCrossValidation){

        Double F1scoreDTCrossValidation = decisionTreeClassifierCrossValidation.getF1Score();
        Double AccuracyDTCrossValidation = decisionTreeClassifierCrossValidation.getAccuracy();
        Double SensivityDTCrossValidation = decisionTreeClassifierCrossValidation.getSensivity();
        Double SpecificityDTCrossValidation = decisionTreeClassifierCrossValidation.getSpecificity();

        Double weightedResultDTCrossValidation = (F1scoreDTCrossValidation + AccuracyDTCrossValidation + SensivityDTCrossValidation + SpecificityDTCrossValidation)/4;

        Method decisionTreeMethodCrossValidation = new Method();

        decisionTreeMethodCrossValidation.setMethodName("Decision tree");

        decisionTreeMethodCrossValidation.setResult(weightedResultDTCrossValidation);
        decisionTreeMethodCrossValidation.setSplitName("CrossValidation");
        decisionTreeMethodCrossValidation.setF1Score(F1scoreDTCrossValidation);
        decisionTreeMethodCrossValidation.setAccuracy(AccuracyDTCrossValidation);
        decisionTreeMethodCrossValidation.setSensivity(SensivityDTCrossValidation);
        decisionTreeMethodCrossValidation.setSpecificity(SpecificityDTCrossValidation);

        methodService.addMethod(dataset_id, decisionTreeMethodCrossValidation);
    }

    public void addSVMCrossValidation(Long dataset_id, SVMCrossValidation svmCrossValidation){

        Double F1scoreSVMCrossValidation = svmCrossValidation.getF1Score();
        Double AccuracySVMCrossValidation = svmCrossValidation.getAccuracy();
        Double SensivitySVMCrossValidation = svmCrossValidation.getSensivity();
        Double SpecificitySVMCrossValidation = svmCrossValidation.getSpecificity();

        Double weightedResultSVMCrossValidation = (F1scoreSVMCrossValidation + AccuracySVMCrossValidation + SensivitySVMCrossValidation + SpecificitySVMCrossValidation)/4;

        Method SVMMethodCrossValidation = new Method();

        SVMMethodCrossValidation.setMethodName("SVM");
        SVMMethodCrossValidation.setResult(weightedResultSVMCrossValidation);
        SVMMethodCrossValidation.setSplitName("CrossValidation");
        SVMMethodCrossValidation.setF1Score(F1scoreSVMCrossValidation);
        SVMMethodCrossValidation.setAccuracy(AccuracySVMCrossValidation);
        SVMMethodCrossValidation.setSensivity(SensivitySVMCrossValidation);
        SVMMethodCrossValidation.setSpecificity(SpecificitySVMCrossValidation);

        methodService.addMethod(dataset_id, SVMMethodCrossValidation);
    }

    public void addKNNCrossValidation(Long dataset_id, KNNCrossValidation knnClassifierCrossValidation){

        Double F1scoreKNNCrossValidation = knnClassifierCrossValidation.getF1Score();
        Double AccuracyKNNCrossValidation = knnClassifierCrossValidation.getAccuracy();
        Double SensivityKNNCrossValidation = knnClassifierCrossValidation.getSensivity();
        Double SpecificityKNNCrossValidation = knnClassifierCrossValidation.getSpecificity();

        Double weightedResultKNNCrossValidation = (F1scoreKNNCrossValidation + AccuracyKNNCrossValidation + SensivityKNNCrossValidation + SpecificityKNNCrossValidation)/4;

        Method KNNMethodCrossValidation = new Method();

        KNNMethodCrossValidation.setMethodName("K nearest neighbour");
        KNNMethodCrossValidation.setResult(weightedResultKNNCrossValidation);
        KNNMethodCrossValidation.setSplitName("CrossValidation");
        KNNMethodCrossValidation.setF1Score(F1scoreKNNCrossValidation);
        KNNMethodCrossValidation.setAccuracy(AccuracyKNNCrossValidation);
        KNNMethodCrossValidation.setSensivity(SensivityKNNCrossValidation);
        KNNMethodCrossValidation.setSpecificity(SpecificityKNNCrossValidation);

        methodService.addMethod(dataset_id, KNNMethodCrossValidation);
    }

    public void addRandomForestCrossValidation(Long dataset_id, RandomForestCrossVal randomForestClassifierCrossValidation){

        Double F1scoreRandomForestCrossValidation = randomForestClassifierCrossValidation.getF1Score();
        Double AccuracyRandomForestCrossValidation = randomForestClassifierCrossValidation.getAccuracy();
        Double SensivityRandomForestCrossValidation = randomForestClassifierCrossValidation.getSensivity();
        Double SpecificityRandomForestCrossValidation = randomForestClassifierCrossValidation.getSpecificity();

        Double weightedResultRandomForestCrossValidation = (F1scoreRandomForestCrossValidation + AccuracyRandomForestCrossValidation + SensivityRandomForestCrossValidation + SpecificityRandomForestCrossValidation)/4;

        Method randomForestMethodCrossValidation = new Method();

        randomForestMethodCrossValidation.setMethodName("Random Forest");
        randomForestMethodCrossValidation.setResult(weightedResultRandomForestCrossValidation);
        randomForestMethodCrossValidation.setSplitName("CrossValidation");
        randomForestMethodCrossValidation.setF1Score(F1scoreRandomForestCrossValidation);
        randomForestMethodCrossValidation.setAccuracy(AccuracyRandomForestCrossValidation);
        randomForestMethodCrossValidation.setSensivity(SensivityRandomForestCrossValidation);
        randomForestMethodCrossValidation.setSpecificity(SpecificityRandomForestCrossValidation);

        methodService.addMethod(dataset_id, randomForestMethodCrossValidation);


    }

    public void addMethodsBagging(Long dataset_id, String fileName, String projectDir) throws InterruptedException{

        List<Thread> threads = new ArrayList<>();

        DTBagging decisionTreeClassifierBagging = new DTBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierBagging);

        SVMBagging svmClassifierBagging = new SVMBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmClassifierBagging);

        KNNBagging knnClassifierBagging = new KNNBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(knnClassifierBagging);

        RandomForestBagging randomForestClassifierBagging = new RandomForestBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(randomForestClassifierBagging);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        addDecisionTreeBagging(dataset_id, decisionTreeClassifierBagging);
        addSVMBagging(dataset_id, svmClassifierBagging);
        addKNNBagging(dataset_id, knnClassifierBagging);
        addRandomForestBagging(dataset_id, randomForestClassifierBagging);

    }


    public void addDecisionTreeBagging(Long dataset_id, DTBagging decisionTreeClassifierBagging){

        Double F1scoreDTBagging= decisionTreeClassifierBagging.getF1Score();
        Double AccuracyDTBagging = decisionTreeClassifierBagging.getAccuracy();
        Double SensivityDTBagging = decisionTreeClassifierBagging.getSensivity();
        Double SpecificityDTBagging = decisionTreeClassifierBagging.getSpecificity();

        Double weightedResultDTBagging = (F1scoreDTBagging + AccuracyDTBagging + SensivityDTBagging + SpecificityDTBagging)/4;

        Method decisionTreeMethodBagging = new Method();

        decisionTreeMethodBagging.setMethodName("Decision tree");
        decisionTreeMethodBagging.setResult(weightedResultDTBagging);
        decisionTreeMethodBagging.setSplitName("Bagging");
        decisionTreeMethodBagging.setF1Score(F1scoreDTBagging);
        decisionTreeMethodBagging.setAccuracy(AccuracyDTBagging);
        decisionTreeMethodBagging.setSensivity(SensivityDTBagging);
        decisionTreeMethodBagging.setSpecificity(SpecificityDTBagging);

        methodService.addMethod(dataset_id, decisionTreeMethodBagging);
    }

    public void addSVMBagging(Long dataset_id, SVMBagging svmClassifierBagging){


        Double F1scoreSVMBagging= svmClassifierBagging.getF1Score();
        Double AccuracySVMBagging = svmClassifierBagging.getAccuracy();
        Double SensivitySVMBagging = svmClassifierBagging.getSensivity();
        Double SpecificitySVMBagging = svmClassifierBagging.getSpecificity();

        Double weightedResultSVMBagging = (F1scoreSVMBagging + AccuracySVMBagging + SensivitySVMBagging + SpecificitySVMBagging)/4;

        Method SVMMethodBagging = new Method();

        SVMMethodBagging.setMethodName("SVM");
        SVMMethodBagging.setResult(weightedResultSVMBagging);
        SVMMethodBagging.setSplitName("Bagging");
        SVMMethodBagging.setF1Score(F1scoreSVMBagging);
        SVMMethodBagging.setAccuracy(AccuracySVMBagging);
        SVMMethodBagging.setSensivity(SensivitySVMBagging);
        SVMMethodBagging.setSpecificity(SpecificitySVMBagging);

        methodService.addMethod(dataset_id, SVMMethodBagging);
    }

    public void addKNNBagging(Long dataset_id,  KNNBagging knnClassifierBagging ){

        Double F1scoreKNNBagging= knnClassifierBagging.getF1Score();
        Double AccuracyKNNBagging = knnClassifierBagging.getAccuracy();
        Double SensivityKNNBagging = knnClassifierBagging.getSensivity();
        Double SpecificityKNNBagging = knnClassifierBagging.getSpecificity();

        Double weightedResultKNNBagging = (F1scoreKNNBagging + AccuracyKNNBagging + SensivityKNNBagging + SpecificityKNNBagging)/4;


        Method KNNMethodBagging = new Method();

        KNNMethodBagging.setMethodName("K nearest neighbour");
        KNNMethodBagging.setResult(weightedResultKNNBagging);
        KNNMethodBagging.setSplitName("Bagging");
        KNNMethodBagging.setF1Score(F1scoreKNNBagging);
        KNNMethodBagging.setAccuracy(AccuracyKNNBagging);
        KNNMethodBagging.setSensivity(SensivityKNNBagging);
        KNNMethodBagging.setSpecificity(SpecificityKNNBagging);

        methodService.addMethod(dataset_id,KNNMethodBagging);
    }

    public void addRandomForestBagging(Long dataset_id, RandomForestBagging randomForestClassifierBagging){

        Double F1scoreRandomForestBagging= randomForestClassifierBagging.getF1Score();
        Double AccuracyRandomForestBagging = randomForestClassifierBagging.getAccuracy();
        Double SensivityRandomForestBagging = randomForestClassifierBagging.getSensivity();
        Double SpecificityRandomForestBagging = randomForestClassifierBagging.getSpecificity();

        Double weightedResultRandomForestBagging = (F1scoreRandomForestBagging + AccuracyRandomForestBagging + SensivityRandomForestBagging + SpecificityRandomForestBagging)/4;

        Method RandomForestMethodBagging = new Method();

        RandomForestMethodBagging.setMethodName("Random Forest");
        RandomForestMethodBagging.setResult(weightedResultRandomForestBagging);
        RandomForestMethodBagging.setSplitName("Bagging");
        RandomForestMethodBagging.setF1Score(F1scoreRandomForestBagging);
        RandomForestMethodBagging.setAccuracy(AccuracyRandomForestBagging);
        RandomForestMethodBagging.setSensivity(SensivityRandomForestBagging);
        RandomForestMethodBagging.setSpecificity(SpecificityRandomForestBagging);

        methodService.addMethod(dataset_id, RandomForestMethodBagging);
    }
}
