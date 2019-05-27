package com.classifierscomparision.classifierscomparisiontool.classifiers;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.J48;
import weka.core.Instances;
import weka.core.converters.ConverterUtils.DataSource;
public class DTCassifier extends Thread implements DefaultDataSupplier{

    private volatile double result;

    String datasetDirectory= "";

    public J48 buildmodel(Instances dataset){

        dataset.setClassIndex(dataset.numAttributes()-1);

        return new J48();
    }

    public void makeEvaluation(Instances dataset, J48 model) throws Exception{
        Evaluation evaluation = new Evaluation(dataset);

        evaluation.evaluateModel(model, dataset);


        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();

        Double weightedResult = (F1Score + accuracy + sensivity + specificity)/4;
        this.result = weightedResult;


    }

    public DTCassifier() {


    }

    public DTCassifier(String datasetDirectory) {
        this.datasetDirectory = datasetDirectory;
    }

    public double getResult() {
        return result;
    }

    @Override
    public void run() {

        try {

            System.out.println("Inside Decision Tree classifier");

            Instances dataset = getDataset(datasetDirectory);

            J48 model = buildmodel(dataset);
            model.buildClassifier(dataset);


            makeEvaluation(dataset, model);
            System.out.println("\n");

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
