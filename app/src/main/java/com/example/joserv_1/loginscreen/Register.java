package com.example.joserv_1.loginscreen;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Register extends AppCompatActivity {
    private EditText edtFname, edtLname, edtEmail, edtPass, edtRetypePass, edtTeam_Viewer_Id,edtPhone;
    private Button butSign_Up;
    private FirebaseAuth  mAuth;
    private ProgressDialog progress;
    private DatabaseReference mDatabase1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Edit Texts
        edtFname=(EditText)findViewById(R.id.editNameFirst);
        edtLname=(EditText)findViewById(R.id.editNameLast);
        edtEmail=(EditText)findViewById(R.id.editEmail);
        edtPass=(EditText)findViewById(R.id.edtPass);
        edtRetypePass=(EditText)findViewById(R.id.editRetypePass);
        edtTeam_Viewer_Id =(EditText)findViewById(R.id.editTeamViewer);
        edtPhone =(EditText)findViewById(R.id.editPhone);

        //database
        mAuth=FirebaseAuth.getInstance();
        progress=new ProgressDialog(this);
        progress.setMessage("Signing Up.....");

        //Buttons
        butSign_Up=(Button)findViewById(R.id.butSignUp);

        //sign up button pressed
        butSign_Up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegister();
            }
        });

    }

    private void startRegister() {
        final String Fname=edtFname.getText().toString().trim();
        final String Lname=edtLname.getText().toString().trim();
        final String Email=edtEmail.getText().toString().trim();
         String PassWord=edtPass.getText().toString().trim();
       String RetypePass=edtRetypePass.getText().toString().trim();
        final String Team_Viewer_Id=edtTeam_Viewer_Id.getText().toString().trim();
        final String Phone=edtPhone.getText().toString().trim();

        if (!TextUtils.isEmpty(Fname) && !TextUtils.isEmpty(Lname) && !TextUtils.isEmpty(Email) && !TextUtils.isEmpty(PassWord) &&
                !TextUtils.isEmpty(RetypePass) && !TextUtils.isEmpty(Team_Viewer_Id) && !TextUtils.isEmpty(Phone) ){

            if (edtPass.getText().toString().equals(edtRetypePass.getText().toString())==false) {
                Toast.makeText(Register.this, "passwords do not match", Toast.LENGTH_LONG).show();

            }
            else if(PassWord.length()<5){
                Toast.makeText(Register.this, "the password must be 6 letter or more number and char", Toast.LENGTH_LONG).show();
                return;
            }
            else {

                mAuth=FirebaseAuth.getInstance();
            mDatabase1= FirebaseDatabase.getInstance().getReference().child("Users");
            progress.show();

            mAuth.createUserWithEmailAndPassword(Email,PassWord).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        Toast.makeText(Register.this,"error Registering please try again later",Toast.LENGTH_LONG).show();
                        progress.dismiss();
                    }else {
                        Toast.makeText(Register.this, "Registering completed successfully", Toast.LENGTH_LONG).show();
                        String user_id=mAuth.getCurrentUser().getUid();
                       final DatabaseReference Current_User_db= mDatabase1.child(user_id);

                        Current_User_db.child("First_Name").setValue(Fname);
                        Current_User_db.child("Last_Name").setValue(Lname);
                        Current_User_db.child("Eamil").setValue(Email);
                        Current_User_db.child("Team_Viewer_Id").setValue(Team_Viewer_Id);
                        Current_User_db.child("phone").setValue(Phone);
                        Current_User_db.child("Type").setValue("2");

                        //Users Type
                        Current_User_db.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String type1=dataSnapshot.child("Type").getValue().toString();
                                if (type1.equals("2")){
                                    Intent mainInt=new Intent(Register.this,RequestActivity.class);
                                    mainInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(mainInt);

                                }else
                                    if (type1.equals("1")){
                                        Intent mainInt=new Intent(Register.this,PoolFragment.class);
                                        mainInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(mainInt);
                                    }else
                                    if (type1.equals("0")){
                                        Intent mainInt=new Intent(Register.this,RequestActivity.class);
                                        mainInt.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(mainInt);
                                    }
                                }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });

                    }

                }
            });
                progress.dismiss();
                finish();
        }
    }
        else {
            Toast.makeText(Register.this, "one of the fields is empty ", Toast.LENGTH_LONG).show();
        }
}}
