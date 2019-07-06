package com.classifierscomparision.classifierscomparisiontool.classifiers.AdaBoost;

import com.classifierscomparision.classifierscomparisiontool.classifiers.DefaultDataSupplier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.AdaBoostM1;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;


public class SVMBoosting extends Thread implements DefaultDataSupplier {

    private volatile Double F1score;
    private volatile Double Accuracy;
    private volatile Double Sensivity;
    private volatile Double Specificity;

    String datasetDirectory= "";

    public void makeEvaluation(Instances dataset, AdaBoostM1 m1) throws Exception{
        Evaluation evaluation = new Evaluation(dataset);

        evaluation.evaluateModel(m1, dataset);

        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();

        this.F1score=F1Score;
        this.Accuracy=accuracy;
        this.Sensivity=sensivity;
        this.Specificity=specificity;
    }

    public SVMBoosting(String datasetDirectory) {
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

            System.out.println("Inside LibSVM classifier");


            dataset.setClassIndex(dataset.numAttributes()-1);

            AdaBoostM1 m1 = new AdaBoostM1();


            LibSVM model = new LibSVM();
            m1.setClassifier(model);
            m1.setNumIterations(25);
            m1.buildClassifier(dataset);
            model.buildClassifier(dataset);

            makeEvaluation(dataset, m1);
            System.out.println("\n");


        }catch(Exception e){
            System.out.println(e);
        }

    }
}