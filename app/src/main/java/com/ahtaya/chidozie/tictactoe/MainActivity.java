package com.ahtaya.chidozie.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button by5, by4, by3, vsHuman, vsComputer, beX, beO, random, start;
    EditText pOne, pTwo;
    ImageView logo, restart;
    TextView title;
    Animation moveInRight, moveInLeft, moveOutRight, moveOutLeft, moveUp, fadeIn, fadeOut;
    int type;
    final static int BY_3 = 1;
    final static int BY_4 = 2;
    final static int BY_5 = 3;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        logo = findViewById(R.id.logo);
        by3 = findViewById(R.id.by3);
        by4 = findViewById(R.id.by4);
        by5 = findViewById(R.id.by5);
        vsHuman = findViewById(R.id.humans);
        vsComputer = findViewById(R.id.computer);
        beX = findViewById(R.id.x);
        beO = findViewById(R.id.o);
        random = findViewById(R.id.rand);
        start = findViewById(R.id.start);
        pOne = findViewById(R.id.pone);
        pTwo = findViewById(R.id.ptwo);
        restart = findViewById(R.id.restart);

        Animation logoAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        logoAnim.setRepeatMode(Animation.REVERSE);

        moveInRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_in);
        moveInLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_in);
        moveOutRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.right_out);
        moveOutLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.left_out);
        moveUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move_up);
        fadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
        fadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);

        title.startAnimation(fadeIn);
        logo.startAnimation(logoAnim);
        by3.startAnimation(moveInLeft);
        by4.startAnimation(moveInRight);
        by5.startAnimation(moveInLeft);

        by3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = BY_3;
                showVersus();
            }
        });

        by4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = BY_4;
                showVersus();
            }
        });

        by5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                type = BY_5;
                showVersus();
            }
        });

        vsComputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showXO();
            }
        });

        vsHuman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPlayer();
            }
        });

        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pOne.setVisibility(View.GONE);
                pTwo.setVisibility(View.GONE);
                random.setVisibility(View.GONE);
                beX.setVisibility(View.GONE);
                beO.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                vsComputer.setVisibility(View.GONE);
                vsHuman.setVisibility(View.GONE);
                by3.startAnimation(moveInLeft);
                by4.startAnimation(moveInRight);
                by5.startAnimation(moveInLeft);
                by3.setVisibility(View.VISIBLE);
                by4.setVisibility(View.VISIBLE);
                by5.setVisibility(View.VISIBLE);
                disappearAnim(fadeOut, restart);
            }
        });

        beO.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == BY_5)
                    intent = new Intent(MainActivity.this, Tic5Computer.class);
                else if (type == BY_4)
                    intent = new Intent(MainActivity.this, Tic4Computer.class);
                else
                    intent = new Intent(MainActivity.this, Tic3Computer.class);
                intent.putExtra("playerTurn", 2);
                startActivity(intent);

            }
        });

        beX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == BY_5)
                    intent = new Intent(MainActivity.this, Tic5Computer.class);
                else if (type == BY_4)
                    intent = new Intent(MainActivity.this, Tic4Computer.class);
                else
                    intent = new Intent(MainActivity.this, Tic3Computer.class);
                intent.putExtra("playerTurn", 1);
                startActivity(intent);
            }
        });

        random.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (type == BY_5)
                    intent = new Intent(MainActivity.this, Tic5Computer.class);
                else if (type == BY_4)
                    intent = new Intent(MainActivity.this, Tic4Computer.class);
                else
                    intent = new Intent(MainActivity.this, Tic3Computer.class);
                intent.putExtra("playerTurn", 0);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pOne.getText().toString().equals("") || pTwo.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(), "Enter names of players", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (type == BY_5)
                    intent = new Intent(MainActivity.this, Tic5Players.class);
                else if (type == BY_4)
                    intent = new Intent(MainActivity.this, Tic4Players.class);
                else
                    intent = new Intent(MainActivity.this, Tic3Players.class);
                intent.putExtra("pOne", pOne.getText().toString());
                intent.putExtra("pTwo", pTwo.getText().toString());
                startActivity(intent);
            }
        });
    }

    private void showPlayer() {
        pOne.startAnimation(moveUp);
        pTwo.startAnimation(moveUp);
        start.startAnimation(moveUp);
        disappearAnim(moveOutLeft, vsComputer);
        disappearAnim(moveOutRight, vsHuman);
        pOne.setVisibility(View.VISIBLE);
        pTwo.setVisibility(View.VISIBLE);
        start.setVisibility(View.VISIBLE);
    }

    private void showXO() {
        beO.startAnimation(moveUp);
        beX.startAnimation(moveUp);
        random.startAnimation(moveUp);
        disappearAnim(moveOutLeft, vsComputer);
        disappearAnim(moveOutRight, vsHuman);
        beO.setVisibility(View.VISIBLE);
        beX.setVisibility(View.VISIBLE);
        random.setVisibility(View.VISIBLE);
    }

    private void showVersus() {
        disappearAnim(moveOutLeft, by3);
        disappearAnim(moveOutRight, by4);
        disappearAnim(moveOutLeft, by5);
        vsHuman.setVisibility(View.VISIBLE);
        vsComputer.setVisibility(View.VISIBLE);
        vsHuman.startAnimation(moveInRight);
        vsComputer.startAnimation(moveInLeft);
        restart.startAnimation(fadeIn);
        restart.setVisibility(View.VISIBLE);
    }

    private void disappearAnim(Animation animation, final View button){

        button.startAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                button.setVisibility(View.GONE);
            }
        });
        button.setVisibility(View.GONE);
        animation.setAnimationListener(null);
    }
}
