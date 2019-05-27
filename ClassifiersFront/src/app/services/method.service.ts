import { Injectable } from "@angular/core";
import { Http } from "@angular/http";
import { BehaviorSubject } from "rxjs";
import { map } from "rxjs/operators";

@Injectable({
  providedIn: "root"
})
export class MethodService {
  constructor(private http: Http) {}

  public datasetBS = new BehaviorSubject<string>(null);

  getMethodsForDataset(id) {
    return this.http
      .get(" http://localhost:8080/api/method/methods/" + id)
      .pipe(map(res => res.json()));
  }
}
