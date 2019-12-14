package com.example.design10.Presenter.InformationPresenter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.design10.Model.UserModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class PresenterInformation {

    private Context mContex;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    public PresenterInformation(Context mContex, FirebaseAuth mAuth, DatabaseReference mDatabase) {
        this.mContex = mContex;
        this.mAuth = mAuth;
        this.mDatabase = mDatabase;
    }

    public void ChangeText(final TextView txtNombre, final TextView txtEmail){
        mDatabase.child("Usuarios").child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserModel userModel = dataSnapshot.getValue(UserModel.class);
                /*String nombres = userModel.getNombre();
                StringTokenizer st = new StringTokenizer(nombres, " ");
                String primerNombre = st.nextToken();*/
                txtNombre.setText(userModel.getNombre());
                txtEmail.setText(userModel.getEmail());
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void ExitApp(){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContex);
        builder.setTitle("Avigo")
                .setMessage("Confirmar para salir")
                .setCancelable(false)
                .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((Activity)mContex).finish();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        //Creating dialog box
        AlertDialog dialog  = builder.create();
        dialog.show();
    }
}
