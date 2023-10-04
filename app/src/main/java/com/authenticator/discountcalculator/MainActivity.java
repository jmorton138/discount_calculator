package com.authenticator.discountcalculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.authenticator.discountcalculator.databinding.ActivityMainBinding;

import java.util.Locale;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText editTextPrice;
    private EditText editTextPercentDiscount;
    private TextView textSalePriceTotal;
    private Button calculateButton;
    private Button clearButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextPrice.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(10, 2)});
        editTextPercentDiscount = findViewById(R.id.editTextPercentDiscount);
        textSalePriceTotal = findViewById(R.id.textSalePriceTotal);
        calculateButton = findViewById(R.id.calculateButton);
        clearButton = findViewById(R.id.clearInputs);
        calculateButton.setOnClickListener(view -> {
            editTextPrice.setError(null);
            editTextPercentDiscount.setError(null);
            String priceString = editTextPrice.getText().toString();
            String percentageOff = editTextPercentDiscount.getText().toString();

            if (validateInputs(priceString, percentageOff)) {
                float priceInput = Float.parseFloat(priceString);
                float percentInput = Float.parseFloat(percentageOff);

                float discountedPrice = calculateDiscountedPrice(priceInput, percentInput);
                String discountedPriceString = String.format(Locale.ENGLISH,"%.2f", discountedPrice);

                textSalePriceTotal.setText(discountedPriceString);
                System.out.println(textSalePriceTotal.getText());
            }

        });

        clearButton.setOnClickListener(view -> {
            editTextPrice.setText("");
            editTextPercentDiscount.setText("");
            textSalePriceTotal.setText("");
        });

    }
    public boolean validateInputs(String priceString, String percentageOff) {
        boolean validated = true;
        if (TextUtils.isEmpty(priceString)) {
            editTextPrice.setError("Please input a price.");
            editTextPrice.requestFocus();
            validated = false;
        }
        if (TextUtils.isEmpty(percentageOff)) {
            editTextPercentDiscount.setError("Please input a discount percentage.");
            editTextPercentDiscount.requestFocus();
            validated = false;
        } else if (Float.parseFloat(percentageOff) >= 100) {
            editTextPercentDiscount.setError("Please enter a percent less than 100.");
            editTextPercentDiscount.requestFocus();
            validated = false;
        }
        return validated;
    }
    public float calculateDiscountedPrice(float beforePrice, float percentageOff) {
        percentageOff = percentageOff/100;
        return beforePrice  - (beforePrice * percentageOff);
    }
}