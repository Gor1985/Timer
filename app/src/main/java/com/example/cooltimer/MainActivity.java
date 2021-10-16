package com.example.cooltimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {
    Button button;// создаем переменную на кнопку
    private SeekBar seekBar;// создаем переменную на сикбар
    private TextView textView;//создаем переменную на текст виев
    private  boolean isTimerOf;// сощдаем переменную для флага на кнопку
  private CountDownTimer countDownTimer;// создаем переменную для обратного отсчета
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// связываем все
        button = findViewById(R.id.start);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        seekBar.setOnSeekBarChangeListener(this);

        seekBar.setMax(600);// выставляем максимальный прогресс сик бара для того что бы его енельзя было крутить больше
 isTimerOf=false;// ставим наш фалг в положение фолз
        seekBar.setProgress(60);// устанавливаем ползунок на 60 секунд
    }

    @Override// переопределяем методы сикбара
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        long progress=i*1000;// так пргресс(i) в милисекундах переводим в секунды
        sec(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    ;// запускаем метод

    public void batto(View view) {// создаем метод для кнопки
        if (!isTimerOf) {// если кнопка  равна фолз
            seekBar.setEnabled(false);// включаем видимость сикбара
            button.setText("stop");// изменяем текст кнгопки
            isTimerOf=true;//выставляем флаг
            countDownTimer=new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
// ставим прогресс секнда интервал тожеб т.к прогресс в милисекундах умножаем на тысячу
                @Override
                public void onTick(long millisUntilFinished) {
                    sec(millisUntilFinished);// сюда вкладываем метод для того что бы у нас считало минуты и секунды
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer=MediaPlayer.create(getApplicationContext(),R.raw.alarm_siren_sound);
                    mediaPlayer.start();// ставим мелодию на оконцовку
                    resetTimer();// вызываем метод сброса таймера
                }

            }.start();// стартуем метод обратного отсчета
        }else {
resetTimer();// если нет то сброс
        }

    }
    private void sec(long millisUntilFinished) {// метод отображения минут и секунд
        int minuts = (int) millisUntilFinished / 1000 / 60;//инт это целое число поэтому приводим к 1 , а не к тысяче
        int seconds = (int) millisUntilFinished / 1000 - (minuts * 60);// тут же приводим к секунде в минуте(то есть 60 секунд в минуте)
        String minuteString = "";// создаем переменную куда мы будем записывать минуты
        String secondsString = "";// создаем переменную куда мы будем записывать секунды
        if (minuts < 10) {//если минуты меньше 10, то есть выглядит как одно чсисло
            minuteString = "0" + minuts;
        } else {
            minuteString = String.valueOf(minuts); }// если нет отображаем так
        if (seconds < 10) {//секунды то же самое
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds); }
            textView.setText(minuteString + " : " + secondsString);// выводим на экран
        }
        private void resetTimer(){// метод обнуления таймера
            countDownTimer.cancel();// сбрасываем таймер
            textView.setText("00:60");// приводим к старому числовому значению
            button.setText("Start");// убираем тект кнопки
            seekBar.setEnabled(true);// выставляем палку сикбара активной
            isTimerOf=false;// кнопке ставим флаг неактивный
            seekBar.setProgress(60);// и выставляем прогресс на обратно на 60(ползунок)
    }
    }












/*
        CountDownTimer countDownTimer = new CountDownTimer(2000, 3000) {// запускаем метод обратного отсчета ставим сколько по времени и интервал
            @Override
            public void onTick(long l) {
               Log.d("BBBBBB",String.valueOf(l/2000)+" секунды все");// тут выполняется код с заданными параметрами
            }

            @Override
            public void onFinish() {
                Log.d("BBBBBB","финиш");
            }
        };
        countDownTimer.start();






     Handler handler=new Handler(); вызываем класс хандлер
        Runnable runnable=new Runnable() {запускаем метод рунабле
            @Override
            public void run() {
                Log.d("Runabble", "dve secundy proshli");// пишем наш код
                handler.postDelayed(this,2000);//ставим контекст и иннтервал


            }
        };   handler.post(runnable);// запускаем метод
    }*/
