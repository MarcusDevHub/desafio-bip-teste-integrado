import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Beneficio } from '../models/beneficio';
import { TransferRequest } from '../models/transfer-request';

@Injectable({
    providedIn: 'root'
})
export class BeneficioService {
    private apiUrl = 'http://localhost:8080/api/v1/beneficios';

    constructor(private http: HttpClient) { }

    listar(): Observable<Beneficio[]> {
        return this.http.get<Beneficio[]>(this.apiUrl);
    }
    criar(beneficio: Partial<Beneficio>): Observable<Beneficio> {
        return this.http.post<Beneficio>(this.apiUrl, beneficio);
    }
    transferir(request: TransferRequest) {
        return this.http.post<void>(`${this.apiUrl}/transfer`, request);
    }
    deletar(id: number) {
        return this.http.delete<void>(`${this.apiUrl}/${id}`);
    }


}
