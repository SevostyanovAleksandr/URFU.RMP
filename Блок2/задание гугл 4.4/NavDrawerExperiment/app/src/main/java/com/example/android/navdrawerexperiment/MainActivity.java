package com.example.android.navdrawerexperiment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 Это приложение настроило панель навигации со следующими вариантами: Импорт, галерея,
 Слайд-шоу, инструменты, общий доступ и отправка. Выбор любого варианта закрывает панель навигации
 и отображает тостовое сообщение, показывающее, какой выбор был выбран.
 Это приложение было создано с использованием шаблона действий в навигационном ящике.
 */
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**
     Создает представление содержимого и панель инструментов, настраивает расположение ящика и переключатель
     панель действий, а также настраивает вид навигации
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (fab != null) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Snackbar.make(view, "Загружаем", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            });
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        if (drawer != null) {
            drawer.addDrawerListener(toggle);
        }
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
    }

    /**
     * Управляет кнопкой "Назад": закрывает навигационный ящик.
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer != null) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    /**
     Раздувает меню опций.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Раздуть меню; это добавляет элементы на панель действий, если она присутствует.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /**
     Обрабатывает щелчок по пункту Настройки в меню опций.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Обработайте элемент панели действий, нажав здесь. Панель действий будет
// автоматически обрабатывать нажатия на кнопку "Домой"/"Вверх", пока
// как вы указываете родительское действие в AndroidManifest.xml .
        int id = item.getItemId();

//отсутствие проверки, упрощаемое утверждение
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Обрабатывает щелчок по элементу навигационного ящика. Он определяет, какой элемент был
     * щелкнул и отобразил всплывающее сообщение, показывающее, какой элемент.
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
// Обработайте элемент просмотра навигации, нажав здесь.
        switch (item.getItemId()) {
            case R.id.nav_camera:
// Обработайте действие импорта камеры (пока отобразите тост).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_camera));
                return true;
            case R.id.nav_gallery:
// Обработайте действие галереи (пока отобразите тост).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_gallery));
                return true;
            case R.id.nav_slideshow:
// Обработайте действие слайд-шоу (пока отобразите тост).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_slideshow));
                return true;
            case R.id.nav_manage:
// Обработайте действие tools (пока отобразите тост).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_tools));
                return true;
            case R.id.nav_share:
// Обработайте действие "Поделиться" (пока отобразите тост).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_share));
                return true;
            case R.id.nav_send:
// Обработайте действие отправки (пока отобразите тост).
                drawer.closeDrawer(GravityCompat.START);
                displayToast(getString(R.string.chose_send));
                return true;
            default:
                return false;
        }
    }

    /**
     * Отображает всплывающее сообщение.
     */
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}
