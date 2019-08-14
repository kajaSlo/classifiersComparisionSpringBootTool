import { Component, OnInit } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import {
  FileUploader,
  FileSelectDirective
} from "ng2-file-upload/ng2-file-upload";

const uploadUrl = "http://localhost:8080/api/dataset/upload";
@Component({
  selector: "app-upload-component",
  templateUrl: "./upload-component.component.html",
  styleUrls: ["./upload-component.component.css"]
})
export class UploadComponentComponent implements OnInit {
  public uploader: FileUploader = new FileUploader({
    url: uploadUrl,
    itemAlias: "file"
  });

  ngOnInit() {
    this.uploader.onAfterAddingFile = file => {
      file.withCredentials = false;
    };
  }

  refresh(): void {
    window.location.reload();
  }
}
