import { Component, OnInit } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import {
  FileUploader,
  FileSelectDirective
} from "ng2-file-upload/ng2-file-upload";

const URL = "http://localhost:8080/api/dataset/upload";
@Component({
  selector: "app-upload-component",
  templateUrl: "./upload-component.component.html",
  styleUrls: ["./upload-component.component.css"]
})
export class UploadComponentComponent implements OnInit {
  uploadedDatasetId: any;

  public uploader: FileUploader = new FileUploader({
    url: URL,
    itemAlias: "file"
  });

  ngOnInit() {
    this.uploader.onAfterAddingFile = file => {
      file.withCredentials = false;
    };
    this.uploader.onCompleteItem = (
      item: any,
      response: any,
      status: any,
      headers: any
    ) => {
      this.uploadedDatasetId = Array.of(response);

      //this.datasetResults = this.datasetResults;
      console.log("ImageUpload:uploaded:", item, status, response);
      alert("File uploaded successfully");
    };
  }

  refresh(): void {
    window.location.reload();
  }
}
