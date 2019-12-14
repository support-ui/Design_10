package com.example.design10.Presenter.LoginPresenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.design10.View.InformationView.InformationActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class PresenterLogin {

    private Context mContex;
    private String TAG = "PresenterLogin";
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public PresenterLogin(Context mContex, FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mContex = mContex;
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    public void signInUser(String email,String password){

        final ProgressDialog dialog = new ProgressDialog(mContex);
        dialog.setMessage("Autenticando...");
        dialog.setCancelable(false);

        if(email.isEmpty()||password.isEmpty()){
            dialog.dismiss();
            AlertDialog("Debe Completar los campos");
        }else{
            dialog.show();
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail: Exitoso");
                        dialog.dismiss();
                        mDatabase.child("Usuarios").child(task.getResult().getUser().getUid());
                        Intent intent = new Intent(mContex, InformationActivity.class);
                        mContex.startActivity(intent);
                        ((Activity) mContex).finish();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        dialog.dismiss();
                        AlertDialog("Error en Login, Vuelva a intentarlo.");
                    }
                }
            });
        }
    }

    public void AlertDialog(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
        builder.setTitle("Avigo")
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();
    }
}
