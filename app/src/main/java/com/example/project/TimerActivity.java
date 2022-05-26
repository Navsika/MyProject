package com.example.project;

import static com.example.project.SettingsActivity.isRest;
import static com.example.project.SettingsActivity.isWorkTime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.project.Model.MoneyClass;

import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    SharedPreferences sp,sp1;
    private static final String SHARED_PREF_MONEY = "moneySharedPref";
    private static final String KEY_MONEY = "money";


    private static final String SHARED_PREF_TIME = "timeSharedPref";
    private static final String KEY_SHORT_TIME = "short";
    private static final String KEY_LONG_TIME = "long";

    //Intent с задачей
    public final static String TASK_TEXT = "task text";



   private static final long START_TIME_IN_MILLIS = isWorkTime * 60000L;
    private static final long SHORT_REST_TIME_IN_MILLIS = isRest * 60000L;

    private TextView timerText, taskTimer, moneyTimer;
    private Button buttonToStartPause, buttonToStop, buttonToCancel, buttonStartRest;

    private CountDownTimer mCountDownTimer;

    private boolean timerRunning;
    private boolean isWorkTimer;
    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mShortTimeLeftInMillis = SHORT_REST_TIME_IN_MILLIS;
    private long mTimeInPause;

    private ProgressBar timerProgress;
    private static int progressStatus = 0;
    private Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        sp1 = getSharedPreferences(SHARED_PREF_TIME, Context.MODE_PRIVATE);

        sp = getSharedPreferences(SHARED_PREF_MONEY, Context.MODE_PRIVATE);
        moneyTimer = findViewById(R.id.money_timer_activity);
        moneyTimer.setText(String.valueOf(sp.getInt(KEY_MONEY, 0)));



        Intent intent = getIntent();
        taskTimer = findViewById(R.id.ito_prosto_text);
        Bundle b = intent.getExtras();
        if(b!=null)
        {
            String j =(String) b.get(TASK_TEXT);
            taskTimer.setText(j);
        }



        timerText = findViewById(R.id.timer_text);
        timerProgress = (ProgressBar) findViewById(R.id.progress_bar);
        buttonToStartPause = findViewById(R.id.start_pause);
        buttonToStop = findViewById(R.id.stop_btn);
        buttonToCancel = findViewById(R.id.cancel_rest);
        buttonStartRest = findViewById(R.id.start_rest);




        buttonToStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if (isWorkTimer) {
                    timerProgress.setMax((int) (mTimeLeftInMillis / 1000));
                    if (timerRunning) {
                        pauseTimer();
                    } else {
                        startTimer();
                    }
              //  } else {
              //      timerProgress.setMax((int) (mShortTimeLeftInMillis / 1000));
               //     restTimer();
               // }


            }
        });

        buttonStartRest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restTimer();
                timerProgress.setMax((int)(mShortTimeLeftInMillis/1000));

            }
        });

        //кнопка стоп


        buttonToStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  if (isWorkTimer = true) {
                    stopTimer();
                    isWorkTimer = true;
             //   } else {
             //       stopRestTimer();
                //      isWorkTimer = true;
            //    }
                progressStatus = 0;
                timerProgress.setProgress(progressStatus);

            }
        });

        buttonToCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRestTimer();
                isWorkTimer = true;
                progressStatus = 0;
                timerProgress.setProgress(progressStatus);
            }
        });

    }

        //метод StartTimer
        private void startTimer () {
            mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mTimeLeftInMillis = millisUntilFinished;
                    updateCountDownText();
                    if (progressStatus < timerProgress.getMax()) {
                        progressStatus++;
                        timerProgress.setProgress(progressStatus);
                    }
                }

                @Override
                public void onFinish() {
                    int money = sp.getInt(KEY_MONEY,0);
                    money+=5;
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putInt(KEY_MONEY, money);
                    editor.apply();
                    moneyTimer.setText(String.valueOf(sp.getInt(KEY_MONEY, 0)));
                    isWorkTimer = false;
                    timerRunning = false;
                 //   buttonToStartPause.setText("START REST");
                    buttonStartRest.setVisibility(View.VISIBLE);
                    progressStatus=0;
                    timerProgress.setProgress(progressStatus);
                    buttonToStartPause.setVisibility(View.INVISIBLE);
                }
            }.start();
            isWorkTimer = true;

            timerRunning = true;
            buttonToStartPause.setText("PAUSE");
            buttonToStop.setVisibility(View.INVISIBLE);
            buttonStartRest.setVisibility(View.INVISIBLE);
        }


        private void pauseTimer () {
            isWorkTimer = true;
            mCountDownTimer.cancel();
            timerRunning = false;
            mTimeInPause = mTimeLeftInMillis;
            buttonToStartPause.setVisibility(View.VISIBLE);
            buttonToStartPause.setText("RESUME");
            buttonToStop.setVisibility(View.VISIBLE);
            buttonStartRest.setVisibility(View.INVISIBLE);
        }

        private void stopTimer () {
            isWorkTimer = true;
            timerRunning = false;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();
            buttonToStop.setVisibility(View.INVISIBLE);
            buttonToStartPause.setText("START");
            buttonToStartPause.setVisibility(View.VISIBLE);
            buttonStartRest.setVisibility(View.INVISIBLE);

        }


        private void updateCountDownText () {
            int minutes = (int) (mTimeLeftInMillis / 1000) / 60;
            int seconds = (int) (mTimeLeftInMillis / 1000) % 60;


            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            timerText.setText(timeLeftFormatted);
        }


        private void updateCountDownTextRest() {
            int minutes = (int) (mShortTimeLeftInMillis / 1000) / 60;
            int seconds = (int) (mShortTimeLeftInMillis / 1000) % 60;


            String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
            timerText.setText(timeLeftFormatted);
        }


        private void restTimer () {
            mCountDownTimer = new CountDownTimer(mShortTimeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    mShortTimeLeftInMillis = millisUntilFinished;
                    updateCountDownTextRest();
                    timerProgress.setMax((int) (mShortTimeLeftInMillis / 1000));
                     if (progressStatus<timerProgress.getMax()){
                       progressStatus++;
                       timerProgress.setProgress(progressStatus);
                     }
                }

                @Override
                public void onFinish() {
                    isWorkTimer = true;
                    timerRunning = false;
                    buttonToStartPause.setText("START");
                    buttonToStartPause.setVisibility(View.VISIBLE);
                    buttonToCancel.setVisibility(View.INVISIBLE);
                    buttonStartRest.setVisibility(View.INVISIBLE);
                }
            }.start();

            timerRunning = true;
            isWorkTimer = false;
         //   buttonToStartPause.setText("CANCEL REST");
            buttonToCancel.setVisibility(View.VISIBLE);
            buttonToStop.setVisibility(View.INVISIBLE);
            buttonToStartPause.setVisibility(View.INVISIBLE);
            buttonStartRest.setVisibility(View.INVISIBLE);


        }

        public void stopRestTimer () {
            timerRunning = false;
            isWorkTimer = true;
            mTimeLeftInMillis = START_TIME_IN_MILLIS;
            updateCountDownText();

            mShortTimeLeftInMillis = SHORT_REST_TIME_IN_MILLIS;
            buttonToStop.setVisibility(View.INVISIBLE);
            buttonToStartPause.setText("START");
            buttonToCancel.setVisibility(View.INVISIBLE);
            buttonToStartPause.setVisibility(View.VISIBLE);
            buttonStartRest.setVisibility(View.INVISIBLE);
        }



}