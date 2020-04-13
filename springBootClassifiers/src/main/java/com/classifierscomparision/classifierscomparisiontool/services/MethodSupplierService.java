package com.classifierscomparision.classifierscomparisiontool.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost.DTBoosting;
import com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost.KNNBoosting;
import com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost.MultilayerPerceptronBoosting;
import com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost.NaiveBayersBoosting;
import com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost.RandomForestBoosting;
import com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost.SVMBoosting;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.DTBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.KNNBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.MultilayerPerceptronBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.NaiveBayersBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.RandomForestBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.bagging.SVMBagging;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.DTCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.KNNCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.MultiLayerPerceptionCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.NaiveBayersCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.RandomForestCrossVal;
import com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation.SVMCrossValidation;
import com.classifierscomparision.classifierscomparisiontool.models.Method;
import javax.transaction.Transactional;

@Transactional
@Service
public class MethodSupplierService {

    @Autowired
    private MethodService methodService;

    public void addMethodsForDataset(Long dataset_id, String fileName) throws InterruptedException {

        String projectDir = new File(System.getProperty("user.dir")).getParentFile().toString();

        addMethodsCrossValidation(dataset_id, fileName, projectDir);
        addMethodsBagging(dataset_id, fileName, projectDir);
        addMethodsBoosting(dataset_id, fileName, projectDir);
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

        NaiveBayersCrossValidation naiveBayersClassifierCrossValidation = new NaiveBayersCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(naiveBayersClassifierCrossValidation);
        
        MultiLayerPerceptionCrossValidation MLPClassifierCrossValidation = new MultiLayerPerceptionCrossValidation(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(MLPClassifierCrossValidation);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        addDecisionTreeCrossValidation(dataset_id, decisionTreeClassifierCrossValidation);
        addSVMCrossValidation(dataset_id, svmCrossValidation);
        addKNNCrossValidation(dataset_id, knnClassifierCrossValidation);
        addRandomForestCrossValidation(dataset_id, randomForestClassifierCrossValidation);
        addNaiveBayersCrossValidation(dataset_id, naiveBayersClassifierCrossValidation);
        addMLPCrossValidation(dataset_id, MLPClassifierCrossValidation);
    }

    public void addDecisionTreeCrossValidation(Long dataset_id, DTCrossValidation decisionTreeClassifierCrossValidation){

        Double F1scoreDTCrossValidation = decisionTreeClassifierCrossValidation.getF1Score();
        Double AccuracyDTCrossValidation = decisionTreeClassifierCrossValidation.getAccuracy();
        Double SensivityDTCrossValidation = decisionTreeClassifierCrossValidation.getSensivity();
        Double SpecificityDTCrossValidation = decisionTreeClassifierCrossValidation.getSpecificity();

        Method decisionTreeMethodCrossValidation = new Method();

        decisionTreeMethodCrossValidation.setMethodName("Decision tree");
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

        Method SVMMethodCrossValidation = new Method();

        SVMMethodCrossValidation.setMethodName("SVM");
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

        Method KNNMethodCrossValidation = new Method();

        KNNMethodCrossValidation.setMethodName("K nearest neighbour");
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

        Method randomForestMethodCrossValidation = new Method();

        randomForestMethodCrossValidation.setMethodName("Random Forest");
        randomForestMethodCrossValidation.setSplitName("CrossValidation");
        randomForestMethodCrossValidation.setF1Score(F1scoreRandomForestCrossValidation);
        randomForestMethodCrossValidation.setAccuracy(AccuracyRandomForestCrossValidation);
        randomForestMethodCrossValidation.setSensivity(SensivityRandomForestCrossValidation);
        randomForestMethodCrossValidation.setSpecificity(SpecificityRandomForestCrossValidation);

        methodService.addMethod(dataset_id, randomForestMethodCrossValidation);
    }
  
    public void addNaiveBayersCrossValidation(Long dataset_id, NaiveBayersCrossValidation naiveBayersClassifierCrossValidation){

        Double F1scoreNaiveBayersCrossValidation = naiveBayersClassifierCrossValidation.getF1Score();
        Double AccuracyNaiveBayersCrossValidation = naiveBayersClassifierCrossValidation.getAccuracy();
        Double SensivityNaiveBayersCrossValidation = naiveBayersClassifierCrossValidation.getSensivity();
        Double SpecificityNaiveBayersCrossValidation = naiveBayersClassifierCrossValidation.getSpecificity();

        Method naiveBayersMethodCrossValidation = new Method();

        naiveBayersMethodCrossValidation.setMethodName("Naive Bayers");
        naiveBayersMethodCrossValidation.setSplitName("CrossValidation");
        naiveBayersMethodCrossValidation.setF1Score(F1scoreNaiveBayersCrossValidation);
        naiveBayersMethodCrossValidation.setAccuracy(AccuracyNaiveBayersCrossValidation);
        naiveBayersMethodCrossValidation.setSensivity(SensivityNaiveBayersCrossValidation);
        naiveBayersMethodCrossValidation.setSpecificity(SpecificityNaiveBayersCrossValidation);

        methodService.addMethod(dataset_id, naiveBayersMethodCrossValidation);
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

        NaiveBayersBagging naiveBayersClassifierBagging = new NaiveBayersBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(naiveBayersClassifierBagging);
        
        MultilayerPerceptronBagging multilayerPerceptronClassifierBagging = new MultilayerPerceptronBagging(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(multilayerPerceptronClassifierBagging);
        
        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        addDecisionTreeBagging(dataset_id, decisionTreeClassifierBagging);
        addSVMBagging(dataset_id, svmClassifierBagging);
        addKNNBagging(dataset_id, knnClassifierBagging);
        addRandomForestBagging(dataset_id, randomForestClassifierBagging);
        addNaiveBayersBagging(dataset_id, naiveBayersClassifierBagging);
        addMLPBagging(dataset_id, multilayerPerceptronClassifierBagging);
    }


    public void addDecisionTreeBagging(Long dataset_id, DTBagging decisionTreeClassifierBagging){

        Double F1scoreDTBagging= decisionTreeClassifierBagging.getF1Score();
        Double AccuracyDTBagging = decisionTreeClassifierBagging.getAccuracy();
        Double SensivityDTBagging = decisionTreeClassifierBagging.getSensivity();
        Double SpecificityDTBagging = decisionTreeClassifierBagging.getSpecificity();

        Method decisionTreeMethodBagging = new Method();

        decisionTreeMethodBagging.setMethodName("Decision tree");
        decisionTreeMethodBagging.setSplitName("Bagging");
        decisionTreeMethodBagging.setF1Score(F1scoreDTBagging);
        decisionTreeMethodBagging.setAccuracy(AccuracyDTBagging);
        decisionTreeMethodBagging.setSensivity(SensivityDTBagging);
        decisionTreeMethodBagging.setSpecificity(SpecificityDTBagging);

        methodService.addMethod(dataset_id, decisionTreeMethodBagging);
    }

    public void addMLPCrossValidation(Long dataset_id, MultiLayerPerceptionCrossValidation MLPClassifierCrossValidation){
		
	    Double F1scoreMLPCrossValidation = MLPClassifierCrossValidation.getF1Score();
	    Double AccuracyMLPCrossValidation = MLPClassifierCrossValidation.getAccuracy();
	    Double SensivityMLPCrossValidation = MLPClassifierCrossValidation.getSensivity();
	    Double SpecificityMLPCrossValidation = MLPClassifierCrossValidation.getSpecificity();
	
	    Method MLPMethodCrossValidation = new Method();
	
	    MLPMethodCrossValidation.setMethodName("Multilayer Perceptor");
	    MLPMethodCrossValidation.setSplitName("CrossValidation");
	    MLPMethodCrossValidation.setF1Score(F1scoreMLPCrossValidation);
	    MLPMethodCrossValidation.setAccuracy(AccuracyMLPCrossValidation);
	    MLPMethodCrossValidation.setSensivity(SensivityMLPCrossValidation);
	    MLPMethodCrossValidation.setSpecificity(SpecificityMLPCrossValidation);
	
	    methodService.addMethod(dataset_id, MLPMethodCrossValidation);
		
	}

	public void addSVMBagging(Long dataset_id, SVMBagging svmClassifierBagging){

        Double F1scoreSVMBagging= svmClassifierBagging.getF1Score();
        Double AccuracySVMBagging = svmClassifierBagging.getAccuracy();
        Double SensivitySVMBagging = svmClassifierBagging.getSensivity();
        Double SpecificitySVMBagging = svmClassifierBagging.getSpecificity();

        Method SVMMethodBagging = new Method();

        SVMMethodBagging.setMethodName("SVM");
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

        Method KNNMethodBagging = new Method();

        KNNMethodBagging.setMethodName("K nearest neighbour");
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

        Method RandomForestMethodBagging = new Method();

        RandomForestMethodBagging.setMethodName("Random Forest");
        RandomForestMethodBagging.setSplitName("Bagging");
        RandomForestMethodBagging.setF1Score(F1scoreRandomForestBagging);
        RandomForestMethodBagging.setAccuracy(AccuracyRandomForestBagging);
        RandomForestMethodBagging.setSensivity(SensivityRandomForestBagging);
        RandomForestMethodBagging.setSpecificity(SpecificityRandomForestBagging);

        methodService.addMethod(dataset_id, RandomForestMethodBagging);
    }

    public void addNaiveBayersBagging(Long dataset_id, NaiveBayersBagging naiveBayersClassifierBagging){

        Double F1scoreNaiveBayersBagging= naiveBayersClassifierBagging.getF1Score();
        Double AccuracyNaiveBayersBagging = naiveBayersClassifierBagging.getAccuracy();
        Double SensivityNaiveBayersBagging = naiveBayersClassifierBagging.getSensivity();
        Double SpecificityNaiveBayersBagging = naiveBayersClassifierBagging.getSpecificity();

        Method naiveBayersMethodBagging = new Method();

        naiveBayersMethodBagging.setMethodName("Naive Bayers");
        naiveBayersMethodBagging.setSplitName("Bagging");
        naiveBayersMethodBagging.setF1Score(F1scoreNaiveBayersBagging);
        naiveBayersMethodBagging.setAccuracy(AccuracyNaiveBayersBagging);
        naiveBayersMethodBagging.setSensivity(SensivityNaiveBayersBagging);
        naiveBayersMethodBagging.setSpecificity(SpecificityNaiveBayersBagging);

        methodService.addMethod(dataset_id,naiveBayersMethodBagging);
    }

    public void addMLPBagging(Long dataset_id, MultilayerPerceptronBagging multilayerPerceptronClassifierBagging){	
    	
    	Double F1scoreMultilayerPerceptronBagging= multilayerPerceptronClassifierBagging.getF1Score();
        Double AccuracyMultilayerPerceptronBagging = multilayerPerceptronClassifierBagging.getAccuracy();
        Double SensivityMultilayerPerceptronBagging = multilayerPerceptronClassifierBagging.getSensivity();
        Double SpecificityMultilayerPerceptronBagging = multilayerPerceptronClassifierBagging.getSpecificity();

        Method MultilayerPerceptronMethodBagging = new Method();

        MultilayerPerceptronMethodBagging.setMethodName("Multilayer Perceptor");
        MultilayerPerceptronMethodBagging.setSplitName("Bagging");
        MultilayerPerceptronMethodBagging.setF1Score(F1scoreMultilayerPerceptronBagging);
        MultilayerPerceptronMethodBagging.setAccuracy(AccuracyMultilayerPerceptronBagging);
        MultilayerPerceptronMethodBagging.setSensivity(SensivityMultilayerPerceptronBagging);
        MultilayerPerceptronMethodBagging.setSpecificity(SpecificityMultilayerPerceptronBagging);

        methodService.addMethod(dataset_id,MultilayerPerceptronMethodBagging);
    }

    private void addMethodsBoosting(Long dataset_id, String fileName, String projectDir) throws InterruptedException{

        List<Thread> threads = new ArrayList<>();

        DTBoosting decisionTreeClassifierBoosting = new DTBoosting(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(decisionTreeClassifierBoosting);

        SVMBoosting svmBoosting = new SVMBoosting(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(svmBoosting);

        KNNBoosting knnClassifierBoosting = new KNNBoosting(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(knnClassifierBoosting);

        RandomForestBoosting randomForestClassifierBoosting = new RandomForestBoosting(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(randomForestClassifierBoosting);

        NaiveBayersBoosting naiveBayersBoosting = new NaiveBayersBoosting(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(naiveBayersBoosting);
        
        MultilayerPerceptronBoosting MLPClassifierBoosting = new MultilayerPerceptronBoosting(projectDir + "/datasets/CSVDatasets/" + fileName);
        threads.add(MLPClassifierBoosting);

        for (Thread thread : threads) {
            thread.start();
            thread.join();
        }

        addDecisionTreeBoosting(dataset_id, decisionTreeClassifierBoosting);
        addSVMBoosting(dataset_id, svmBoosting);
        addKNNBoosting(dataset_id, knnClassifierBoosting);
        addRandomForestBoosting(dataset_id, randomForestClassifierBoosting);
        addNaiveBayersBoosting(dataset_id, naiveBayersBoosting);
        addMLPBoosting(dataset_id, MLPClassifierBoosting);
    }

    public void addDecisionTreeBoosting(Long dataset_id, DTBoosting decisionTreeClassifierBoosting){

        Double F1scoreDTBoosting = decisionTreeClassifierBoosting.getF1Score();
        Double AccuracyDTBoosting = decisionTreeClassifierBoosting.getAccuracy();
        Double SensivityDTBoosting = decisionTreeClassifierBoosting.getSensivity();
        Double SpecificityDTBoosting = decisionTreeClassifierBoosting.getSpecificity();

        Method decisionTreeMethodBoosting = new Method();

        decisionTreeMethodBoosting.setMethodName("Decision tree");
        decisionTreeMethodBoosting.setSplitName("Boosting");
        decisionTreeMethodBoosting.setF1Score(F1scoreDTBoosting);
        decisionTreeMethodBoosting.setAccuracy(AccuracyDTBoosting);
        decisionTreeMethodBoosting.setSensivity(SensivityDTBoosting);
        decisionTreeMethodBoosting.setSpecificity(SpecificityDTBoosting);

        methodService.addMethod(dataset_id, decisionTreeMethodBoosting);
    }

   public void addSVMBoosting(Long dataset_id, SVMBoosting svmBoosting){

       Double F1scoreSVMBoosting= svmBoosting.getF1Score();
       Double AccuracySVMBoosting = svmBoosting.getAccuracy();
       Double SensivitySVMBoosting= svmBoosting.getSensivity();
       Double SpecificitySVMBoosting = svmBoosting.getSpecificity();

       Method SVMMethodBoosting = new Method();

       SVMMethodBoosting.setMethodName("SVM");
       SVMMethodBoosting.setSplitName("Boosting");
       SVMMethodBoosting.setF1Score(F1scoreSVMBoosting);
       SVMMethodBoosting.setAccuracy(AccuracySVMBoosting);
       SVMMethodBoosting.setSensivity(SensivitySVMBoosting);
       SVMMethodBoosting.setSpecificity(SpecificitySVMBoosting);

       methodService.addMethod(dataset_id, SVMMethodBoosting);
    }

   public void  addKNNBoosting(Long dataset_id, KNNBoosting knnClassifierBoosting){

       Double F1scoreKNNBoosting= knnClassifierBoosting.getF1Score();
       Double AccuracyKNNBoosting = knnClassifierBoosting.getAccuracy();
       Double SensivityKNNBoosting = knnClassifierBoosting.getSensivity();
       Double SpecificityKNNBoosting = knnClassifierBoosting.getSpecificity();

       Method KNNMethodBoosting = new Method();

       KNNMethodBoosting.setMethodName("K nearest neighbour");
       KNNMethodBoosting.setSplitName("Boosting");
       KNNMethodBoosting.setF1Score(F1scoreKNNBoosting);
       KNNMethodBoosting.setAccuracy(AccuracyKNNBoosting);
       KNNMethodBoosting.setSensivity(SensivityKNNBoosting);
       KNNMethodBoosting.setSpecificity(SpecificityKNNBoosting);

       methodService.addMethod(dataset_id,KNNMethodBoosting);
   }

    public void addRandomForestBoosting(Long dataset_id, RandomForestBoosting randomForestClassifierBoosting){

        Double F1scoreRandomForestBoosting = randomForestClassifierBoosting.getF1Score();
        Double AccuracyRandomForestBoosting = randomForestClassifierBoosting.getAccuracy();
        Double SensivityRandomForestBoosting = randomForestClassifierBoosting.getSensivity();
        Double SpecificityRandomForestBoosting = randomForestClassifierBoosting.getSpecificity();

        Method RandomForestMethodBoosting = new Method();

        RandomForestMethodBoosting.setMethodName("Random Forest");
        RandomForestMethodBoosting.setSplitName("Boosting");
        RandomForestMethodBoosting.setF1Score(F1scoreRandomForestBoosting);
        RandomForestMethodBoosting.setAccuracy(AccuracyRandomForestBoosting);
        RandomForestMethodBoosting.setSensivity(SensivityRandomForestBoosting);
        RandomForestMethodBoosting.setSpecificity(SpecificityRandomForestBoosting);

        methodService.addMethod(dataset_id, RandomForestMethodBoosting);
    }

    public void addNaiveBayersBoosting(Long dataset_id, NaiveBayersBoosting naiveBayersBoosting){

        Double F1scoreNaiveBayersBoosting= naiveBayersBoosting.getF1Score();
        Double AccuracyNaiveBayersBoosting = naiveBayersBoosting.getAccuracy();
        Double SensivityNaiveBayersBoosting = naiveBayersBoosting.getSensivity();
        Double SpecificityNaiveBayersBoosting = naiveBayersBoosting.getSpecificity();

        Method naiveBayersMethodBoosting = new Method();

        naiveBayersMethodBoosting.setMethodName("Naive Bayers");
        naiveBayersMethodBoosting.setSplitName("Boosting");
        naiveBayersMethodBoosting.setF1Score(F1scoreNaiveBayersBoosting);
        naiveBayersMethodBoosting.setAccuracy(AccuracyNaiveBayersBoosting);
        naiveBayersMethodBoosting.setSensivity(SensivityNaiveBayersBoosting);
        naiveBayersMethodBoosting.setSpecificity(SpecificityNaiveBayersBoosting);

        methodService.addMethod(dataset_id,naiveBayersMethodBoosting);
    }
    
    public void addMLPBoosting(Long dataset_id, MultilayerPerceptronBoosting MLPClassifierBoosting){
    	
    	Double F1scoreMultilayerPerceptronBoosting= MLPClassifierBoosting.getF1Score();
        Double AccuracyMultilayerPerceptronBoosting = MLPClassifierBoosting.getAccuracy();
        Double SensivityMultilayerPerceptronBoosting = MLPClassifierBoosting.getSensivity();
        Double SpecificityMultilayerPerceptronBoosting = MLPClassifierBoosting.getSpecificity();

        Method multilayerPerceptronMethodBoosting = new Method();

        multilayerPerceptronMethodBoosting.setMethodName("Multilayer Perceptor");
        multilayerPerceptronMethodBoosting.setSplitName("Boosting");
        multilayerPerceptronMethodBoosting.setF1Score(F1scoreMultilayerPerceptronBoosting);
        multilayerPerceptronMethodBoosting.setAccuracy(AccuracyMultilayerPerceptronBoosting);
        multilayerPerceptronMethodBoosting.setSensivity(SensivityMultilayerPerceptronBoosting);
        multilayerPerceptronMethodBoosting.setSpecificity(SpecificityMultilayerPerceptronBoosting);

        methodService.addMethod(dataset_id,multilayerPerceptronMethodBoosting);   	
    }
}
