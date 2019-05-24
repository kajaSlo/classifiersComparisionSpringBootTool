package com.classifierscomparision.classifierscomparisiontool.services;

import com.classifierscomparision.classifierscomparisiontool.models.Method;
import com.classifierscomparision.classifierscomparisiontool.services.DatasetService;
import com.classifierscomparision.classifierscomparisiontool.services.MethodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

@Service
public class MethodSupplierService {

    @Autowired
    private MethodService methodService;

    public void addMethodsForDataset(Long dataset_id){
        Method decisionTreeMethod = new Method();

        decisionTreeMethod.setMethodName("Decision tree");
        decisionTreeMethod.setResult(0.99999);

        Method SVMMethod = new Method();
        SVMMethod.setMethodName("SVM");
        SVMMethod.setResult(0.12345);

        Method DTMethod = methodService.addMethod(dataset_id, decisionTreeMethod);

        Method sVMMethod = methodService.addMethod(dataset_id, SVMMethod);
    }
}
