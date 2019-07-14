import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class MethodService {
  constructor(private http: Http) {}

  getMethodsForDataset(id) {
    return this.http
      .get("http://localhost:8080/api/method/methods/" + id)
      .pipe(map(res => res.json()));
  }

  getCrossValidationMethodsForDataset(id) {
    return this.http
      .get("http://localhost:8080/api/method/methods/crossValidation/" + id)
      .pipe(map(res => res.json()));
  }

  getBaggingMethodsForDataset(id) {
    return this.http
      .get("http://localhost:8080/api/method/methods/bagging/" + id)
      .pipe(map(res => res.json()));
  }

  getBoostingMethodsForDataset(id) {
    return this.http
      .get("http://localhost:8080/api/method/methods/boosting/" + id)
      .pipe(map(res => res.json()));
  }

  getBestResultForDataset(id) {
    return this.http
      .get("http://localhost:8080/api/method/methods/bestResult/" + id)
      .pipe(map(res => res.json()));
  }
}
