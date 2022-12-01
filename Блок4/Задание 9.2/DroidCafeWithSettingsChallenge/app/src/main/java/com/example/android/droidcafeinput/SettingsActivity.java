package com.example.android.droidcafeinput;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.RingtonePreference;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;

import java.util.List;

/**
 * PreferenceActivity, который представляет набор настроек приложения. На телефонной трубке
 * устройства, настройки представлены в виде единого списка. На планшетах настройки следующие
 * разделено по категориям, заголовки категорий отображаются слева от списка
 настроек *.
 */

public class SettingsActivity extends AppCompatPreferenceActivity {

    /**
     * * Прослушиватель изменения значения предпочтения, который обновляет сводку предпочтения
     * чтобы отразить его новую ценность.
     */
    private static Preference.OnPreferenceChangeListener
            sBindPreferenceSummaryToValueListener =
            new Preference.OnPreferenceChangeListener() {
        @Override
        public boolean onPreferenceChange(Preference preference,
                                          Object value) {
            String stringValue = value.toString();

            if (preference instanceof ListPreference) {
// Для получения предпочтений списка найдите правильное отображаемое значение в
                // // список записей предпочтения.
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                // Установите сводку так, чтобы она отражала новое значение.
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);

            } else if (preference instanceof RingtonePreference) {
                // Для настройки мелодии звонка найдите правильное отображаемое значение
                // с помощью RingtoneManager.
                if (TextUtils.isEmpty(stringValue)) {
                    // Пустые значения соответствуют "беззвучному" (без мелодии звонка).
                    preference.setSummary(R.string.pref_ringtone_silent);

                } else {
                    Ringtone ringtone = RingtoneManager.getRingtone(
                            preference.getContext(), Uri.parse(stringValue));

                    if (ringtone == null) {
                        // Очистите сводку, если произошла ошибка поиска.
                        preference.setSummary(null);
                    } else {
                        // Установите сводку так, чтобы она отражала отображение новой мелодии звонка
                        // имя.
                        String name = ringtone.getTitle(preference.getContext());
                        preference.setSummary(name);
                    }
                }

            } else {
                // Для всех других настроек установите для сводки значение
                // простое строковое представление.
                preference.setSummary(stringValue);
            }
            return true;
        }
    };

    /**
     * Определяет, имеет ли устройство очень большой экран. Например, 10"
     * таблетки очень большие.
     */
    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    /**
     ** Привязывает сводку предпочтения к его значению. Более конкретно, когда
     * значение предпочтения * изменяется, его краткое содержание (строка текста под заголовком
     * предпочтения *) обновляется, чтобы отразить значение. Сводка также
     * * немедленно обновляется при вызове этого метода. Точный формат отображения
     * * зависит от типа предпочтения.
     */
    private static void bindPreferenceSummaryToValue(Preference preference) {
        // Установите прослушиватель так, чтобы он отслеживал изменения значений.
        preference.setOnPreferenceChangeListener
                (sBindPreferenceSummaryToValueListener);

        // // Немедленно запустите прослушиватель с помощью предпочтения
        // текущее значение.
        sBindPreferenceSummaryToValueListener
                .onPreferenceChange(preference, PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), ""));
    }

    /**
     * Настраивает панель действий для действия.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupActionBar();
    }

    /**
     * Настройте панель действий android.app.ActionBar, если доступен API.
     */
    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Показать кнопку "Вверх" на панели действий.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if (!super.onMenuItemSelected(featureId, item)) {
                NavUtils.navigateUpFromSameTask(this);
            }
            return true;
        }
        return super.onMenuItemSelected(featureId, item);
    }

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.pref_headers, target);
    }

    /**
     * Этот метод останавливает внедрение фрагментов в вредоносные приложения.
     * Убедитесь, что здесь запрещены любые неизвестные фрагменты.
     */
    protected boolean isValidFragment(String fragmentName) {
        return PreferenceFragment.class.getName().equals(fragmentName)
                || GeneralPreferenceFragment
                .class.getName().equals(fragmentName)
                || DataSyncPreferenceFragment
                .class.getName().equals(fragmentName)
                || NotificationPreferenceFragment
                .class.getName().equals(fragmentName);
    }

    /**
     * Этот фрагмент показывает только общие настройки. Он используется, когда действие
     * отображает пользовательский интерфейс настроек с двумя панелями.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class GeneralPreferenceFragment
            extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_general);
            setHasOptionsMenu(true);

// Привязать сводки настроек EditText/List/Dialog/Ringtone
            // к их ценностям. Когда их значения меняются, их сводки
// обновляются, чтобы отразить новое значение, в соответствии с дизайном Android
            // руководящие принципы.
            bindPreferenceSummaryToValue(findPreference("example_text"));
            bindPreferenceSummaryToValue(findPreference("example_list"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                // // Сначала проверьте размер таблицы. Если true, запустите MainActivity.
                boolean tabletSize = getResources()
                        .getBoolean(R.bool.isTablet);
                if (tabletSize) {
                    startActivity(new Intent(getActivity(),
                            MainActivity.class));
                } else {
                    startActivity(new Intent(getActivity(),
                            SettingsActivity.class));
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Этот фрагмент показывает только настройки уведомлений. Он используется, когда действие
     * отображает пользовательский интерфейс настроек с двумя панелями.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class NotificationPreferenceFragment
            extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_notification);
            setHasOptionsMenu(true);

// Привязать сводки настроек EditText/List/Dialog/Ringtone
            // к их ценностям. Когда их значения меняются, их сводки
// обновляются, чтобы отразить новое значение, в соответствии с дизайном Android
            // руководящие принципы.
            bindPreferenceSummaryToValue
                    (findPreference("notifications_new_message_ringtone"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                // Check first for tabletSize. If true, start MainActivity.
                boolean tabletSize = getResources()
                        .getBoolean(R.bool.isTablet);
                if (tabletSize) {
                    startActivity(new Intent(getActivity(),
                            MainActivity.class));
                } else {
                    startActivity(new Intent(getActivity(),
                            SettingsActivity.class));
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Этот фрагмент показывает только данные и настройки синхронизации. Он используется, когда действие
     * отображает пользовательский интерфейс настроек с двумя панелями.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public static class DataSyncPreferenceFragment
            extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_account);
            setHasOptionsMenu(true);

// Привязать сводки настроек EditText/List/Dialog/Ringtone
            // к их ценностям. Когда их значения меняются, их сводки
// обновляются, чтобы отразить новое значение, в соответствии с дизайном Android
            // руководящие принципы.
            bindPreferenceSummaryToValue
                    (findPreference("sync_frequency"));
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if (id == android.R.id.home) {
                // // Сначала проверьте размер таблицы. Если true, запустите MainActivity.
                boolean tabletSize = getResources()
                        .getBoolean(R.bool.isTablet);
                if (tabletSize) {
                    startActivity(new Intent(getActivity(),
                            MainActivity.class));
                } else {
                    startActivity(new Intent(getActivity(),
                            SettingsActivity.class));
                }
                return true;
            }
            return super.onOptionsItemSelected(item);
        }
    }
}
