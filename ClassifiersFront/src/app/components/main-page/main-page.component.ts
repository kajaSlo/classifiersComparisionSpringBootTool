import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { DatasetService } from "src/app/services/dataset.service";

@Component({
  selector: "app-main-page",
  templateUrl: "./main-page.component.html",
  styleUrls: ["./main-page.component.css"]
})
export class MainPageComponent implements OnInit {
  datasets: any;
  constructor(private router: Router, private datasetService: DatasetService) {}

  ngOnInit() {
    this.datasetService.getAllDatasets().subscribe(datasets => {
      this.datasetService.datasetBS.next(datasets);
      this.datasets = this.datasetService.datasetBS;

      console.log(datasets);
    });
  }
}
