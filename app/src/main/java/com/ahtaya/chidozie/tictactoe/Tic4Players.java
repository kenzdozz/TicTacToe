package com.ahtaya.chidozie.tictactoe;

import android.app.Dialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
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

import java.util.Objects;
import java.util.Random;

public class Tic4Players extends AppCompatActivity {

    Button btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button btn10, btn11, btn12, btn13, btn14, btn15, btn16;
    TextView status, notifyTv;
    LinearLayout notify;
    boolean playerTwoTurn = false;
    boolean isPlay = true;
    String playerOneSign = "O";
    String playerTwoSign = "X";
    String win, playerSign;
    Animation animation, animation2;
    MediaPlayer player;
    TextView playerOne, playerTwo, tie, playerOneStat, playerTwoStat, tieStat;
    String pOne, pTwo;
    boolean sound = true;
    ImageButton soundBtn;
    int playe;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                startActivity(new Intent(this, AboutActivity.class));
                break;
            case android.R.id.home:
                NavUtils.navigateUpTo(this, Objects.requireNonNull(NavUtils.getParentActivityIntent(this)));
                break;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic4_players);

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        btn4 = findViewById(R.id.btn4);
        btn5 = findViewById(R.id.btn5);
        btn6 = findViewById(R.id.btn6);
        btn7 = findViewById(R.id.btn7);
        btn8 = findViewById(R.id.btn8);
        btn9 = findViewById(R.id.btn9);
        btn10 = findViewById(R.id.btn10);
        btn11 = findViewById(R.id.btn11);
        btn12 = findViewById(R.id.btn12);
        btn13 = findViewById(R.id.btn13);
        btn14 = findViewById(R.id.btn14);
        btn15 = findViewById(R.id.btn15);
        btn16 = findViewById(R.id.btn16);
        soundBtn = findViewById(R.id.sound);

        status = findViewById(R.id.status);
        notify = findViewById(R.id.notify);
        notifyTv = findViewById(R.id.notifyTv);

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

        if (!playerTwoTurn) {
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
        player.setVolume(100, 100);

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
                } else {
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


    void playerPlay() {

        if (!isPlay)
            return;

        String playing = pOne + "'s turn";
        playerSign = playerOneSign;
        playe = 1;

        if (playerTwoTurn) {
            playing = pTwo + "'s turn";
            playerSign = playerTwoSign;
            playerTwoTurn = false;
            playe = 2;
        } else {
            playerTwoTurn = true;
        }

        status.setText(playing);

        for (int i = 1; i < 17; i++) {
            final Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (button.getText() == "") {
                        button.startAnimation(animation);
                        button.setText(playerSign);
                        if (checkWin(playerSign)) {
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
                areEqual(btn1.getText().toString(), btn2.getText().toString(), btn3.getText().toString(),
                        btn4.getText().toString(), sign) ||

                        areEqual(btn5.getText().toString(), btn6.getText().toString(), btn7.getText().toString(),
                                btn8.getText().toString(), sign) ||

                        areEqual(btn9.getText().toString(), btn10.getText().toString(), btn11.getText().toString(),
                                btn12.getText().toString(), sign) ||

                        areEqual(btn13.getText().toString(), btn14.getText().toString(), btn15.getText().toString(),
                                btn16.getText().toString(), sign) ||

                        areEqual(btn1.getText().toString(), btn5.getText().toString(), btn9.getText().toString(),
                                btn13.getText().toString(), sign) ||

                        areEqual(btn2.getText().toString(), btn6.getText().toString(), btn10.getText().toString(),
                                btn14.getText().toString(), sign) ||

                        areEqual(btn3.getText().toString(), btn7.getText().toString(), btn11.getText().toString(),
                                btn15.getText().toString(), sign) ||

                        areEqual(btn4.getText().toString(), btn8.getText().toString(), btn12.getText().toString(),
                                btn16.getText().toString(), sign) ||

                        areEqual(btn1.getText().toString(), btn6.getText().toString(), btn11.getText().toString(),
                                btn16.getText().toString(), sign) ||

                        areEqual(btn4.getText().toString(), btn7.getText().toString(), btn10.getText().toString(),
                                btn13.getText().toString(), sign) ||

                        gameOver();
    }

    boolean areEqual(String a, String b, String c, String d, String sign) {
        Boolean check = (sign.equals(a) && sign.equals(b) && sign.equals(c) && sign.equals(d));
        if (check && sign.equals(playerTwoSign)) {
            win = pTwo + " wins";
        }
        if (check && sign.equals(playerOneSign)) {
            win = pOne + " wins";
        }
        return check;
    }

    boolean gameOver() {

        int p = 0;
        for (int i = 1; i < 17; i++) {
            Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));
            if (!button.getText().equals(""))
                p++;
        }
        if (p == 16) {
            win = "It's a tie";
            setNotify(win, 0);
            isPlay = false;
        }
        return false;
    }

    void removeClickListener() {
        for (int i = 1; i < 17; i++) {
            Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));
            button.setOnClickListener(null);
        }
    }

    void setNotify(String win, int player) {
        status.setText(win);
        notifyTv.setText(win);
        notify.setVisibility(View.VISIBLE);
        animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
        notify.setAnimation(animation2);
        removeClickListener();

        if (player == 0) {
            int stat = Integer.parseInt(tieStat.getText().toString()) + 1;
            tieStat.setText(String.valueOf(stat));
        } else if (player == 1) {
            int stat = Integer.parseInt(playerOneStat.getText().toString()) + 1;
            playerOneStat.setText(String.valueOf(stat));
        } else if (player == 2) {
            int stat = Integer.parseInt(playerTwoStat.getText().toString()) + 1;
            playerTwoStat.setText(String.valueOf(stat));
        }
    }

    void reset() {
        isPlay = true;
        notify.setAnimation(null);
        notify.setVisibility(View.GONE);
        for (int i = 1; i < 17; i++) {
            Button button = findViewById(getResources().getIdentifier("btn" + i, "id", getPackageName()));
            button.setText("");
        }
        Random random = new Random();
        int rand = random.nextInt(10);
        playerTwoTurn = rand % 2 == 1;

        if (!playerTwoTurn) {
            playerOneSign = "X";
            playerTwoSign = "O";
            playerPlay();
        }
    }
}
