import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MethodService } from "src/app/services/method.service";
import { DatasetService } from "src/app/services/dataset.service";

@Component({
  selector: "app-cross-validation-results-for-dataset",
  templateUrl: "./cross-validation-results-for-dataset.component.html",
  styleUrls: ["./cross-validation-results-for-dataset.component.css"]
})
export class CrossValidationResultsForDatasetComponent implements OnInit {
  method: any;
  param: any;
  datasetName: any;

  constructor(
    private route: ActivatedRoute,
    private methodService: MethodService,
    private datasetService: DatasetService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.param = params["id"];

      this.methodService
        .getCrossValidationMethodsForDataset(this.param)
        .subscribe(method => {
          this.method = method;

          for (let methods of this.method) {
            methods.accuracy = methods.accuracy * 100;
            methods.sensivity = methods.sensivity * 100;
            methods.specificity = methods.specificity * 100;
            methods.f1Score = methods.f1Score * 100;
          }
        });
    });

    this.datasetService.getDataset(this.param).subscribe(dataset => {
      this.datasetName = dataset.datasetName;
    });
  }
}
