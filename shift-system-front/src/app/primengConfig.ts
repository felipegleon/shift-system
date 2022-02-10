import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';


import {ToolbarModule} from 'primeng/toolbar';
import {TieredMenuModule} from 'primeng/tieredmenu';

import {TableModule} from 'primeng/table';

import {ButtonModule} from 'primeng/button';


@NgModule({
    imports: [
        CommonModule,           
        ToolbarModule,
        TieredMenuModule,
        ButtonModule,
        TableModule       

    ],

    exports: [   
        ToolbarModule,
        TieredMenuModule,
        ButtonModule,
        TableModule

    ], entryComponents: [

  ]
})

export class primengConfig {}