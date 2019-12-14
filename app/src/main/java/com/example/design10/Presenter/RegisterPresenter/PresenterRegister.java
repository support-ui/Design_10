package com.example.design10.Presenter.RegisterPresenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.design10.View.InformationView.InformationActivity;
import com.example.design10.View.LoginView.LoginActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.Map;

public class PresenterRegister {

    private Context mContex;
    private FirebaseAuth mAuth;
    private String TAG = "PresenterRegister";
    private DatabaseReference mDatabase;

    public PresenterRegister(Context mContex, FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mContex = mContex;
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    public void signUpUser(final String email, String password, final String nombres,final  String celular){
        final ProgressDialog dialog = new ProgressDialog(mContex);
        dialog.setMessage("Registrando Usuario...");
        dialog.setCancelable(false);
        if((email.isEmpty()||password.isEmpty()||nombres.isEmpty()||celular.isEmpty())){
            AlertDialog("Debe Completar Todos los campos");
        }else{
            dialog.show();
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "createUserWithEmail: Exitoso");
                                Map<String,Object> crearUsuario = new HashMap<>();
                                crearUsuario.put("nombre",nombres);
                                crearUsuario.put("email",email);
                                crearUsuario.put("celular",celular);
                                mDatabase.child("Usuarios").child(task.getResult().getUser().getUid()).updateChildren(crearUsuario);
                                dialog.dismiss();
                                Intent intent = new Intent(mContex, InformationActivity.class);
                                mContex.startActivity(intent);
                                ((Activity) mContex).finish();



                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                dialog.dismiss();
                                AlertDialog("Error al Registrar, Vuelva a intentarlo");
                            }

                            // ...
                        }
                    });

        }



    }

    public void volver(){
        Intent intent = new Intent(mContex, LoginActivity.class);
        mContex.startActivity(intent);
        ((Activity) mContex).finish();
    }

    public void AlertDialog(String mensaje){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
        builder.setTitle("Fitness Diary")
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
