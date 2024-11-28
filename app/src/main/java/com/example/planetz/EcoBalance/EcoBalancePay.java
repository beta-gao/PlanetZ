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

        // 获取返回主页按钮
        Button backToHomePageButton = findViewById(R.id.backToHomePageButton);

        // 设置返回主页按钮点击事件
        backToHomePageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回到主页面 (HomePageActivity)
                Intent intent = new Intent(EcoBalancePay.this, HomePageActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}