package com.classifierscomparision.classifierscomparisiontool.models;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class SplitResults {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String splitName;
    private double F1Score;
    private double accuracy;
    private double sensivity;
    private double specificity;


    //ManyToOne with Method
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="method_id", updatable = false, nullable = false)
    @JsonIgnore
    private Method method;

    public SplitResults() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public double getF1Score() {
        return F1Score;
    }

    public void setF1Score(double f1Score) {
        F1Score = f1Score;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(double accuracy) {
        this.accuracy = accuracy;
    }

    public double getSensivity() {
        return sensivity;
    }

    public void setSensivity(double sensivity) {
        this.sensivity = sensivity;
    }

    public double getSpecificity() {
        return specificity;
    }

    public void setSpecificity(double specificity) {
        this.specificity = specificity;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public String getSplitName() {
        return splitName;
    }

    public void setSplitName(String splitName) {
        this.splitName = splitName;
    }
}
