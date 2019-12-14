package com.example.design10.View.LoginView;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.design10.Presenter.LoginPresenter.PresenterLogin;
import com.example.design10.R;
import com.example.design10.View.RegisterView.RegisterUserActivity;
import com.example.design10.View.InformationView.InformationActivity;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
LinearLayout btnSigInGoogle;
GoogleSignInClient mGoogleSignInClient;
TextView linkregister;
Button btnLogin;
private TextInputLayout txtEmailLogin,txtPassLogin;
private PresenterLogin presenterLogin;
private static final String TAG = "LoginActivity";
int RC_SIGN_IN = 0;
private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        presenterLogin = new PresenterLogin(this,mAuth,mDatabase);

        btnSigInGoogle = findViewById(R.id.btnSigInGoogle);
        btnLogin = findViewById(R.id.btnLogin);
        linkregister = findViewById(R.id.linkregister);
        txtEmailLogin = findViewById(R.id.txtEmailLogin);
        txtPassLogin = findViewById(R.id.txtPassLogin);
        mAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);

        linkregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnSigInGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnSigInGoogle:
                        signIn();
                        break;
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Intent intent = new Intent(LoginActivity.this, InformationActivity.class);
            startActivity(intent);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogin:
                String email = txtEmailLogin.getEditText().getText().toString().trim();
                String pass = txtPassLogin.getEditText().getText().toString().trim();
                presenterLogin.signInUser(email,pass);
                break;
        }
    }
}
