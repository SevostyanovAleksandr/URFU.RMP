package com.example.android.whowroteitloader;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 Класс утилиты для использования Google Book Search API для загрузки книги
 информация.
 */
public class NetworkUtils {

    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Константы для различных компонентов запроса Books API.
    //
    // Базовый URL конечной точки для Books API.
    private static final String BOOK_BASE_URL =
            "https://www.googleapis.com/books/v1/volumes?";
    // Параметр для строки поиска.
    private static final String QUERY_PARAM = "q";
    // Параметр, ограничивающий результаты поиска.
    private static final String MAX_RESULTS = "maxResults";
    // Параметр для фильтрации по типу печати.
    private static final String PRINT_TYPE = "printType";


    /**
     Статический метод для выполнения фактического запроса к Books API.
     */
    static String getBookInfo(String queryString) {

        // Настройте переменные для блока try, которые необходимо закрыть в блоке
// finally
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
// Создайте полный URI запроса, ограничив результаты 10 элементами и
// печатными книгами.
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

             // Преобразуйте URI в URL.
            URL requestURL = new URL(builtURI.toString());

            // Откройте сетевое подключение.
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Получить входной поток.
            InputStream inputStream = urlConnection.getInputStream();

            // Создайте буферизованный считыватель из этого входного потока.
            reader = new BufferedReader(new InputStreamReader(inputStream));

            // Используйте StringBuilder для хранения входящего ответа.
            StringBuilder builder = new StringBuilder();

            String line;
            while ((line = reader.readLine()) != null) {
                // Добавьте текущую строку в строку.
                builder.append(line);

// Поскольку это JSON, добавление новой строки необязательно (это не
// повлияет на синтаксический анализ), но это делает отладку * намного * проще
// если вы распечатаете заполненный буфер для отладки.
                builder.append("\n");
            }

            if (builder.length() == 0) {
// Поток был пуст.  Выйдите без разбора.
                return null;
            }

            bookJSONString = builder.toString();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Закройте соединение и буферизованный считыватель.
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // Запишите окончательный ответ JSON в журнал
        Log.d(LOG_TAG, bookJSONString);

        return bookJSONString;
    }
}
