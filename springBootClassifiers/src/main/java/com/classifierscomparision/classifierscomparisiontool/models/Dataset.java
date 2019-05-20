package com.classifierscomparision.classifierscomparisiontool.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Dataset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    @NotBlank(message = "Dataset name is required")
    private String datasetName;

    //OneToMany with Method
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "dataset")
    private List<Method> datasetMethods = new ArrayList<>();

    public Dataset() {
    }

    public Dataset(@NotBlank(message = "Dataset name is required") String datasetName) {
        this.datasetName = datasetName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDatasetName() {
        return datasetName;
    }

    public void setDatasetName(String datasetName) {
        this.datasetName = datasetName;
    }

    public List<Method> getDatasetMethods() {
        return datasetMethods;
    }

    public void setDatasetMethods(List<Method> datasetMethods) {
        this.datasetMethods = datasetMethods;
    }
}
