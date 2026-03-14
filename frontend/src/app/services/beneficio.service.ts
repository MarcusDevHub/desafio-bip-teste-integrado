import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio } from '../models/beneficio';

@Injectable({
    providedIn: 'root'
})
export class BeneficioService {
    private apiUrl = 'http://localhost:8080/api/v1/beneficios';

    constructor(private http: HttpClient) { }

    listar(): Observable<Beneficio[]> {
        return this.http.get<Beneficio[]>(this.apiUrl);
    }
}
