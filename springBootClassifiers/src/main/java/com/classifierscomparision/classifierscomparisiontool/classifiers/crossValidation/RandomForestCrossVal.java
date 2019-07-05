package com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation;

import com.classifierscomparision.classifierscomparisiontool.classifiers.DefaultDataSupplier;
import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;
import weka.classifiers.trees.RandomForest;
import weka.core.Instances;

import java.util.Random;

public class RandomForestCrossVal extends Thread implements DefaultDataSupplier {

    private volatile Double F1score;
    private volatile Double Accuracy;
    private volatile Double Sensivity;
    private volatile Double Specificity;

    String datasetDirectory= "";

    public RandomForest buildmodel(Instances dataset){

        dataset.setClassIndex(dataset.numAttributes()-1);

        return new  RandomForest ();
    }

    public void makeEvaluation(Instances dataset, RandomForest model) throws Exception{
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

    public RandomForestCrossVal(String datasetDirectory) {
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


            RandomForest model = buildmodel(dataset);
            model.setNumIterations(20); //the same as set num trees
            model.buildClassifier(dataset);


            makeEvaluation(dataset, model);
            System.out.println("\n");


        }catch(Exception e){
            System.out.println(e);
        }

    }
}
