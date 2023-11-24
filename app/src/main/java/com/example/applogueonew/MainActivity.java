package com.example.applogueonew;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ///inicio animacion el ejecutar
//para que sea fulscrem
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //agregar las animaciones

        Animation animation1= AnimationUtils.loadAnimation(this, R.anim.desplazamiento_arriba);
        Animation animation2= AnimationUtils.loadAnimation(this, R.anim.desplazamiento_abajo);


        TextView deTextView= findViewById(R.id.deTextView);
        TextView antonRMTextView= findViewById(R.id.antonRMTextView);
        ImageView logoImageView= findViewById(R.id.logoImageView);


        deTextView.setAnimation(animation2);
        antonRMTextView.setAnimation(animation2);
        logoImageView.setAnimation(animation1);

        new Handler().postDelayed(new Runnable() {
            //despues de que pasaron los 4 segundos de la animacion

            @Override
            public void run() {
                //si el usuario ya inicio sesion se va hacia el userACtiviti y si no se manda al login
                FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser(); //
                    if (user!= null){
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                        finish();
                    }else {//si no inicia sesion
//(Animaciones)
                        Intent intent= new Intent(MainActivity.this, LoginActivity.class);
                        Pair[] pairs= new Pair[2];
                        pairs[0]=new Pair<View, String>(logoImageView,"logoImageTrans");
                        pairs[1]=new Pair<View, String>(antonRMTextView,"antonRMTextView");
//si la version del jdk no soporta que se salte la animacion
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this,pairs);
                            startActivity(intent, options.toBundle());
                        }else {
                            startActivity(intent);
                            finish();
                        }

                        startActivity(intent);
                        finish();
                    }

            }

        },4000);
        //fin bloque animacion ejecutar
    }
}