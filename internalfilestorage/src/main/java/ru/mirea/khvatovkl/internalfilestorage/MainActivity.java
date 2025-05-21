package ru.mirea.khvatovkl.internalfilestorage;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String FILE_NAME = "data.txt";

    private EditText editTextData;
    private Button buttonSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        editTextData = findViewById(R.id.editTextdata);
        buttonSave = findViewById(R.id.buttonsave);

        buttonSave.setOnClickListener(v -> {
            String data = editTextData.getText().toString();
            try (FileOutputStream outputStream = openFileOutput(FILE_NAME, Context.MODE_PRIVATE)) {
                outputStream.write(data.getBytes());
                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Log.e(LOG_TAG, "Error writing file", e);
                Toast.makeText(MainActivity.this, "Error saving data", Toast.LENGTH_LONG).show();
            }
        });
    }
}