package com.example.planetz.EcoBalance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.HomePageActivity;
import com.example.planetz.R;

public class EcoBalancePay extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eco_balance_pay);

        Button backToHomePageButton = findViewById(R.id.backToHomePageButton);

        backToHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EcoBalancePay.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}