package com.example.android.roomwordssample;


import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.util.List;


/**
 * Это не полный набор тестов. Для простоты большинство ваших образцов не
 * включите тесты. Однако при строительстве помещения полезно убедиться, что оно работает, прежде чем
 * добавление пользовательского интерфейса.
 */

@RunWith(AndroidJUnit4.class)
public class WordDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private WordDao mWordDao;
    private WordRoomDatabase mDb;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getTargetContext();
        // Используя базу данных в памяти, потому что информация, хранящаяся здесь, исчезает, когда процесс
// завершается.
        mDb = Room.inMemoryDatabaseBuilder(context, WordRoomDatabase.class)
                // Разрешение запросов основного потока, только для тестирования.
                .allowMainThreadQueries()
                .build();
        mWordDao = mDb.wordDao();
    }

    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void insertAndGetWord() throws Exception {
        Word word = new Word("word");
        mWordDao.insert(word);
        List<Word> allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords());
        assertEquals(allWords.get(0).getWord(), word.getWord());
    }

    @Test
    public void getAllWords() throws Exception {
        Word word = new Word("aaa");
        mWordDao.insert(word);
        Word word2 = new Word("bbb");
        mWordDao.insert(word2);
        List<Word> allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords());
        assertEquals(allWords.get(0).getWord(), word.getWord());
        assertEquals(allWords.get(1).getWord(), word2.getWord());
    }

    @Test
    public void deleteAll() throws Exception {
        Word word = new Word("word");
        mWordDao.insert(word);
        Word word2 = new Word("word2");
        mWordDao.insert(word2);
        mWordDao.deleteAll();
        List<Word> allWords = LiveDataTestUtil.getValue(mWordDao.getAlphabetizedWords());
        assertTrue(allWords.isEmpty());
    }
}
