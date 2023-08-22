import {Component, ViewChild} from '@angular/core';
import {ClientService} from "./client.service";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import { PoNotificationService } from '@po-ui/ng-components';

@Component({
    selector: 'client-root',
    templateUrl: './client.component.html',
    styleUrls: ['./client.component.css']
})
export class ClientComponent {
    @ViewChild('modal', { static: false }) modal: any;
    @ViewChild('modalTelefone', { static: false }) modalTelefone: any;
    errorMessages: string[] = [];
    formGroup: any
    clients: any[] = [];
    telefoneMask: string = '(99) 9 9999-9999';

    showModal: boolean = false;

    selectedClient: any = {};

    saving: boolean = false; // Adiciona essa variável
    constructor(private clientService: ClientService, private fb: FormBuilder, private poNotification: PoNotificationService) {
        this.createReactiveForm();
        this.carregarDados();
    }


    createReactiveForm() {
        this.formGroup = this.fb.group({
            name: ['', Validators.compose([Validators.required, Validators.minLength(11), Validators.maxLength(50)])],
            address: [''],
            neighborhood: ['',],
            phones: this.fb.array([this.createPhoneFormControl('')])
        });
    }

    carregarDados() {

        this.clientService.listarDados().subscribe(
            data => {
                this.clients = data;
            },
            error => {
                console.error('Erro ao carregar dados:', error);
            }
        );
    }

    showClientPhonesModal(client: any) {
        // this.showModal = true;
        this.selectedClient = client;
        this.modalTelefone.open();
        // console.log(this.showModal)
    }

    createPhoneFormControl(phone: string): FormGroup {
        return this.fb.group({
            phone: [phone],
        });
    }
    adicionarTelefone() {
        const phonesFormArray = this.formGroup.get('phones') as FormArray;
        phonesFormArray.push(this.createPhoneFormControl(''));

    }

    removeTelefone(index: number) {
        const phonesFormArray = this.formGroup.get('phones') as FormArray;
        phonesFormArray.removeAt(index);
    }

    salvarFormulario() {

        this.saving = true;
        this.errorMessages = [];

        this.clientService.createClient(this.formGroup.value).subscribe(
            data => {
                this.clients.push(data);
                this.saving = false;
                this.formGroup.reset();
                this.modal.close();
                this.poNotification.success('Cliente criado com sucesso!');
            },
            error => {
                this.saving = false;
                if (error.error instanceof Array) {
                    for (const errorItem of error.error) {
                        this.poNotification.error(errorItem.errorMessage);
                    }
                }

                this.poNotification.error("Erro ao criar um cliente: ");

            }
        );
    }
}
