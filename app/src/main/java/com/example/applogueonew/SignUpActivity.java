package com.example.applogueonew;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class SignUpActivity extends AppCompatActivity {
TextView nuevoUsuario, bienvenidoLabel,continuarLabel;
ImageView singUpImageView;
TextInputLayout usuarioSingUpTextFile, contraseñaTextFile;
MaterialButton inicioSesion;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        singUpImageView = findViewById(R.id.singUpImageView);
        bienvenidoLabel= findViewById(R.id.bienvenidoLabel);
        continuarLabel= findViewById(R.id.continuarLabel);
        usuarioSingUpTextFile= findViewById(R.id.usuarioSingUpTextFile);
        contraseñaTextFile= findViewById(R.id.contraseñaTextFile);
        inicioSesion= findViewById(R.id.inicioSesion);
        nuevoUsuario= findViewById(R.id.nuevoUsuario);
//llamar al metodo cuando el usuario le da clic al nuevo usuario
        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                trastitionBack();
            }
        });
    }

//cuando se preciones la flecha de hacia atras
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        trastitionBack();
    }
public void trastitionBack() {
    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
    Pair[] pairs = new Pair[7];
    pairs[0] = new Pair<View, String>(singUpImageView, "logoImageTrans");
    pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
    pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
    pairs[3] = new Pair<View, String>(usuarioSingUpTextFile, "emailInputTextTrans");
    pairs[4] = new Pair<View, String>(contraseñaTextFile, "passwordInputTextTrans");
    pairs[5] = new Pair<View, String>(inicioSesion, "buttonSingInTrans");
    pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

    //si la version del jdk no soporta que se salte la animacion
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUpActivity.this, pairs);
        startActivity(intent, options.toBundle());
    } else {
        startActivity(intent);
        finish();
    }

    startActivity(intent);
    finish();
    }
}

