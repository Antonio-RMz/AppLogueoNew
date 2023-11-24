package com.example.applogueonew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {
    TextView bienvenidoLabel, continuarLabel, nuevoUsuario;
    ImageView loginImageView;
    TextInputLayout usuarioTextFile, contraseñaTextFile;
    MaterialButton inicioSesion;
    //elementos para logica de iniciar sesion
    TextInputEditText emailEditText, passwordEditText;
    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginImageView = findViewById(R.id.loginImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioTextFile = findViewById(R.id.usuarioTextFile);
        contraseñaTextFile = findViewById(R.id.contraseñaTextFile);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        //referencias para boton iniciar sesion

        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById((R.id.passwordEditText));
        mAuth = FirebaseAuth.getInstance();

        nuevoUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //de donde estamos a donde queremos ir
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                Pair[] pairs = new Pair[7];
                pairs[0] = new Pair<View, String>(loginImageView, "logoImageTrans");
                pairs[1] = new Pair<View, String>(bienvenidoLabel, "textTrans");
                pairs[2] = new Pair<View, String>(continuarLabel, "iniciaSesionTextTrans");
                pairs[3] = new Pair<View, String>(usuarioTextFile, "emailInputTextTrans");
                pairs[4] = new Pair<View, String>(contraseñaTextFile, "passwordInputTextTrans");
                pairs[5] = new Pair<View, String>(inicioSesion, "buttonSingInTrans");
                pairs[6] = new Pair<View, String>(nuevoUsuario, "newUserTrans");

                //si la version del jdk no soporta que se salte la animacion
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, pairs);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                    finish();
                }
            }
        });

        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();//validar si es un correo valido y validar contrase;a
            }
        });
    }

    public void validate() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //si el email es vacio o si es o no una direccion de email
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Correo invalido ");
            return;
        } else {
            emailEditText.setError(null);
        }

        //ahora para la contrasenia, debe ser de 8 caracteres
        if (password.isEmpty() || password.length() < 8) {
            passwordEditText.setError("Se necesitan mas de 8 caracteres");
            return;

        } else if (!Pattern.compile("[0-9]").matcher(password).find()) {
            passwordEditText.setError("Almenos un numero");
            return;
        } else {
            passwordEditText.setError(null);
        }


        //metodo que va a mandar los datos a firebase para que se registren
        inicioSesion(email, password);

    }

    public void inicioSesion(String email, String password){
mAuth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                  //se comnparan las credenciales y si pasa, ya puede iniciar sesion con el usuario
                    Intent intent= new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                }else {//si la contrasenia no es
                    Toast.makeText(LoginActivity.this, "Credenciales equivocadas, trata de nuevo", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}