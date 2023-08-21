import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root' // Isso garante que o serviço seja injetado globalmente
})
export class ClientService {
    private apiUrl = 'http://localhost:8080/client'; // Altere para a URL da sua API

    constructor(private http: HttpClient) { }

    listarDados(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }
}
