package com.android.example.roomwordssample;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

/**
 * База данных Word Room. Включает код для создания базы данных.
 * После того, как приложение создаст базу данных, все дальнейшие взаимодействия
 * с этим происходит через WordViewModel.
 */

@Database(entities = {Word.class}, version = 2, exportSchema = false)
public abstract class WordRoomDatabase extends RoomDatabase {

    public abstract WordDao wordDao();

    private static WordRoomDatabase INSTANCE;

    public static WordRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (WordRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Создайте базу данных здесь.
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            WordRoomDatabase.class, "word_database")
                            // Стирает и перестраивает вместо миграции, если объекта миграции нет.
                            // Миграция не является частью этого практического.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // Этот обратный вызов вызывается при открытии базы данных.
    // // В этом случае используйте PopulateDbAsync для заполнения базы данных
    // с исходным набором данных, если в базе данных нет записей.
    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){

                @Override
                public void onOpen (@NonNull SupportSQLiteDatabase db){
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    // Заполнить базу данных исходным набором данных
    // только в том случае, если в базе данных нет записей.
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final WordDao mDao;

        // Начальный набор данных
        private static String [] words = {"Арсентьев", "Двоеглазов", "Казанцева", "Утемова", "Верхнева",
                "Гирева", "Воротеленко"};

        PopulateDbAsync(WordRoomDatabase db) {
            mDao = db.wordDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Если у нас нет слов, то создайте начальный список слов.
            if (mDao.getAnyWord().length < 1) {
                for (int i = 0; i <= words.length - 1; i++) {
                    Word word = new Word(words[i]);
                    mDao.insert(word);
                }
            }
            return null;
        }
    }
}

