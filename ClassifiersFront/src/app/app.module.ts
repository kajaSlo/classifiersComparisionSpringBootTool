import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { FileSelectDirective } from "ng2-file-upload";
import { AppComponent } from "./app.component";
import { MethodService } from "./services/method.service";
import { HttpModule } from "@angular/http";
import { RouterModule, Routes } from "@angular/router";
import { UploadComponentComponent } from "./components/upload-component/upload-component.component";
import { CrossValidationResultsForDatasetComponent } from "./components/cross-validation-results-for-dataset/cross-validation-results-for-dataset.component";
import { MainPageComponent } from "./components/main-page/main-page.component";
import { BaggingResultsForDatasetComponent } from "./components/bagging-results-for-dataset/bagging-results-for-dataset.component";
import { BoostingResultsForDatasetComponent } from "./components/boosting-results-for-dataset/boosting-results-for-dataset.component";
import { BestResultComponent } from "./components/best-result/best-result.component";
import { DatasetService } from "./services/dataset.service";

const appRoutes: Routes = [
  { path: "", component: MainPageComponent },
  {
    path: "crossValidationResuls/:id",
    component: CrossValidationResultsForDatasetComponent
  },
  {
    path: "baggingResults/:id",
    component: BaggingResultsForDatasetComponent
  },

  {
    path: "boostingResults/:id",
    component: BoostingResultsForDatasetComponent
  },
  {
    path: "bestResult/:id",
    component: BestResultComponent
  }
];

@NgModule({
  declarations: [
    AppComponent,
    FileSelectDirective,
    UploadComponentComponent,
    CrossValidationResultsForDatasetComponent,
    MainPageComponent,
    BaggingResultsForDatasetComponent,
    BoostingResultsForDatasetComponent,
    BestResultComponent
  ],
  imports: [BrowserModule, HttpModule, RouterModule.forRoot(appRoutes)],
  providers: [MethodService, DatasetService],
  bootstrap: [AppComponent]
})
export class AppModule {}
