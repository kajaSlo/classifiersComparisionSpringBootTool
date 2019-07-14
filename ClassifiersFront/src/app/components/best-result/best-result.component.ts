import { Component, OnInit } from "@angular/core";
import { MethodService } from "src/app/services/method.service";
import { ActivatedRoute } from "@angular/router";
import { DatasetService } from "src/app/services/dataset.service";

@Component({
  selector: "app-best-result",
  templateUrl: "./best-result.component.html",
  styleUrls: ["./best-result.component.css"]
})
export class BestResultComponent implements OnInit {
  bestResult: any;
  param: String;
  accuracy: any;
  datasetName: String;

  constructor(
    private route: ActivatedRoute,
    private methodService: MethodService,
    private datasetService: DatasetService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.param = params["id"];

      this.methodService
        .getBestResultForDataset(this.param)
        .subscribe(bestResult => {
          bestResult.accuracy = bestResult.accuracy * 100;
          bestResult.sensivity = bestResult.sensivity * 100;
          bestResult.specificity = bestResult.specificity * 100;
          bestResult.f1Score = bestResult.f1Score * 100;
          bestResult.result = bestResult.result * 100;
          this.bestResult = bestResult;
        });
    });

    this.datasetService.getDataset(this.param).subscribe(dataset => {
      this.datasetName = dataset.datasetName;
    });
  }
}
