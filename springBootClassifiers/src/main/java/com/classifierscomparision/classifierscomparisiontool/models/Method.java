package com.classifierscomparision.classifierscomparisiontool.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Method {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String methodName; //eg. decisionTree

    private Double result;

    //ManyToOne with Dataset

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    @JoinColumn(name="dataset_id", updatable = false, nullable = false)
    @JsonIgnore
    private Dataset dataset;

    //OneToMany with SplitResults
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "method")
    private List<SplitResults> splitResults= new ArrayList<>();


    public Method() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

    public Dataset getDataset() {
        return dataset;
    }

    public void setDataset(Dataset dataset) {
        this.dataset = dataset;
    }

    public List<SplitResults> getSplitResults() {
        return splitResults;
    }

    public void setSplitResults(List<SplitResults> splitResults) {
        this.splitResults = splitResults;
    }
}
