package com.example.kbasa.teaching;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kbasa.teaching.DataTypes.Student;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayDeque;
import java.util.Deque;

public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private Button studentLoginButton;
    private Button teacherLoginButton;
    private boolean studentFlag = false;
    private boolean teacherFlag = false;
    private boolean studentFlag1 = false;
    private boolean teacherFlag1 = false;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();


        //To check if user has logged in
        /*if(auth.getCurrentUser()!=null){
            SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
            boolean userType = sharedPref.getBoolean("IsTeacher",false);
            //Which type of user
            //Teacher
            if(userType==true){
                Intent intent = new Intent(this,TeacherHomeActivity.class);
                startActivity(intent);
                finish();
            }
            //Student
            else{
                Intent intent = new Intent(this, StudentHomeActivity.class);
                startActivity(intent);
                finish();
            }
        }*/

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        studentLoginButton = findViewById(R.id.studentLoginButton);


        /*
        *   Student login
        *   Checks credentials
        *
        *
        *
        *
        */
        studentLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                studentFlag1 = true;
                auth.signInWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            final FirebaseAuth user = FirebaseAuth.getInstance();
                            DatabaseReference db = FirebaseDatabase.getInstance().getReference("Student");
                            db.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        if(dataSnapshot1.getKey().equals(user.getUid())){
                                            studentFlag=true;
                                        }
                                    }

                                    if(studentFlag==true && studentFlag1==true){
                                        Intent intent = new Intent(LoginActivity.this,StudentHomeActivity.class);
                                        startActivity(intent);
                                        studentFlag1=false;
                                        studentFlag=false;
                                        SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPref.edit();
                                        editor.putBoolean("IsStudent", true);
                                        editor.putBoolean("IsTeacher", false);
                                        editor.commit();
                                        finish();
                                    }
                                    else {
                                        studentFlag=false;
                                        studentFlag1=false;
                                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                    }

                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                        }
                    });
            }
        });

        teacherLoginButton = findViewById(R.id.teacherLoginButton);

        /*
        *   Teacher login
        *   Checks credentials
        *
        *
        *
        *
        */
        teacherLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                teacherFlag1 = true;
                auth.signInWithEmailAndPassword(emailEditText.getText().toString(),passwordEditText.getText().toString())
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                final FirebaseAuth user = FirebaseAuth.getInstance();
                                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Teacher");
                                db.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                            if(dataSnapshot1.getKey().equals(user.getUid())){
                                                teacherFlag=true;
                                            }
                                        }
                                        if(teacherFlag==true && teacherFlag1==true){
                                            Intent intent = new Intent(LoginActivity.this,TeacherHomeActivity.class);
                                            startActivity(intent);
                                            teacherFlag1 = false;
                                            teacherFlag=false;
                                            SharedPreferences sharedPref = LoginActivity.this.getPreferences(Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putBoolean("IsTeacher", false);
                                            editor.putBoolean("IsStudent", true);
                                            editor.commit();
                                            finish();
                                        }
                                        else {
                                            teacherFlag1 = false;
                                            teacherFlag=false;
                                            Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Login failed",Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });


    }


    /*
    *   New Registration
    */
    public void newUser(View v){
        Intent intent = new Intent(this,RegistrationActivity.class);
        startActivity(intent);
    }

}
