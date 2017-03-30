package com.example.joserv_1.loginscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener stateListener;
    private EditText Email_Login,Pass_login;
    private Button button_login;
    private TextView RegisterHere;
    private CheckBox Remember_Me;
    private ProgressDialog mProgress;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgress=new ProgressDialog(this);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth=FirebaseAuth.getInstance();
        //Edit Texts
        Email_Login=(EditText)findViewById(R.id.edtEmailLogin);
        Pass_login=(EditText)findViewById(R.id.edtPassLogin);
        //Buttons
        button_login=(Button)findViewById(R.id.butLogin);
        //TextView
        RegisterHere=(TextView)findViewById(R.id.textRegister);

        //Login button pressed
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckLogin();
            }
        });

    }

    private void CheckLogin() {
        String email_login=Email_Login.getText().toString().trim();
        String pass_login=Pass_login.getText().toString().trim();

        if (!TextUtils.isEmpty(email_login) && !TextUtils.isEmpty(pass_login)){

         mAuth.signInWithEmailAndPassword(email_login,pass_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
             @Override
             public void onComplete(@NonNull Task<AuthResult> task) {

                 if (task.isSuccessful()){

                     String user_id=mAuth.getCurrentUser().getUid();
                     final DatabaseReference Current_User_db= mDatabase.child(user_id);Current_User_db.addListenerForSingleValueEvent(new ValueEventListener() {
                         @Override
                         public void onDataChange(DataSnapshot dataSnapshot) {
                             String type1=dataSnapshot.child("Type").getValue().toString();
                             if (type1.equals("2")){
                                 Intent mainInt=new Intent(LoginActivity.this,RequestActivity.class);
                                 mainInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                 startActivity(mainInt);

                             }else
                                 if (type1.equals("1")){
                                     Intent mainInt=new Intent(LoginActivity.this,PoolFragment.class);
                                     mainInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     startActivity(mainInt);

                             }else
                                 if (type1.equals("0")){
                                     Intent mainInt=new Intent(LoginActivity.this,RequestActivity.class);
                                     mainInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                     startActivity(mainInt);

                                 }
                         }

                         @Override
                         public void onCancelled(DatabaseError databaseError) {

                         }
                     });



                 }else {

                     Toast.makeText(LoginActivity.this," Error Login",Toast.LENGTH_LONG).show();
                 }
             }
         });
        }
    }



    public void RegisterHere(View view) {
        Intent registerIntent= new Intent(LoginActivity.this,Register.class);
        registerIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(registerIntent);

    }
}
