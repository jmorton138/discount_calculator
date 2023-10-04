package com.authenticator.discountcalculator;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.authenticator.discountcalculator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private EditText editTextPrice;
    private EditText editTextPercentDiscount;
    private TextView textSalePriceTotal;
    private Button calculateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextPercentDiscount = findViewById(R.id.editTextPercentDiscount);
        textSalePriceTotal = findViewById(R.id.textSalePriceTotal);
        calculateButton = findViewById(R.id.calculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTextPrice.setError(null);
                editTextPercentDiscount.setError(null);
                String priceString = editTextPrice.getText().toString();
                String percentageOff = editTextPercentDiscount.getText().toString();
                if (validateInputs(priceString, percentageOff)) {
                    float priceInput = new Float(priceString);
                    float percentInput = new Float((percentageOff));
                    float discountedPrice = calculateDiscountedPrice(priceInput, percentInput);
                    String discountedPriceString = String.format("%.2f", discountedPrice);
                    textSalePriceTotal.setText(discountedPriceString);
                }

            }
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
        }
        return validated;
    }
    public float calculateDiscountedPrice(float beforePrice, float percentageOff) {
        percentageOff = percentageOff/100;
        float afterDiscountPrice = beforePrice  - (beforePrice * percentageOff);
        return afterDiscountPrice;
    }
}