package com.example.clientproject;

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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Signin extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private Button btnSignup, btnLogin;
    RadioGroup radioGroup;
    UserModel userModel;

    String chosecolor;
    RadioButton radioButton;
    DatabaseReference mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signin );

        auth = FirebaseAuth.getInstance();
        mDb= FirebaseDatabase.getInstance().getReference();
        userModel = new UserModel();
        inputEmail = (EditText) findViewById( R.id.loginemail );
        inputPassword = (EditText) findViewById( R.id.loginpassword );
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById( R.id.registrationbtn );
        btnLogin = (Button) findViewById( R.id.signinbtn );
//        btnReset = (Button) findViewById(R.id.btn_reset_password);
        auth = FirebaseAuth.getInstance();
        radioGroup = (RadioGroup) findViewById( R.id.selectradiogroup );
        radioGroup.setOnCheckedChangeListener( new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                radioButton = (RadioButton) findViewById( checkedId );
                int selectedId = radioGroup.indexOfChild( radioButton );
                switch (checkedId) {

                    case R.id.selectGreen:
                        RadioButton r = (RadioButton) radioGroup.getChildAt( selectedId );
                        chosecolor = r.getText().toString();
                        break;


                    case R.id.selectYellow:
                        RadioButton m = (RadioButton) radioGroup.getChildAt( selectedId );
                        chosecolor = m.getText().toString();
                        break;


                    default:
                        Toast.makeText( getApplicationContext(), "Choose Color For security", Toast.LENGTH_SHORT ).show();
                        break;

                }
            }
        } );


        btnLogin.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(sign()){

               }
            }
        } );

        btnSignup.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent( Signin.this, MainActivity.class ) );
            }
        } );


    }

    public boolean sign() {
        String email = inputEmail.getText().toString();
        final String password = inputPassword.getText().toString();


            if (TextUtils.isEmpty( email )) {
            Toast.makeText( getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT ).show();

        } else if (TextUtils.isEmpty( password )) {
            Toast.makeText( getApplicationContext(), "Enter password!", Toast.LENGTH_SHORT ).show();

        } else {
            auth.signInWithEmailAndPassword( email, password )
                    .addOnCompleteListener( Signin.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            // progressBar.setVisibility(View.GONE);
                            if (!task.isSuccessful()) {
                                Toast.makeText( getApplicationContext(), "User Not Valid", Toast.LENGTH_SHORT ).show();
//                                //                                             return;e was an error

//                                if (password.length() < 6) {
//                                    Toast.makeText( getApplicationContext(), "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT ).show();
//                                    return;
//                                } else {
//                                    Toast.makeText( Signin.this, getString( R.string.auth_failed ), Toast.LENGTH_LONG ).show();
//                                }
                            } else {



                                mDb.child("UserModel").addListenerForSingleValueEvent( new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.getValue()!=null){

                                            UserModel model=dataSnapshot.getValue(UserModel.class);
                                            if(model!=null){
                                                // if(model.getColorCode().equalsIgnoreCase( chosecolor)){
                                                if(chosecolor.equalsIgnoreCase( model.getColorCode()) ){
                                                    Intent intent = new Intent( Signin.this, Dashboard.class );
                                                    startActivity( intent );
                                                }else{

                                                    Toast.makeText( Signin.this, "Wrong color code", Toast.LENGTH_SHORT ).show();
                                                }
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                } );












//                                checkColorCode();
//                                Intent intent = new Intent( Signin.this, Dashboard.class );
//                                startActivity( intent );

                            }
                        }
                    } );
        }

        return false;
    }

    private void checkColorCode() {
//        mDb.child("UserModel").addListenerForSingleValueEvent( new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                if(dataSnapshot.getValue()!=null){
//
//                    UserModel model=dataSnapshot.getValue(UserModel.class);
//                    if(model!=null){
//                       // if(model.getColorCode().equalsIgnoreCase( chosecolor)){
//                        if(chosecolor.equalsIgnoreCase( model.getColorCode()) ){
//                            Intent intent = new Intent( Signin.this, Dashboard.class );
//                                startActivity( intent );
//                        }else{
//
//                            Toast.makeText( Signin.this, "Wrong color code", Toast.LENGTH_SHORT ).show();
//                        }
//                    }
//                }
//           }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        } );
    }


}

