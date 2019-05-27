import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { MethodService } from "src/app/services/method.service";

@Component({
  selector: "app-results-for-uploaded-file",
  templateUrl: "./results-for-uploaded-file.component.html",
  styleUrls: ["./results-for-uploaded-file.component.css"]
})
export class ResultsForUploadedFileComponent implements OnInit {
  method: any;
  param: any;
  constructor(
    private router: Router,
    private route: ActivatedRoute,
    private methodService: MethodService
  ) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.param = params["id"];

      this.methodService.getMethodsForDataset(this.param).subscribe(method => {
        this.method = method;
      });
    });
  }
}
