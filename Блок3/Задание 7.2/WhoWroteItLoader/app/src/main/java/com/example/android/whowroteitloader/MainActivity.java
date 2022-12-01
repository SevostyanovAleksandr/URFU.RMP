package com.example.android.whowroteitloader;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 Приложение WhoWroteIt запрашивает API поиска книг на основе
 при поиске пользователя. Он использует AsyncTask для выполнения задачи поиска в
 фоновом режиме.
 */
public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBookInput = findViewById(R.id.bookInput);
        mTitleText = findViewById(R.id.titleText);
        mAuthorText = findViewById(R.id.authorText);

        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    /**
     Обработчик onClick для кнопки "Поиск книг".
     */
    public void searchBooks(View view) {
        // Получите строку поиска из поля ввода.
        String queryString = mBookInput.getText().toString();

        // Скрыть клавиатуру при нажатии кнопки.
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        // Проверьте состояние сетевого подключения.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // Если сеть доступна, подключена и поле поиска
        // // не пусто, запустите загрузчик книг AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()
                && queryString.length() != 0) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle, this);

            mAuthorText.setText("");
            mTitleText.setText(R.string.loading);
        }
// В противном случае обновите TextView, чтобы сообщить пользователю, что нет   соединени
// или нет поискового запроса.
        else {
            if (queryString.length() == 0) {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_search_term);
            } else {
                mAuthorText.setText("");
                mTitleText.setText(R.string.no_network);
            }
        }
    }


    @NonNull
    @Override
    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
        String queryString = "";

        if (args != null) {
            queryString = args.getString("queryString");
        }

        return new BookLoader(this, queryString);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String data) {
        try {
            // Преобразуйте ответ в объект JSON.
            JSONObject jsonObject = new JSONObject(data);
            // Получить JSONArray элементов книги.
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            // Инициализируйте поля итератора и результатов.
            int i = 0;
            String title = null;
            String authors = null;

// Ищите результаты в массиве элементов, завершая работу, когда найдены какзаголовок, так и автор
// , или когда все элементы были проверены.
            while (i < itemsArray.length() &&
                    (authors == null && title == null)) {
                // Получить текущую информацию о товаре.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Попробуйте получить автора и название из текущего элемента,
// поймайте, если какое-либо поле пусто, и двигайтесь дальше.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // Перейти к следующему пункту.
                i++;
            }

            // Если оба найдены, отобразите результат.
            if (title != null && authors != null) {
                mTitleText.setText(title);
                mAuthorText.setText(authors);
                //mBookInput.setText("");
            } else {
                // Если ничего не найдено, обновите пользовательский интерфейс, чтобы показать неудачные результаты.
                mTitleText.setText(R.string.no_results);
                mAuthorText.setText("");
            }

        } catch (Exception e) {
// Если onPostExecute не получает правильную строку JSON,
// обновите пользовательский интерфейс, чтобы показать неудачные результаты.
            mTitleText.setText(R.string.no_results);
            mAuthorText.setText("");
            e.printStackTrace();
        }

    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {
        // Ничего не делай.  Требуется интерфейсом.
    }
}
