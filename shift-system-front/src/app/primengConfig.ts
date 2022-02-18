import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import {ToolbarModule} from 'primeng/toolbar';
import {TieredMenuModule} from 'primeng/tieredmenu';

import {TableModule} from 'primeng/table';

import {InputTextModule} from 'primeng/inputtext';
import {ButtonModule} from 'primeng/button';
import {TooltipModule} from 'primeng/tooltip';
import {MessagesModule} from 'primeng/messages';
import {MessageModule} from 'primeng/message';
import {ToastModule} from 'primeng/toast';
import {ConfirmDialogModule} from 'primeng/confirmdialog';

import {CardModule} from 'primeng/card';

@NgModule({
    imports: [
        CommonModule,           
        ToolbarModule,
        TieredMenuModule,
        ButtonModule,
        TableModule,
        CardModule,
        InputTextModule,
        MessagesModule,
        MessageModule,
        TooltipModule,
        ToastModule, 
        ConfirmDialogModule
    ],

    exports: [   
        ToolbarModule,
        TieredMenuModule,
        ButtonModule,
        TableModule,
        CardModule,
        InputTextModule,
        MessagesModule,
        MessageModule,
        TooltipModule,
        ToastModule,
        ConfirmDialogModule

    ], entryComponents: [

  ]
})

export class primengConfig {}