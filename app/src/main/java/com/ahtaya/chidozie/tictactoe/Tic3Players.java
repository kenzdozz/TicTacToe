package com.ahtaya.chidozie.tictactoe;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class Tic3Players extends AppCompatActivity {

    Button btnOne, btnTwo, btnThree, btnFour, btnFive, btnSix, btnSeven, btnEight, btnNine;
    TextView status, notifyTv;
    LinearLayout notify;
    boolean playerTwoTurn = false;
    boolean isPlay = true;
    String playerOneSign = "O";
    String playerTwoSign = "X";
    String win, playerSign;
    Animation animation, animation2;
    MediaPlayer player;
    int playe;
    TextView playerOne, playerTwo, tie, playerOneStat, playerTwoStat, tieStat;
    String pOne, pTwo;
    boolean sound = true;
    ImageButton soundBtn;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic3_players);

        btnOne = findViewById(R.id.btn1);
        btnTwo = findViewById(R.id.btn2);
        btnThree = findViewById(R.id.btn3);
        btnFour = findViewById(R.id.btn4);
        btnFive = findViewById(R.id.btn5);
        btnSix = findViewById(R.id.btn6);
        btnSeven = findViewById(R.id.btn7);
        btnEight = findViewById(R.id.btn8);
        btnNine = findViewById(R.id.btn9);
        status = findViewById(R.id.status);
        notify = findViewById(R.id.notify);
        notifyTv = findViewById(R.id.notifyTv);
        soundBtn = findViewById(R.id.sound);

        playerOne = findViewById(R.id.player_one);
        playerTwo = findViewById(R.id.player_two);
        tie = findViewById(R.id.tie);
        playerOneStat = findViewById(R.id.player_one_stat);
        playerTwoStat = findViewById(R.id.player_two_stat);
        tieStat = findViewById(R.id.tie_stat);

        Animation logoAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate);
        logoAnim.setRepeatMode(Animation.REVERSE);
        findViewById(R.id.logo).startAnimation(logoAnim);

        Intent intent = getIntent();
        pOne = intent.getStringExtra("pOne");
        pTwo = intent.getStringExtra("pTwo");

        Random random = new Random();
        int rand = random.nextInt(10);
        playerTwoTurn = rand % 2 == 1;

        if(!playerTwoTurn){
            playerOneSign = "X";
            playerTwoSign = "O";
        }
        playerPlay();

        playerOne.setText(pOne);
        playerTwo.setText(pTwo);

        Dialog dialog = new Dialog(this, R.style.PauseDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.notify);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.zoom);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);

        player = MediaPlayer.create(this, R.raw.play);
        player.setLooping(false); // Set looping
        player.setVolume(100,100);

        final Animation.AnimationListener animationListener = new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                player.seekTo(0);
                player.start();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                player.seekTo(0);
                player.start();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
            }
        };
        animation.setAnimationListener(animationListener);

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notify.setAnimation(null);
                notify.setVisibility(View.GONE);
            }
        });

        soundBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sound) {
                    animation.setAnimationListener(null);
                    sound = false;
                    soundBtn.setImageDrawable(getResources().getDrawable(R.drawable.no_sound));
                }else {
                    animation.setAnimationListener(animationListener);
                    sound = true;
                    soundBtn.setImageDrawable(getResources().getDrawable(R.drawable.sound));
                }
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tieStat.setText("0");
                playerOneStat.setText("0");
                playerTwoStat.setText("0");
            }
        });
    }


    void playerPlay(){

        if (!isPlay)
            return;

        String playing = pOne +"'s turn";
        playe = 1;
        playerSign = playerOneSign;

        if (playerTwoTurn) {
            playing = pTwo +"'s turn";
            playerSign = playerTwoSign;
            playe = 2;
            playerTwoTurn = false;
        }else {
            playerTwoTurn = true;
        }

        status.setText(playing);

        for (int i = 1; i < 10; i++) {
            final Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getText() == "") {
                        button.startAnimation(animation);
                        button.setText(playerSign);
                        if (checkWin(playerSign)){
                            isPlay = false;
                            removeClick();
                            setNotify(win, playe);
                            return;
                        }
                        playerPlay();
                    }
                }
            });
        }
    }

    boolean checkWin(String sign) {

        if (!isPlay)
            return false;

        return
                areEqual(btnOne.getText().toString(),      btnTwo.getText().toString(),   btnThree.getText().toString(), sign)   ||
                        areEqual(btnFour.getText().toString(),     btnFive.getText().toString(),  btnSix.getText().toString(), sign)     ||
                        areEqual(btnSeven.getText().toString(),    btnEight.getText().toString(), btnNine.getText().toString(), sign)    ||
                        areEqual(btnOne.getText().toString(),      btnFour.getText().toString(),  btnSeven.getText().toString(), sign)   ||
                        areEqual(btnTwo.getText().toString(),      btnFive.getText().toString(),  btnEight.getText().toString(), sign)   ||
                        areEqual(btnThree.getText().toString(),    btnSix.getText().toString(),   btnNine.getText().toString(), sign)    ||
                        areEqual(btnOne.getText().toString(),      btnFive.getText().toString(),  btnNine.getText().toString(), sign)    ||
                        areEqual(btnThree.getText().toString(),    btnFive.getText().toString(),  btnSeven.getText().toString(), sign)   ||
                        gameOver();
    }

    boolean areEqual( String a, String b, String c, String sign ){
        Boolean check = ( sign.equals(a)  &&  sign.equals(b) &&  sign.equals(c) );
        if (check && sign.equals(playerTwoSign)) {
            win = pTwo +" wins";
        }
        if (check && sign.equals(playerOneSign)) {
            win = pOne +" wins";
        }
        return check;
    }

    boolean gameOver(){

        int p = 0;
        for (int i = 1; i < 10; i++){
            Button button = findViewById(getResources().getIdentifier("btn"+i, "id", getPackageName()));
            if (!button.getText().equals(""))
                p++;
        }
        if (p == 9){
            win = "It's a tie";
            setNotify(win, 0);
            isPlay = false;
        }
        return false;
    }

    void setNotify(String win, int player){
        status.setText(win);
        notifyTv.setText(win);
        notify.setVisibility(View.VISIBLE);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        notify.setAnimation(animation2);

        if (player == 0){
            int stat = Integer.parseInt(tieStat.getText().toString()) + 1;
            tieStat.setText(String.valueOf(stat));
        }else if (player == 1){
            int stat = Integer.parseInt(playerOneStat.getText().toString()) + 1;
            Log.v("Here", String.valueOf(stat));
            playerOneStat.setText(String.valueOf(stat));
        }else if (player == 2){
            int stat = Integer.parseInt(playerTwoStat.getText().toString()) + 1;
            playerTwoStat.setText(String.valueOf(stat));
        }
    }

    void reset(){
        isPlay = true;
        notify.setAnimation(null);
        notify.setVisibility(View.GONE);
        for (int i = 1; i < 10; i++){
            Button button = findViewById(getResources().getIdentifier("btn"+i, "id", getPackageName()));
            button.setText("");
        }
        Random random = new Random();
        int rand = random.nextInt(10);
        playerTwoTurn = rand % 2 == 1;

        if(!playerTwoTurn){
            playerOneSign = "X";
            playerTwoSign = "O";
        }
        playerPlay();
    }

    void removeClick(){
        for (int i = 1; i < 10; i++) {
            final Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));
            button.setOnClickListener(null);
        }
    }
}
