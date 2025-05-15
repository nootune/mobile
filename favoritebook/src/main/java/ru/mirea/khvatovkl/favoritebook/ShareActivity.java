package ru.mirea.khvatovkl.favoritebook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ShareActivity extends AppCompatActivity {

    static final String QUOTES_KEY = "quotes_name";
    private TextView textView;
    private EditText editTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        textView = findViewById(R.id.textViewDeveloperBook);
        editTextMessage = findViewById(R.id.editTextBookName);
        Button buttonSend = findViewById(R.id.buttonSubmit);
        EditText editTextQuote = findViewById(R.id.editTextQuote);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String bookName = extras.getString(MainActivity.BOOK_NAME_KEY);
            String quotesName = extras.getString(MainActivity.QUOTES_KEY);
            textView.setText(String.format("Моя любимая книга: %s и цитата: %s", bookName, quotesName));
        }

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userMessage = editTextMessage.getText().toString();
                String quote = editTextQuote.getText().toString();

                Intent data = new Intent();
                data.putExtra(MainActivity.USER_MESSAGE, userMessage);
                data.putExtra(ShareActivity.QUOTES_KEY, quote);
                setResult(Activity.RESULT_OK,data);
                finish();
            }
        });
    }
}