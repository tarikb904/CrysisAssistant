package com.xinus.generic.crysisassistant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CreateNewAccount extends AppCompatActivity {


    EditText editTextName,editTextPassword,editTextEmail,editTextPhoneNumber;
    TextView textViewLogIn;
    Button buttonRegister;
    ProgressDialog progressDialog;

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_account);

        firebaseAuth=FirebaseAuth.getInstance();

        editTextName = (EditText)findViewById(R.id.userragistrationName);
        editTextPassword = (EditText)findViewById(R.id.userragistrationPassword);
        editTextEmail =(EditText)findViewById(R.id.userragistrationEmail);
        editTextPhoneNumber =(EditText)findViewById(R.id.userragistrationPhoneNumber);

        progressDialog = new ProgressDialog(this);

        buttonRegister = (Button)findViewById(R.id.buttonToRegister);
        textViewLogIn =(TextView) findViewById(R.id.textviewtologin) ;




        Typeface typeface = Typeface.createFromAsset(getAssets() , "impact.ttf");
        editTextName.setTypeface(typeface);
        editTextPassword.setTypeface(typeface);
        editTextEmail.setTypeface(typeface);
        editTextPhoneNumber.setTypeface(typeface);
        buttonRegister.setTypeface(typeface);
        textViewLogIn.setTypeface(typeface);
    }

    public void LoginAgain(View view) {
        startActivity(new Intent(this , MainActivity.class));
    }

    public void RegisterUser(View view) {


        editTextName = (EditText)findViewById(R.id.userragistrationName);
        editTextPassword = (EditText)findViewById(R.id.userragistrationPassword);
        editTextEmail =(EditText)findViewById(R.id.userragistrationEmail);
        editTextPhoneNumber =(EditText)findViewById(R.id.userragistrationPhoneNumber);

        final String name = editTextName.getText().toString().trim();
        final String phonenumber = editTextPhoneNumber.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();


        if(email.isEmpty()||password.isEmpty()||name.isEmpty()||phonenumber.isEmpty())
        {
            Snackbar.make(view, "Please Fill Up All The nformation. :)", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();
            progressDialog.cancel();
        }
        else {

            progressDialog.setMessage("Please wait for sometimes...");
            progressDialog.show();


            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                updateUserdata(name ,email, phonenumber);
                                startActivity(new Intent(CreateNewAccount.this , HomeLayout.class));
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext() , "Please Try Again. :)"+task.getException().toString() , Toast.LENGTH_SHORT).show();
                                progressDialog.cancel();
                            }
                        }
                    });

        }

    }

    private void updateUserdata(String name,String email,String phonenumber) {
        progressDialog.cancel();
        String user_id = firebaseAuth.getCurrentUser().getUid();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference current_user_db = firebaseDatabase.getReference().child("Users").child(user_id);

        Users user = new Users(user_id , name , email , phonenumber );

        current_user_db.setValue(user);
        Toast.makeText(getApplicationContext() , "Registration Successful :)" , Toast.LENGTH_SHORT).show();
    }
}
