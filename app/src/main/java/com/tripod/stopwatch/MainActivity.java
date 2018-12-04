package com.tripod.stopwatch;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    TextView timerTextView;
    SeekBar timerSeekBar;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    public void resetTimer()
    {
        timerTextView.setText("0:30");
        timerSeekBar.setProgress(30);
        timerSeekBar.setEnabled(true);
        countDownTimer.cancel();
        counterIsActive=false;
        startButton.setText("start");
        counterIsActive= false;
    }

    public void updateTimer (int secondsRemaining){

        int minutes = secondsRemaining/60;
        int seconds = secondsRemaining - (minutes*60);

        String secondString = Integer.toString(seconds);
        if(seconds<=9) {

            secondString="0"+secondString;

        }
        timerTextView.setText(Integer.toString(minutes)+ ":"+secondString);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startButton = findViewById(R.id.startbutton);

        timerSeekBar = findViewById(R.id.seekBar);
        timerTextView = findViewById(R.id.TimertextView);

        timerSeekBar.setProgress(30);
        timerSeekBar.setMax(600);

        timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

               updateTimer(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // if counter is active and you are pressing the stop button !
                if (counterIsActive) {

                  resetTimer();


                }
                // when you are starting the timer
                else {

                    counterIsActive = true;
                    timerSeekBar.setEnabled(false);
                    startButton.setText("Stop!");

                 countDownTimer = new CountDownTimer(timerSeekBar.getProgress() * 1000 + 100, 1000) {
                    @Override
                    public void onTick(long l) {

                        updateTimer((int) (l / 1000));
                    }

                    @Override
                    public void onFinish() {

                        //Log.i("Finished","Time's up !");

                        MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.timeup);
                        mediaPlayer.start();
                        resetTimer();
                    }
                }.start();
            }
            }
        });


    }
}
