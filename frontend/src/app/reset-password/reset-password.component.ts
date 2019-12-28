import { Component, OnInit } from '@angular/core';
import { Password } from '../model/password';
import { FormGroup, FormBuilder } from '@angular/forms';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ResetPasswordService } from '../service/reset-password.service';
import { ConfirmPasswordValidator } from '../validation/confirm-password.validator';
import { ActivatedRoute, Router } from '@angular/router';


@Component({
  selector: 'app-reset-password',
  templateUrl: './reset-password.component.html',
  styleUrls: ['./reset-password.component.scss']
})
export class ResetPasswordComponent implements OnInit {

  resetPasswordForm: FormGroup;
  password: Password;
  message: string;
  token: string;
  isExpired: boolean;

  constructor(private resetPasswordService: ResetPasswordService,
    private fb: FormBuilder, private snackBar: MatSnackBar, private router: Router,
    private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.resetPasswordForm = this.fb.group({
      password: [''],
      confirmPassword: ['']
    }, { validator: ConfirmPasswordValidator.matchPassword });
    this.checkIsExpired();
  }

  checkIsExpired() {
    this.activatedRoute.queryParams.subscribe(params => {
      this.token = params.token;
    });
    this.resetPasswordService.checkIsExpired(this.token).subscribe(response => {
      this.isExpired = false;
    }, err => {
      this.isExpired = true;
      this.message = err.error;
    });
  }

  resetPassword(): void {
    this.password = new Password(this.token, this.resetPasswordForm.get('password').value);
    this.resetPasswordService.resetPassword(this.password).subscribe(response => {
      this.router.navigate(['login']).then((navigated: boolean) => {
        if (navigated) {
          this.openSnackBar('Your password was successfully reseted! Try to log in using your new credentials');
        }
      });
    }, err => {
      this.message = err.error;
      this.openSnackBar(this.message);
    });
  }

  openSnackBar(message: string) {
    this.snackBar.open(message, 'Close', {
      duration: 4000,
    });
  }
}
