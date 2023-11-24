package com.example.applogueonew;
//clase la que se pasa despues de que se logro el logueo
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserActivity extends AppCompatActivity {
TextView emailTextView;
MaterialButton logoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        emailTextView = findViewById(R.id.emailTextView);
        logoutButton = findViewById(R.id.logoutButton);
//si ya hay un usuario que se inicializo
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null){
            ///colocar el correo en el label que decia (correo)
            emailTextView.setText(user.getEmail());
        }
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cerrar sesion cuando se precione el boton
                FirebaseAuth.getInstance().signOut();
                Intent intent= new Intent(UserActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}