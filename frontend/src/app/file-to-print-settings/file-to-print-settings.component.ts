import { ErrorDialogComponent } from './../error-dialog/error-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { AlertDialogComponent } from './../alert-dialog/alert-dialog.component';
import { HomeService } from './../Services/home.service';
import { ParametreService } from './../Services/parametre.service';
import { element } from 'protractor';
import { map } from 'rxjs/operators';
import { ShActivity } from './../Models/ShActivity';
import { FileToPrint } from './../Models/FileToPrint';
import { Component, OnInit, AfterViewInit, AfterContentChecked, AfterViewChecked } from '@angular/core';
import { Structure } from '../Models/Structure';
import { clotureFiles } from '../Models/cloturesFiles';
import { MatCheckbox } from '@angular/material/checkbox';

import { ViewChildren, QueryList } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { mobiscroll, MbscFormOptions } from '@mobiscroll/angular-lite';
import { coerceStringArray } from '@angular/cdk/coercion';

export interface Element {
  "idStructure": number,
  "idEtat": [number]
}

@Component({
  selector: 'app-file-to-print-settings',
  templateUrl: './file-to-print-settings.component.html',
  styleUrls: ['./file-to-print-settings.component.css']
})
export class FileToPrintSettingsComponent implements OnInit{
  payrollstructures: Structure[] = [];
  tousLesEtats: clotureFiles[] = [];  
  allShActivities: ShActivity[] = [];
  allFileToPrint: FileToPrint[] = [];
  tempFileToPrint: FileToPrint[] = [];
  messageAdded:Boolean=false;
  messageDeleted:Boolean=false;
  selectFiles:Boolean=false;
 
  formSettings: MbscFormOptions = {
    theme: 'mobiscroll',
    themeVariant: 'light'
  };
  fileToPrintFormGroup: FormGroup = new FormGroup({});
  constructor(private _formBuilder: FormBuilder, private paramService:ParametreService, private homeService:HomeService,public dialog: MatDialog) {


  }

  ngOnInit(): void {

    this.homeService.getAllStructures().subscribe(
      (data) => {
        if(data!=null){
          this.payrollstructures = data;
          // //console.log("allstructures");
         //  //console.log(data);
          //this.getAllSelectedEtatWithStructure();
        }else{
          this.openDialog();
        }
    
      },
      error => {
        // //console.log(error);
         this.openDialogError(error);;
        throw error;

      }
    );

    //get all etats to select file to print

    this.paramService.getEtats().subscribe(
      (data) => {
        if(data!=null){
          this.tousLesEtats = data;
          //this.addToggles();
          //this.getAllSelectedEtatWithStructure();
         //  //console.log("touslesetats");
         //  //console.log(data);
        }else{
          this.openDialog();
        }
       
      },
      error => {
        // //console.log(error);
         this.openDialogError(error);;
        throw error;
      }
    );

    //get all files to print
    this.paramService.getAllFileToPrint().subscribe(
      (data) => {
        if(data!=null){
          this.tempFileToPrint = data;
        
        
             
          // //console.log("allfiletoprint");
         //  //console.log(data);
        }else{
          this.openDialog();
        }
        
      },
      error => {
       //  //console.log(error);
         this.openDialogError(error);;
        throw error;
      }
    );

    //get all files to print
    this.paramService.getAllFileToPrint().subscribe(
      (data) => {
        
        if(data!=null){
          this.allFileToPrint = data;
        
             
          //  //console.log("allfiletoprint");
           // //console.log(data);
        }else{
          this.openDialog();
        }
        
      },
      error => {
       //  //console.log(error);
         this.openDialogError(error);;
        throw error;
      }
    );

    //get all ShActivities
    this.paramService.getAllShActivities().subscribe(
      (data) => {
        if(data!=null){
          this.allShActivities = data;
             
          // //console.log("allshavtivities");
         //  //console.log(data);
        }else{
          this.openDialog();
        } 
      },
      error => {
        // //console.log(error);
         this.openDialogError(error);;
        throw error;
      }
    );
    
  }
  

