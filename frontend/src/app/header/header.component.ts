import { AlertDialogComponent } from './../alert-dialog/alert-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { ErrorDialogComponent } from './../error-dialog/error-dialog.component';
import { UserService } from './../Services/user.service';
import { Router } from '@angular/router';
import { TokenStorageService } from './../auth/token-storage.service';
import { Component, OnInit } from '@angular/core';
import { mobiscroll, MbscFormOptions } from '@mobiscroll/angular-lite';
import { CONTEXT_NAME } from '@angular/compiler/src/render3/view/util';


@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  public connection:Boolean=false;
  
  formSettings: MbscFormOptions = {
    theme: 'mobiscroll',
    themeVariant: 'light'
  };
  currentMonth:String="dd";
  constructor(private tokenStorage: TokenStorageService,private router: Router,private userService:UserService,public dialog: MatDialog) { }


  ngOnInit(): void {
    if (this.tokenStorage.getToken()) {
      this.connection=true;
   
    }

    this.userService.getCurrentMonth().subscribe(
      (data) => {
        if(data!=null){
        //console.log(data);
        let currentYear = data.paymonth.substring(0, 4);
			let currentMonth = data.paymonth.substring(4, 6);
			let dateFormat =currentMonth+"/"+ currentYear  ;
          this.currentMonth=dateFormat;
         
        }else{
          this.openDialog();
        }
        
        
      },
      error => {
        // //console.log(error);
        this.openDialogError(error);
        throw error;

      }
    )
  }
  logout() {
    // //console.log("deconnexion")
    this.connection=false;
    this.tokenStorage.signOut();
  
    this.router.navigateByUrl("auth/login");
    window.location.reload();
  }
  showConfirm() {
 
  mobiscroll.confirm({
    title: 'Déconnexion',
    message: 'Voulez vous vraiment vous déconnecter?',
    okText: 'Oui',
    cancelText: 'Non'
  
}).then( (result) => {
  // //console.log(result ? 'Agreed.' : 'Disagreed.');
  if(result){
   this.logout();
  }
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
