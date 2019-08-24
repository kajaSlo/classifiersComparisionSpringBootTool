package com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost;

import com.classifierscomparision.classifierscomparisiontool.classifiers.DefaultDataSupplier;
import weka.classifiers.Evaluation;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.trees.RandomForest;
import weka.core.Debug;
import weka.core.Instances;

public class RandomForestBoosting extends Thread implements DefaultDataSupplier {

    private volatile Double F1score;
    private volatile Double Accuracy;
    private volatile Double Sensivity;
    private volatile Double Specificity;

    String datasetDirectory= "";

    public void makeEvaluation(Instances trainDataset, Instances testDataset, AdaBoostM1 m1) throws Exception{
        Evaluation evaluation = new Evaluation(trainDataset);

        evaluation.evaluateModel(m1, testDataset);

        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();


        this.F1score=F1Score;
        this.Accuracy=accuracy;
        this.Sensivity=sensivity;
        this.Specificity=specificity;
    }

    public RandomForestBoosting(String datasetDirectory) {
        this.datasetDirectory = datasetDirectory;
    }

    public Double getF1Score() {
        return F1score;
    }

    public Double getAccuracy() {
        return Accuracy;
    }

    public Double getSensivity() {
        return Sensivity;
    }

    public Double getSpecificity() {
        return Specificity;
    }

    @Override
    public void run() {
        try {
            Instances dataset = getDataset(datasetDirectory);

            dataset.setClassIndex(dataset.numAttributes()-1);
            
            int trainDatasetSize = (int) Math.round(dataset.numInstances() * 0.7);
            int testDatasetSize = dataset.numInstances() - trainDatasetSize;

            dataset.randomize(new Debug.Random(1));
            
            Instances trainDataset = new Instances(dataset, 0, trainDatasetSize);
            Instances testDataset = new Instances(dataset, trainDatasetSize, testDatasetSize);

            AdaBoostM1 m1 = new AdaBoostM1();

            RandomForest model = new RandomForest();
            model.setNumIterations(20);
            m1.setClassifier(model);
            m1.setNumIterations(25);
            m1.buildClassifier(trainDataset); 

            makeEvaluation(trainDataset, testDataset,m1);

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
