import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";
@Injectable({
  providedIn: "root"
})
export class DatasetService {
  constructor(private http: Http) {}

  public datasetBS = new BehaviorSubject<string>(null);

  getAllDatasets() {
    return this.http
      .get(" http://localhost:8080/api/dataset/all")
      .pipe(map(res => res.json()));
  }

  getDataset(id) {
    return this.http
      .get(" http://localhost:8080/api/dataset/" + id)
      .pipe(map(res => res.json()));
  }

  deleteDatasetAndItsResults(id) {
    return this.http
      .delete("http://localhost:8080/api/dataset/" + id)
      .pipe(map(res => res.text()));
  }
}
