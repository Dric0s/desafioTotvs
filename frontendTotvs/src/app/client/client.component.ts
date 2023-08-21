import { Component } from '@angular/core';
import {ClientService} from "./client.service";

@Component({
    selector: 'client-root',
    templateUrl: './client.component.html',
    styleUrls: ['./client.component.css']
})
export class ClientComponent {

    itens: any[] | undefined;

    constructor(private dataService: ClientService) { }

    ngOnInit(): void {
        this.dataService.listarDados().subscribe(
            data => {
                this.itens = data;
            },
            error => {
                console.error('Erro ao buscar os dados:', error);
            }
        );
    }}
