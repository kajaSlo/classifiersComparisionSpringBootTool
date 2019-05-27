package com.classifierscomparision.classifierscomparisiontool.classifiers;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LibSVM;

import weka.core.Instances;



public class SVMLibSVM extends Thread implements DefaultDataSupplier{

    private volatile double result;

    String datasetDirectory= "";

    public LibSVM buildmodel(Instances dataset){

        dataset.setClassIndex(dataset.numAttributes()-1);

        return new  LibSVM ();
    }

    public void makeEvaluation(Instances dataset, LibSVM model) throws Exception{
        Evaluation evaluation = new Evaluation(dataset);

        evaluation.evaluateModel(model, dataset);

        Double F1Score = evaluation.weightedFMeasure();
        Double accuracy = evaluation.pctCorrect()/100;
        Double sensivity = evaluation.weightedRecall();
        Double specificity = evaluation.weightedTrueNegativeRate();

        Double weightedResult = (F1Score + accuracy + sensivity + specificity)/4;
        this.result = weightedResult;
    }

    public SVMLibSVM(String datasetDirectory) {
        this.datasetDirectory = datasetDirectory;
    }

    public double getResult() {
        return result;
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