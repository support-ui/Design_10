package com.example.design10.View.RegisterView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.design10.Presenter.RegisterPresenter.PresenterRegister;
import com.example.design10.R;
import com.example.design10.View.LoginView.LoginActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUserActivity extends AppCompatActivity implements View.OnClickListener {
    private TextInputLayout txtUserName,txtUserPhone,txtUserEmail,txtUserPassword;
    private PresenterRegister presenterRegister;
    Button btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        presenterRegister = new PresenterRegister(this,mAuth,mDatabase);


        setContentView(R.layout.activity_register_user);
        btnRegister = findViewById(R.id.btnRegister);
        txtUserName = findViewById(R.id.txtUserName);
        txtUserPhone = findViewById(R.id.txtUserPhone);
        txtUserEmail = findViewById(R.id.txtUserEmail);
        txtUserPassword = findViewById(R.id.txtUserPassword);

        btnRegister.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        presenterRegister.volver();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnRegister:
                String email = txtUserEmail.getEditText().getText().toString().trim();
                String pass = txtUserPassword.getEditText().getText().toString().trim();
                String nombres = txtUserName.getEditText().getText().toString().trim();
                String celular =txtUserPhone.getEditText().getText().toString().trim();
                presenterRegister.signUpUser(email,pass,nombres,celular);
                break;
        }
    }
}
