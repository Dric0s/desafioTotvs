import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root' // Isso garante que o serviço seja injetado globalmente
})
export class ClientService {
    private apiUrl = 'http://localhost:3000/client'; // Altere para a URL da sua API

    constructor(private http: HttpClient) { }

    listarDados(): Observable<any[]> {
        return this.http.get<any[]>(this.apiUrl);
    }

    createClient(formData:Object): Observable<Object>{
        return this.http.post(this.apiUrl, formData);
    }
}
