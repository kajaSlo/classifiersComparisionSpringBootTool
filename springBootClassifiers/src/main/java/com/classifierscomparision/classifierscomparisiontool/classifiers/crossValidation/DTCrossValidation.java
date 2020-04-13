package com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation;

import java.util.Random;
import com.classifierscomparision.classifierscomparisiontool.classifiers.DefaultDataSupplier;
import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;

public class DTCrossValidation extends Thread implements DefaultDataSupplier {

    private volatile Double F1score;
    private volatile Double Accuracy;
    private volatile Double Sensivity;
    private volatile Double Specificity;

    String datasetDirectory= "";

    public J48 buildmodel(Instances dataset){

        dataset.setClassIndex(dataset.numAttributes()-1);

        return new J48();
    }

    public void makeEvaluation(Instances dataset, J48 model) throws Exception{
        Evaluation evaluation = new Evaluation(dataset);

        model.setNumFolds(10);
        evaluation.crossValidateModel(model, dataset, model.getNumFolds(), new Random(1));

        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();

        this.F1score=F1Score;
        this.Accuracy=accuracy;
        this.Sensivity=sensivity;
        this.Specificity=specificity;
    }

    public DTCrossValidation(String datasetDirectory) {
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

            J48 model = buildmodel(dataset);

            makeEvaluation(dataset, model);

        }catch(Exception e){
            System.out.println(e);
        }
    }
}
