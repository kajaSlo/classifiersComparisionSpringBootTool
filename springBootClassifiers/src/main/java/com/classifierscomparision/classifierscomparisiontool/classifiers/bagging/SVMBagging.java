package com.classifierscomparision.classifierscomparisiontool.classifiers.bagging;

import com.classifierscomparision.classifierscomparisiontool.classifiers.DefaultDataSupplier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.meta.Bagging;
import weka.core.Instances;

import java.util.Random;

public class SVMBagging extends Thread implements DefaultDataSupplier {

    private volatile Double F1score;
    private volatile Double Accuracy;
    private volatile Double Sensivity;
    private volatile Double Specificity;

    String datasetDirectory= "";

    public void makeEvaluation(Instances dataset, LibSVM model, Bagging bagger) throws Exception{
        Evaluation evaluation = new Evaluation(dataset);

        evaluation.evaluateModel(bagger, dataset);



        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();

        Double weightedResult = (F1Score + accuracy + sensivity + specificity)/4;

        this.F1score=F1Score;
        this.Accuracy=accuracy;
        this.Sensivity=sensivity;
        this.Specificity=specificity;
    }

    public SVMBagging(String datasetDirectory) {
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

            Bagging bagger = new Bagging();

            LibSVM model = new LibSVM();
            bagger.setClassifier(model);
            bagger.setNumIterations(25);
            bagger.buildClassifier(dataset);
            model.buildClassifier(dataset);

            makeEvaluation(dataset, model,bagger);
            System.out.println("\n");


        }catch(Exception e){
            System.out.println(e);
        }

    }
}