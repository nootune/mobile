package ru.mirea.khvatovkl.notebook;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.os.Environment;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.*;

public class MainActivity extends AppCompatActivity {

    private EditText EditTexNameFile;
    private EditText EditTexQuote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditTexNameFile = findViewById(R.id.EditTextnamefile);
        EditTexQuote = findViewById(R.id.EditTextquote);
        Button bSave = findViewById(R.id.buttonsave);
        Button bLoad = findViewById(R.id.buttonload);

        bSave.setOnClickListener(v -> saveFile());
        bLoad.setOnClickListener(v -> loadFile());
    }

    private void saveFile() {
        String fileName = EditTexNameFile.getText().toString();
        String content = EditTexQuote.getText().toString();

        if (fileName.isEmpty()) {
            showToast("Введите название файла");
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            if (!path.exists()) path.mkdirs();

            File file = new File(path, fileName);

            try (FileOutputStream fos = new FileOutputStream(file, false)) {
                fos.write(content.getBytes());
                showToast("Файл сохранён: " + file.getAbsolutePath());
            }
        } catch (Exception e) {
            showToast("Ошибка сохранения: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadFile() {
        String fileName = EditTexNameFile.getText().toString();

        if (fileName.isEmpty()) {
            showToast("Введите название файла");
            return;
        }

        try {
            File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS);
            File file = new File(path, fileName);

            if (!file.exists()) {
                showToast("Файл не найден");
                return;
            }

            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(file), "UTF-8"))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            }

            EditTexQuote.setText(content.toString().trim());
            showToast("Данные успешно загружены");

        } catch (Exception e) {
            showToast("Ошибка загрузки: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}