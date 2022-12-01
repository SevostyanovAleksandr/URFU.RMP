
    package com.example.android.scorekeeper;

    import android.os.Bundle;
    import android.support.v7.app.AppCompatActivity;
    import android.support.v7.app.AppCompatDelegate;
    import android.view.Menu;
    import android.view.MenuItem;
    import android.view.View;
    import android.widget.TextView;

    public class MainActivity extends AppCompatActivity {

        // Переменные-члены для удержания оценки
        private int mScore1;
        private int mScore2;

        // Переменные-члены для двух элементов score TextView
        private TextView mScoreText1;
        private TextView mScoreText2;

        // Tags to be used as the keys in OnSavedInstanceState
        static final String STATE_SCORE_1 = "Team 1 Score";
        static final String STATE_SCORE_2 = "Team 2 Score";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //текстовое представление по идентификатору
            mScoreText1 = findViewById(R.id.score_1);
            mScoreText2 = findViewById(R.id.score_2);

            // Восстанавливает результаты, если есть savedInstanceState.
            if (savedInstanceState != null) {
                mScore1 = savedInstanceState.getInt(STATE_SCORE_1);
                mScore2 = savedInstanceState.getInt(STATE_SCORE_2);

                //Установть текстовые представления
                mScoreText1.setText(String.valueOf(mScore1));
                mScoreText2.setText(String.valueOf(mScore2));
            }
        }

        /**
         * Handles the onClick of both the decrement buttons.
         *
         * @param view The button view that was clicked
         */
        public void decreaseScore(View view) {
            // Получите идентификатор кнопки, на которую был нажат.
            int viewID = view.getId();
            switch (viewID) {
                // Если бы это было в команде 1:
                case R.id.decreaseTeam1:
                    // Уменьшите оценку и обновите TextView.
                    mScore1--;
                    mScoreText1.setText(String.valueOf(mScore1));
                    break;
                // If it was Team 2:
                case R.id.decreaseTeam2:
                    // Уменьшите оценку и обновите TextView.
                    mScore2--;
                    mScoreText2.setText(String.valueOf(mScore2));
            }
        }

        public void increaseScore(View view) {
            // Получите идентификатор кнопки, на которую был нажат.
            int viewID = view.getId();
            switch (viewID) {
                // Если бы это было в команде 1:
                case R.id.increaseTeam1:
                    //Увеличьте оценку и обновите текстовое представление.
                    mScore1++;
                    mScoreText1.setText(String.valueOf(mScore1));
                    break;
                //Если бы это была команда 2:
                case R.id.increaseTeam2:
                    // Увеличьте оценку и обновите текстовое представление.
                    mScore2++;
                    mScoreText2.setText(String.valueOf(mScore2));
            }
        }

        /*
         Создает опцию меню ночного режима.
         */
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu, menu);
            // Измените метку меню в зависимости от состояния приложения.
            int nightMode = AppCompatDelegate.getDefaultNightMode();
            if(nightMode == AppCompatDelegate.MODE_NIGHT_YES){
                menu.findItem(R.id.night_mode).setTitle(R.string.day_mode);
            } else{
                menu.findItem(R.id.night_mode).setTitle(R.string.night_mode);
            }
            return true;
        }


        /*
         *Обрабатывает щелчки по элементам меню опций.
         */

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            // роверьте, был ли нажат правильный элемент.
            if (item.getItemId() == R.id.night_mode) {
                // Получите состояние приложения в ночном режиме.
                int nightMode = AppCompatDelegate.getDefaultNightMode();
                // Установите режим темы для перезапущенного действия.
                if (nightMode == AppCompatDelegate.MODE_NIGHT_YES) {
                    AppCompatDelegate.setDefaultNightMode
                            (AppCompatDelegate.MODE_NIGHT_NO);
                } else {
                    AppCompatDelegate.setDefaultNightMode
                            (AppCompatDelegate.MODE_NIGHT_YES);
                }
                //Повторите действие, чтобы изменение темы вступило в силу.
                recreate();
            }
            return true;
        }

        /*
         * Метод, который вызывается при изменении конфигурации
         */

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            //Сохраните результаты.
            outState.putInt(STATE_SCORE_1, mScore1);
            outState.putInt(STATE_SCORE_2, mScore2);
            super.onSaveInstanceState(outState);
        }
    }