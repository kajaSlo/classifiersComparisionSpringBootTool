import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { MethodService } from "src/app/services/method.service";
import { DatasetService } from "src/app/services/dataset.service";

@Component({
  selector: "app-bagging-results-for-dataset",
  templateUrl: "./bagging-results-for-dataset.component.html",
  styleUrls: ["./bagging-results-for-dataset.component.css"]
})
export class BaggingResultsForDatasetComponent implements OnInit {
  methods: any;
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
        .getBaggingMethodsForDataset(this.param)
        .subscribe(method => {
          this.methods = method;

          for (let method of this.methods) {
            method.accuracy = method.accuracy * 100;
            method.sensivity = method.sensivity * 100;
            method.specificity = method.specificity * 100;
            method.f1Score = method.f1Score * 100;
          }
        });
    });

    this.datasetService.getDataset(this.param).subscribe(dataset => {
      this.datasetName = dataset.datasetName;
    });
  }
}
