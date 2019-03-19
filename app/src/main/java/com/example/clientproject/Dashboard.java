package com.example.clientproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Dashboard extends AppCompatActivity {
    Button Signout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_dashboard );
        Signout=(Button)findViewById( R.id.signout );
        Signout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent= new Intent( Dashboard.this ,Signin.class );
               startActivity( intent );
            }
        } );

    }
}
