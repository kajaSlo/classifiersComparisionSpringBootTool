import { Component, OnInit } from "@angular/core";
import { DatasetService } from "src/app/services/dataset.service";

@Component({
  selector: "app-main-page",
  templateUrl: "./main-page.component.html",
  styleUrls: ["./main-page.component.css"]
})
export class MainPageComponent implements OnInit {
  datasets: any;
  successMsg: boolean = false;
  errorMsg: boolean = false;
  constructor(private datasetService: DatasetService) {}

  ngOnInit() {
    this.datasetService.getAllDatasets().subscribe(datasets => {
      this.datasetService.datasetBS.next(datasets);
      this.datasets = this.datasetService.datasetBS;
    });
  }

  deleteDataset(id) {
    if (confirm("Confirm deletion")) {
      this.datasetService.deleteDatasetAndItsResults(id).subscribe(res => {
        if (res == "Dataset deleted succesfully") {
          this.successMsg = true;
          setTimeout(
            function() {
              this.successMsg = false;
            }.bind(this),
            2000
          );
          this.datasetService.getAllDatasets().subscribe(datasets => {
            this.datasetService.datasetBS.next(datasets);
          });
        } else {
          this.errorMsg = true;
          setTimeout(
            function() {
              this.errorMsg = false;
            }.bind(this),
            2000
          );
        }
      });
    }
  }
}
