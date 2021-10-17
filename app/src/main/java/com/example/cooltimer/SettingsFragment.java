package com.example.cooltimer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.preference.CheckBoxPreference;
import androidx.preference.EditTextPreference;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragment;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceScreen;

import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;

public class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener, PreferenceChangeListener, Preference.OnPreferenceChangeListener {// наследуемс\я от класса фрагмент
    // для того что бы отображалось каждое изменение т. к в онкрейт отображается один раз при вызове мы иммплементируем слушатель шареда
    // этот интерфейс служит для отслеживания измененеий в файле шаредпреференсес первый интерфейс второй для отслеживания конкретной настройки до помещения в файл значения(если возвращает тру то в шаред записывается)
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
          addPreferencesFromResource(R.xml.timer_preferens);// берем настройку из файла
        SharedPreferences sharedPreferences= getPreferenceScreen().getSharedPreferences();
        PreferenceScreen preferenceScreen=getPreferenceScreen();
        int count= preferenceScreen.getPreferenceCount();// получить количество настроек на преференсе
        for (int i=0;i<count;i++) {// перебираем наши настройки
            Preference preference = preferenceScreen.getPreference(i);// вкладываем отображение экрана по индексу ай
            if (!(preference instanceof CheckBoxPreference)) {// если это не чек бокс тогда выполняем код
                String value=sharedPreferences.getString(preference.getKey(), "");// присваиваем значения которые мы получаем из шаред преференсес
         setPreference(preference,value);// вкладываем значение обьекта преференс и валуе
            }
        }
        Preference preference=findPreference("defolt interval");
        preference.setOnPreferenceChangeListener(this);// именно для этой настройки мы имплементируем метод онпреференс
    }private void setPreference(Preference preference,String value){
        if (preference instanceof ListPreference) {// проверяем является ли преференст лист преференс
            ListPreference listPreference = (ListPreference) preference;//если да создаем новый обьект
            int index = listPreference.findIndexOfValue(value);// присваиваем индексу по строке валуе (название в аррай листе)
            if (index >= 0) {// по индексу полученному из этого значения
                listPreference.setSummary(listPreference.getEntries()[index]);// получаем запись по индексу
            }
        }else if (preference instanceof EditTextPreference){// ПОКАЗЫВАЕМ НАШЕ ДЕФОЛТНОЕ ЗНАЧЕНИЕ
            preference.setSummary(value); }
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference=findPreference(key);// создаем переменную и присваиваем ему настройку по ключу
        if (!(preference instanceof CheckBoxPreference)){
            String value=sharedPreferences.getString(preference.getKey(),"" ); //присваиваем значения(ключи) которые мы получаем из шаред преференсес
            setPreference(preference,value);
        }
    }
    @Override//переопределяем метод окрейт для регистрации нашего слушателя
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();// при разрушении активити снимаем с регистрации
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    }



    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {// метод для проверки корректного значения
        if (preference.getKey().equals("defolt interval")) {
            String defoltInterval = (String) newValue;
            try {
                int defolt = Integer.parseInt(defoltInterval);
            } catch (Exception ex) {
                Toast.makeText(getContext(),"Введите целое число",Toast.LENGTH_LONG).show();
            }
        }
        return false;
    }
    @Override
    public void preferenceChange(PreferenceChangeEvent evt) {

    }
}