  ischeked(idetat:number,idpayrollstructure:number){
    for (let i = 0; i < this.tempFileToPrint.length; i++) {
      if (this.tempFileToPrint[i].idStructure == idpayrollstructure) {
        if (this.tempFileToPrint[i].idFileType==idetat) {
          return true;
        }
      }
    }
    return false;
  }
  
 
  ischekedindex(idetat:number,idpayrollstructure:number){
    for (let i = 0; i < this.tempFileToPrint.length; i++) {
      if (this.tempFileToPrint[i].idStructure == idpayrollstructure) {
        if (this.tempFileToPrint[i].idFileType==idetat) {
          return i;
        }
      }
    }
    return -1;
  }


  updateSelectedEtatForStructure(idetat:number,idpayrollstructure:number){
    let file: FileToPrint = {
      "idFileType": idetat,
      "idStructure": idpayrollstructure,
      "addedDate": new Date()
    }
   //  //console.log("before");
    // //console.log(this.tempFileToPrint);
    let index=this.ischekedindex(idetat,idpayrollstructure)
    if(index!=-1){
      this.tempFileToPrint.splice(index,1);
    }else{
      this.tempFileToPrint.push(file);
    }
   //  //console.log("the file");
   //  //console.log(file);
    // //console.log("after");
    // //console.log(this.tempFileToPrint);
    }

 
  //alert pour le FileToPrint selection
  showAlert() {
    if(this.messageAdded &&this.messageDeleted){
      mobiscroll.alert({
        title: 'Fichier ?? imprimer',
        message: "Vos modification ont bien ??t?? enregistr??es"
        , callback: function () {
          window.location.reload();
        }
      });
    }
   
  }


  SelectFileToPrint(){
    // //console.log("temp");
    // //console.log(this.tempFileToPrint);
   // //console.log("all");
    // //console.log(this.allFileToPrint);
    
    let localtempFileToPrint:FileToPrint[]=this.tempFileToPrint;
    let localallFileToPrint:FileToPrint[]=this.allFileToPrint;

 
    // to drop 
    const drop = this.allFileToPrint.filter(function(o1){
      return !localtempFileToPrint.some(function(o2){    //  for diffrent we use NOT (!) befor obj2 here
        return o1.idFileType == o2.idFileType && o1.idStructure == o2.idStructure;          // id is unnique both array object
      });
    });

   //  //console.log("to delete"); 
    // //console.log(drop);

    // Need differnt obj 
    const add = this.tempFileToPrint.filter(function(o1){
      return !localallFileToPrint.some(function(o2){    //  for diffrent we use NOT (!) befor obj2 here
        return o1.idFileType == o2.idFileType && o1.idStructure == o2.idStructure;          // id is unnique both array object
      });
    });
    // //console.log("to add"); 
   //  //console.log(add);
    // //console.log("all and temp"); 
    // //console.log(this.allFileToPrint); 
    // //console.log(this.tempFileToPrint); 
    this.SaveChangesInDB(add,drop);
  }


  //sauvgarder les file to print
  SaveChangesInDB(fileToAdd:FileToPrint[],fileToDelete:FileToPrint[]) {

    this.paramService.saveFileToPrint(fileToAdd).subscribe(
      data => {
       //  //console.log(data);
        if (data === null) {
          this.openDialog();
        } else {
          this.messageAdded=true;
         this.showAlert();
          fileToAdd.slice(1, fileToAdd.length);
          
        }

      },
      error => {
       //  //console.log(error);
         this.openDialogError(error);;
        throw error;
      });



    this.paramService.deleteFileToPrint(fileToDelete).subscribe(
      data => {
        // //console.log(data);
        if (data === null) {
          this.openDialog();
        } else {
          this.messageDeleted=true;
          this.showAlert();
          fileToDelete.splice(1, fileToDelete.length);
         
        }

      },
      error => {
        // //console.log(error);
         this.openDialogError(error);;
        throw error;
      });

     
    

  
  }

  openDialog() {
    const dialogRef = this.dialog.open(AlertDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  openDialogError(error:String): void {
    const dialogRef = this.dialog.open(ErrorDialogComponent, {
      width: '650px',
      data: {message: error}
    });

    dialogRef.afterClosed().subscribe(result => {
      window.location.reload();
    });
  }

  
}
