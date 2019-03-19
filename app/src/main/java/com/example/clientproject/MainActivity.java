package com.example.clientproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button signin, btnSignUp;
    private FirebaseAuth auth;
    private DatabaseReference databaseUser;
    String userid, usergraphic;
    EditText name, lastname, phoneno;
    private UserModel userModel;

    // RadioGroup radioGroup;


    String color;
    int clickcount = 0;

    RadioGroup options;
    RadioButton selected;
    private String userId;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );
        auth = FirebaseAuth.getInstance();
        databaseUser = FirebaseDatabase.getInstance().getReference( "UserModel" );

        userModel = new UserModel();

        signin = (Button) findViewById( R.id.forlogin );
        btnSignUp = (Button) findViewById( R.id.registration );
        inputEmail = (EditText) findViewById( R.id.email );
        inputPassword = (EditText) findViewById( R.id.password );
        name = (EditText) findViewById( R.id.name );
        lastname = (EditText) findViewById( R.id.lastname );
        phoneno = (EditText) findViewById( R.id.phoneno );
        options = (RadioGroup) findViewById( R.id.radiogroup );

        options.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                selected = findViewById( checkedId );
                int idx = options.indexOfChild( selected );
                switch (checkedId) {

                    case R.id.Green:
                        RadioButton r = (RadioButton) options.getChildAt( idx );
                        color = r.getText().toString();

                        break;
                    case R.id.Yellow:

                        RadioButton m = (RadioButton) options.getChildAt( idx );
                        color = m.getText().toString();
                        break;
                    default:
                        Toast.makeText( getApplicationContext(), "Choose Color For security", Toast.LENGTH_SHORT ).show();
                        break;
                }

            }
        } );


        signin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( MainActivity.this, Signin.class );
                startActivity( intent );
                finish();
            }
        } );


        btnSignUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (authregistration())
                        {

                        }
                    }

                } );


    }

    @SuppressLint("ResourceType")
    public boolean authregistration() {
        String email = inputEmail.getText().toString().trim();
        String password = inputPassword.getText().toString().trim();


        String dbname = name.getText().toString().trim();
        String dblastname = lastname.getText().toString().trim();
        String dbphone = phoneno.getText().toString().trim();

        if (TextUtils.isEmpty( dbname )) {
            Toast.makeText( getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT ).show();
            return false;
        } else if (TextUtils.isEmpty( dblastname )) {
            Toast.makeText( getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT ).show();
            return false;

        } else if (TextUtils.isEmpty( dbphone )) {
            Toast.makeText( getApplicationContext(), "Enter Cell No", Toast.LENGTH_SHORT ).show();
            return false;
        } else if(options.getCheckedRadioButtonId()<=0){
            Toast.makeText( getApplicationContext(), "Select Color", Toast.LENGTH_SHORT ).show();
            return false;
        }
        else if (TextUtils.isEmpty( email )) {
            Toast.makeText( getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT ).show();
            return false;
        }
        else if (TextUtils.isEmpty( password )) {
            Toast.makeText( getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT ).show();
            return false;
        }
       else  if (password.length() < 6) {
            Toast.makeText( getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT ).show();
            return false;
        }
        else if (!password.matches( "[A-Za-z0-9]*" )) {
            Toast.makeText( getApplicationContext(), "Please Enter Alphanumerci Password", Toast.LENGTH_SHORT ).show();
            return false;
        }

        else {
            auth.createUserWithEmailAndPassword( email, password )
                    .addOnCompleteListener( MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            //progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText( MainActivity.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_SHORT ).show();
                            } else {
                                // userId=FirebaseAuth.getInstance().getCurrentUser().getUid();
                                //   startActivity( new Intent( MainActivity.this, Signin.class ) );

                            }
                        }
                    } );


            userid = databaseUser.push().getKey();
            userModel = new UserModel( dbname, dbphone, dblastname, color);
            databaseUser.child( dbphone ).setValue( userModel );


            startActivity( new Intent( MainActivity.this, Dashboard.class ) );

        }
        return false;
    }


//    @SuppressLint("ResourceType")
//    public boolean relatimedb() {
//
////        String dbname = name.getText().toString().trim();
////        String dblastname = lastname.getText().toString().trim();
////        String dbphone = phoneno.getText().toString().trim();
//
////        if (TextUtils.isEmpty( dbname )) {
////            Toast.makeText( getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT ).show();
////            return false;
////        } else if (TextUtils.isEmpty( dblastname )) {
////            Toast.makeText( getApplicationContext(), "Enter Last Name", Toast.LENGTH_SHORT ).show();
////            return false;
////
////        } else if (TextUtils.isEmpty( dbphone )) {
////            Toast.makeText( getApplicationContext(), "Enter Cell No", Toast.LENGTH_SHORT ).show();
////            return false;
////        } else if(options.getCheckedRadioButtonId()<=0){
////            Toast.makeText( getApplicationContext(), "Select Color", Toast.LENGTH_SHORT ).show();
////            return false;
////        }
//
//
//
//
//       // else {
//
////            userid = databaseUser.push().getKey();
////            userModel = new UserModel( dbname, dbphone, dblastname, color );
////            databaseUser.child( dbphone ).setValue( userModel );
//
//
//
////            startActivity( new Intent( ProfileActivity.this, Dashboard.class ) );
//      //  }
//
//
//        return false;
//    }

}


