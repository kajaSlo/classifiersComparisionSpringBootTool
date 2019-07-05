package com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation;

import com.classifierscomparision.classifierscomparisiontool.classifiers.DefaultDataSupplier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.core.Instances;

import java.util.Random;


public class SVMCrossValidation extends Thread implements DefaultDataSupplier {

    private volatile Double F1score;
    private volatile Double Accuracy;
    private volatile Double Sensivity;
    private volatile Double Specificity;

    String datasetDirectory= "";

    public LibSVM buildmodel(Instances dataset){

        dataset.setClassIndex(dataset.numAttributes()-1);

        return new  LibSVM ();
    }

    public void makeEvaluation(Instances dataset, LibSVM model) throws Exception{
        Evaluation evaluation = new Evaluation(dataset);

        evaluation.crossValidateModel(model, dataset, 10 , new Random(1));


        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();

        this.F1score=F1Score;
        this.Accuracy=accuracy;
        this.Sensivity=sensivity;
        this.Specificity=specificity;
    }

    public SVMCrossValidation(String datasetDirectory) {
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


            LibSVM model = buildmodel(dataset);
            model.buildClassifier(dataset);


            makeEvaluation(dataset, model);
            System.out.println("\n");


        }catch(Exception e){
            System.out.println(e);
        }

    }
}