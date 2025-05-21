package ru.mirea.khvatovkl.lesson6;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText eTGroup;
    private EditText eTNumberOfList;
    private EditText eTCinema;
    private Button bSave;

    private static final String PREFS_NAME = "MyPrefs";
    private static final String KEY_GROUP = "group_number";
    private static final String KEY_LIST_NUMBER = "list_number";
    private static final String KEY_CINEMA = "favorite_cinema";
    private static final String KEY_LAUNCH_COUNT = "launch_count";

    private SharedPreferences sharedPref;

    private int launchCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eTGroup = findViewById(R.id.editTextgroup);
        eTNumberOfList = findViewById(R.id.editTextnumber);
        eTCinema = findViewById(R.id.editTextcinema);
        bSave = findViewById(R.id.buttonsave);

        sharedPref = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        launchCount = sharedPref.getInt(KEY_LAUNCH_COUNT, 0);
        launchCount++;
        sharedPref.edit().putInt(KEY_LAUNCH_COUNT, launchCount).apply();

        if (launchCount > 1) {
            loadPreferences();
        }

        bSave.setOnClickListener(v -> {
            savePreferences();
            Toast.makeText(MainActivity.this, "Данные сохранены", Toast.LENGTH_LONG).show();
        });
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sharedPref.edit().putInt(KEY_LAUNCH_COUNT, 0).apply();
    }

    private void savePreferences() {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY_GROUP, eTGroup.getText().toString());
        editor.putString(KEY_LIST_NUMBER, eTNumberOfList.getText().toString());
        editor.putString(KEY_CINEMA, eTCinema.getText().toString());
        editor.apply();
    }

    private void loadPreferences() {
        eTGroup.setText(sharedPref.getString(KEY_GROUP, ""));
        eTNumberOfList.setText(sharedPref.getString(KEY_LIST_NUMBER, ""));
        eTCinema.setText(sharedPref.getString(KEY_CINEMA, ""));
    }
}