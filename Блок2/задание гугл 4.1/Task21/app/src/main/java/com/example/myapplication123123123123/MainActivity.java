package com.example.myapplication123123123123;
import static com.example.myapplication123123123123.R.id.fab;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FloatingActionButton fab = findViewById(R.id.fab);
         fab.setOnClickListener(new View.OnClickListener() {
            @Override
            // метод с помощью которого мы нажимая на обьект fab переходим на макет activity_order
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, OrderActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    // метод который обрабатывает клик изображения и возвращает значение
    public void showIceCreamOrder(View view) {
        changeFab();
        displayToast(getString(R.string.ice_cream_order_message));

    }

    public  void changeFab (){
        Resources resources = getResources();
        int backgroundColor = resources.getColor(R.color.black);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setCustomSize(250);
        fab.setBackgroundColor(backgroundColor);
    }
    // метод который обрабатывает клик изображения и возвращает значение
    public void showFroyoOrder(View view) {
        changeFab();
        displayToast(getString(R.string.froyo_order_message));
    }
    // метод который обрабатывает клик изображения и возвращает значение
    public void showDonutOrder(View view) {
        changeFab();
        displayToast(getString(R.string.donut_order_message));
    }
    // метод который показывает на экране сообщение
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_SHORT).show();
    }
}