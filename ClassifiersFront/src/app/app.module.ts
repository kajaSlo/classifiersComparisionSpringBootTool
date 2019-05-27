import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FileSelectDirective } from "ng2-file-upload";
import { FormsModule } from "@angular/forms";
import { AppComponent } from "./app.component";
import { MethodService } from "./services/method.service";
import { HttpModule } from "@angular/http";
import { RouterModule, Routes } from "@angular/router";
import { UploadComponentComponent } from "./components/upload-component/upload-component.component";
import { ResultsForUploadedFileComponent } from "./components/results-for-uploaded-file/results-for-uploaded-file.component";

const appRoutes: Routes = [
  { path: "", component: UploadComponentComponent },
  {
    path: "uploadedFileResults/:id",
    component: ResultsForUploadedFileComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    FileSelectDirective,
    UploadComponentComponent,
    ResultsForUploadedFileComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpModule,
    RouterModule.forRoot(appRoutes)
  ],
  providers: [MethodService],
  bootstrap: [AppComponent]
})
export class AppModule {}
