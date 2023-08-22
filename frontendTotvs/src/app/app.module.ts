import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { PoModule } from '@po-ui/ng-components';
import { HttpClientModule } from '@angular/common/http';
import {ClientComponent} from "./client/client.component";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import { PoFieldModule } from '@po-ui/ng-components';


@NgModule({
  declarations: [
    AppComponent, ClientComponent
  ],
    imports: [
        BrowserModule,
        PoModule,
        HttpClientModule,
        FormsModule,
        ReactiveFormsModule, PoFieldModule

    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
