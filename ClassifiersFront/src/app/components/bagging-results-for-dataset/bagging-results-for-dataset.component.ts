import { Component, OnInit } from "@angular/core";
import { Router, ActivatedRoute } from "@angular/router";
import { MethodService } from "src/app/services/method.service";
import { DatasetService } from "src/app/services/dataset.service";

@Component({
  selector: "app-bagging-results-for-dataset",
  templateUrl: "./bagging-results-for-dataset.component.html",
  styleUrls: ["./bagging-results-for-dataset.component.css"]
})
export class BaggingResultsForDatasetComponent implements OnInit {
  method: any;
  param: any;
  datasetName: any;

  constructor(
    private router: Router,
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
          this.method = method;
        });
    });

    this.datasetService.getDataset(this.param).subscribe(dataset => {
      this.datasetName = dataset.datasetName;
    });
  }
}
