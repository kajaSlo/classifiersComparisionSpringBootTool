package com.classifierscomparision.classifierscomparisiontool.classifiers.crossValidation;

import weka.core.Instances;
import weka.core.converters.CSVLoader;

import java.io.File;

public interface DefaultDataSupplier {
    default public Instances getDataset(String datasetDirectory) throws Exception{

        CSVLoader loader = new CSVLoader();
        // loader.setSource(new File("/home/kaja/Pulpit/Praca magisterska/iris.csv"));
        loader.setSource(new File(datasetDirectory));
        String[] options = new String[1];
        options[0] = "-H";
        loader.setOptions(options);
        return loader.getDataSet();
    }
}