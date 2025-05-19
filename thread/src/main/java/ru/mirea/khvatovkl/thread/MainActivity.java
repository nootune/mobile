package ru.mirea.khvatovkl.thread;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mirea.khvatovkl.thread.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String parString = binding.editTextpary.getText().toString();
                String daysString = binding.editTextdays.getText().toString();

                if (TextUtils.isEmpty(parString) || TextUtils.isEmpty(daysString)) {
                    binding.textViewtext.setText("Пожалуйста, заполните все поля.");
                    return;
                }

                int numberOfPairs = Integer.parseInt(parString);
                int numberOfDays = Integer.parseInt(daysString);
                new CalculateAverageTask().execute(numberOfPairs, numberOfDays);
            }
        });
    }
    private class CalculateAverageTask extends AsyncTask<Integer, Void, Double> {

        @Override
        protected Double doInBackground(Integer... params) {
            int numberOfPairs = params[0];
            int numberOfDays = params[1];
            if (numberOfDays == 0) {
                return 0.0;
            }
            return (double) numberOfPairs / numberOfDays;
        }
        @Override
        protected void onPostExecute(Double result) {
            binding.textViewtext.setText(String.format("Среднее количество пар в день: %.2f", result));
        }
    }
}