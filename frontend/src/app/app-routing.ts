import { ErrorComponent } from './error/error.component';
import { UpdateStructureComponent } from './update-structure/update-structure.component';
import { HomeComponent } from './home/home.component';

import { DeleteStructureComponent } from './delete-structure/delete-structure.component';
import { AddStructureComponent } from './add-structure/add-structure.component';
import {Routes, RouterModule}from '@angular/router';
import { FilesGeneratorComponent } from './files-generator/files-generator.component';

const APP_ROUTING : Routes =[
    {path:'home', component:HomeComponent },
    {path :'FilesGenerator',component:FilesGeneratorComponent},
    {path :'addStructure',component:AddStructureComponent},
    {path :"structures", component :DeleteStructureComponent},
    {path :"", component :HomeComponent},
    {path :"updateStructure/:id", component :UpdateStructureComponent},
    {path :"**", component: ErrorComponent}
    
]

export const ROUTING =RouterModule.forRoot(APP_ROUTING);