import { Component, OnInit } from "@angular/core";
import {
  FileUploader,
  FileSelectDirective
} from "ng2-file-upload/ng2-file-upload";

const URL = "http://localhost:8080/api/dataset/upload";

@Component({
  selector: "app-root",
  templateUrl: "./app.component.html",
  styleUrls: ["./app.component.css"]
})
export class AppComponent implements OnInit {
  title = "app";

  stringFromBackEnd: any;

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
      this.stringFromBackEnd = response;
      console.log("ImageUpload:uploaded:", item, status, response);
      alert("File uploaded successfully");
    };
  }
}
