package com.example.currencyconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ArrayAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatDelegate;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText amount;
    Spinner fromCurrency, toCurrency;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ ADD THIS BLOCK HERE
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        boolean isDark = prefs.getBoolean("DarkMode", false);

        if (isDark) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

        // ⬇️ THIS MUST COME AFTER
        setContentView(R.layout.activity_main);

        amount = findViewById(R.id.amount);
        fromCurrency = findViewById(R.id.fromCurrency);
        toCurrency = findViewById(R.id.toCurrency);
        result = findViewById(R.id.result);

        String[] currencies = {"INR", "USD", "JPY", "EUR"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, currencies);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        fromCurrency.setAdapter(adapter);
        toCurrency.setAdapter(adapter);
    }

    public void convert(View view) {

        double[][] rates = {
                {1, 0.012, 1.6, 0.011},
                {83, 1, 133, 0.92},
                {0.62, 0.0075, 1, 0.0069},
                {90, 1.08, 145, 1}
        };
        String input = amount.getText().toString();

        if (input.isEmpty()) {
            result.setText(getString(R.string.enter_amount_error));
            return;
        }

        double amt = Double.parseDouble(input);

        int from = fromCurrency.getSelectedItemPosition();
        int to = toCurrency.getSelectedItemPosition();

        if (from == to) {
            result.setText(getString(R.string.same_currency, amt));
            return;
        }

        double converted = amt * rates[from][to];

        result.setText(getString(R.string.converted_value, converted));
    }
    public void openSettings(View view) {
        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
        startActivity(intent);
    }
}