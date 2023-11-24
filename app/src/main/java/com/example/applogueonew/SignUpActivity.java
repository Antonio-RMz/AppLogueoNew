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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class SignUpActivity extends AppCompatActivity {
    TextView nuevoUsuario, bienvenidoLabel, continuarLabel;
    ImageView singUpImageView;
    TextInputLayout usuarioSingUpTextFile, contraseñaTextFile;
    MaterialButton inicioSesion;

    TextInputEditText emailEditText, passwordEditText, confirmPasswordEditText;

    //metodo de firebase

    private FirebaseAuth mAuth;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        singUpImageView = findViewById(R.id.singUpImageView);
        bienvenidoLabel = findViewById(R.id.bienvenidoLabel);
        continuarLabel = findViewById(R.id.continuarLabel);
        usuarioSingUpTextFile = findViewById(R.id.usuarioSingUpTextFile);
        contraseñaTextFile = findViewById(R.id.contraseñaTextFile);
        inicioSesion = findViewById(R.id.inicioSesion);
        nuevoUsuario = findViewById(R.id.nuevoUsuario);

        //esto es para la version 4, regristro y validacion
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        ///termina elementos nuevos para la vercion 4



        nuevoUsuario.setOnClickListener((v -> {
            trastitionBack();
        }));
        //llamar al metodo cuando el usuario le da clic al texto nuevo usuario
        inicioSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(); //metodo que va a validar que la contrasenia tenga mas de 8 caracteres y que el correo sea un correo valido
            }
        });
        mAuth= FirebaseAuth.getInstance();
    }

    public void validate() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

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
        }else {
            passwordEditText.setError(null);
        }

        ///determinar que las contrasenias sean iguales
        if (!confirmPassword.equals(password)){
            confirmPasswordEditText.setError("Deben ser iguales");
            return;
        }else {
            //metodo que va a mandar los datos a firebase para que se registren
            registrar(email,password);
        }
    }

public void registrar (String email, String password){
mAuth.createUserWithEmailAndPassword(email,password)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    //si se pudo iniciar sesion que arroje a alguna actividad\fragmento
                    Intent intent= new Intent(SignUpActivity.this, UserActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    ///si no se pudo crear el usuario
                    Toast.makeText(SignUpActivity.this, "Fallo en registrarse", Toast.LENGTH_LONG).show();
                }
            }
        });

}
    //cuando se preciones la flecha de hacia atras
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        trastitionBack();
    }

    //metodo que realiza la transicion
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

