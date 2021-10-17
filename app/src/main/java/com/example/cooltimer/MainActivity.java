package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    Button button;// создаем переменную на кнопку
    private SeekBar seekBar;// создаем переменную на сикбар
    private TextView textView;//создаем переменную на текст виев
    private boolean isTimerOf;// сощдаем переменную для флага на кнопку
    private CountDownTimer countDownTimer;// создаем переменную для обратного отсчета
    private int defoultInterval;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);// связываем все
        button = findViewById(R.id.start);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        seekBar.setMax(600);// выставляем максимальный прогресс сик бара для того что бы его енельзя было крутить больше
        isTimerOf = false;// ставим наш фалг в положение фолз
        //  seekBar.setProgress(60);// устанавливаем ползунок на 60 секунд
        setInterval(sharedPreferences);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override// переопределяем методы сикбара
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                long progress = i * 1000;// так пргресс(i) в милисекундах переводим в секунды
                sec(progress);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
            public void batto(View view) {// создаем метод для кнопки
                if (!isTimerOf) {// если кнопка  равна фолз
                    seekBar.setEnabled(false);// включаем видимость сикбара
                    button.setText("stop");// изменяем текст кнгопки
                    isTimerOf = true;//выставляем флаг
                    countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                        // ставим прогресс секнда интервал тожеб т.к прогресс в милисекундах умножаем на тысячу
                        @Override
                        public void onTick(long millisUntilFinished) {
                            sec(millisUntilFinished);// сюда вкладываем метод для того что бы у нас считало минуты и секунды
                        }

                        @Override
                        public void onFinish() {
                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());// вызываем метод с настройкой
                            if (sharedPreferences.getBoolean("enable sound", true)) {// проверяем ключ
                                String melody = sharedPreferences.getString("TitleMelody", "alarm");//ставим на выбор мелодию
                                if (melody.equals("alarm")) {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_siren_sound);
                                    mediaPlayer.start();// ставим мелодию на оконцовку
                                } else if (melody.equals("bell")) {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bell_sound);
                                    mediaPlayer.start();// ставим мелодию на оконцовку
                                } else if (melody.equals("bip")) {
                                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bip_sound);
                                    mediaPlayer.start();// ставим мелодию на оконцовку
                                }
                            }
                            resetTimer();// вызываем метод сброса таймера
                        }
                    }.start();// стартуем метод обратного отсчета
                } else {
                    resetTimer();// если нет то сброс
                }

            }
            private void setInterval(SharedPreferences sharedPreferences){// создаем метод для забора значений из наших настроек(изменение интервала таймера)
                defoultInterval=Integer.valueOf(sharedPreferences.getString("defolt interval","60"));// записываем в переменную данные нашего эдит преференса
// приводим к интежер так как у нас переменная инт а в таблице стринг
                sec(defoultInterval)                ;// уставливаем значение из едит текста
                seekBar.setProgress(defoultInterval);// устанвиваем прогресс для сик бара
            }

            private void resetTimer() {// метод обнуления таймера
                countDownTimer.cancel();// сбрасываем таймер
                //textView.setText("00:60");// приводим к старому числовому значению
                setInterval(sharedPreferences);// вставляем значение прописанное вручную
                button.setText("Start");// убираем тект кнопки
                seekBar.setEnabled(true);// выставляем палку сикбара активной
                isTimerOf = false;// кнопке ставим флаг неактивный
                // seekBar.setProgress(60);// и выставляем прогресс на обратно на 60(ползунок)
            }
        });
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);// регистрируем нашу таблицу
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {// метод для вызова меню
        MenuInflater menuInflater = getMenuInflater();// тут мы создаем меню
        menuInflater.inflate(R.menu.timer_menu, menu);// тут мы раздуваем наш ксмл файл и само меню
        return super.onCreateOptionsMenu(menu);// возвращаем
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {// создаем метод для управления элементами меню
        int id = item.getItemId();// получаем элемент который выбран меню/которое мы создали в ксмл файле
        if (id == R.id.setting) {
            Intent open = new Intent(this, Setting.class);
            startActivity(open);
            return true;
        } else if (id == R.id.abaut) {
            Intent About = new Intent(this, About.class);
            startActivity(About);
            return true;
        }
        return super.onOptionsItemSelected(item);
        }

    private void setInterval(SharedPreferences sharedPreferences){// создаем метод для забора значений из наших настроек(изменение интервала таймера)


               defoultInterval = Integer.valueOf(sharedPreferences.getString("defolt interval", "60"));// записываем в переменную данные нашего эдит преференса
// приводим к интежер так как у нас переменная инт а в таблице стринг

            long defolta = defoultInterval * 100;// т.к метод в милисекундах мы переводим в секунды
            sec(defolta);// уставливаем значение из едит текста
            seekBar.setProgress(defoultInterval);// устанвиваем прогресс для сик бара

    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("defolt interval")){
            setInterval(sharedPreferences);// вызываем метод для забора значений

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);// снимаем все настройки при разрушении активити
    }
 public void sec(long millisUntilFinished) {// метод отображения минут и секунд
    int minuts = (int) millisUntilFinished / 1000 / 60;//инт это целое число поэтому приводим к 1 , а не к тысяче
    int seconds = (int) millisUntilFinished / 1000 - (minuts * 60);// тут же приводим к секунде в минуте(то есть 60 секунд в минуте)
    String minuteString = "";// создаем переменную куда мы будем записывать минуты
    String secondsString = "";// создаем переменную куда мы будем записывать секунды
    if (minuts < 10) {//если минуты меньше 10, то есть выглядит как одно чсисло
        minuteString = "0" + minuts;
    } else {
        minuteString = String.valueOf(minuts);
    }// если нет отображаем так
    if (seconds < 10) {//секунды то же самое
        secondsString = "0" + seconds;
    } else {
        secondsString = String.valueOf(seconds);
    }
    textView.setText(minuteString + " : " + secondsString);// выводим на экран
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
