package com.example.android.simpleasynctask;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 Класс SimpleAsyncTask расширяет AsyncTask для выполнения очень простого
 фоновая задача - в этом случае она просто переходит в спящий режим на произвольное количество времени.
 */

public class SimpleAsyncTask extends AsyncTask<Void,Void, String> {

    // Текстовое представление, в котором мы будем показывать результаты
    private WeakReference<TextView> mTextView;

    // Конструктор, который предоставляет ссылку на TextView из MainActivity
    SimpleAsyncTask(TextView tv) {
            mTextView = new WeakReference<>(tv);
    }

    /**
     * Выполняется в фоновом потоке.
     */
    @Override
    protected String doInBackground(Void... voids) {

        // Сгенерируйте случайное число от 0 до 10.
        Random r = new Random();
        int n = r.nextInt(11);

        // Сделайте так, чтобы задача занимала достаточно много времени, чтобы у нас было
        // время поворота телефона во время его работы.
        int s = n * 200;

        // Спящий режим на произвольное количество времени.
        try {
            Thread.sleep(s);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Возвращает строковый результат.
        return "Проснувшись наконец после долгого сна " + s + " миллисекунд!";
    }

    /**
     * Делает что-то с результатом в потоке пользовательского интерфейса;
     */
    protected void onPostExecute(String result) {
        mTextView.get().setText(result);
    }
}
