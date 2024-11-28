package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.ResultActivity;
import com.example.planetz.model.CarbonFootprintData;

public class Question21 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupRecyclingFrequency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question21);

        // 初始化 CarbonFootprintData 实例
        carbonFootprintData = CarbonFootprintData.getInstance();

        // 获取 RadioGroup
        radioGroupRecyclingFrequency = findViewById(R.id.radioGroup_recycling_frequency);

        // 设置 RadioGroup 的选中改变事件
        radioGroupRecyclingFrequency.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) { // 检查是否选择了选项
                RadioButton selectedRadioButton = findViewById(checkedId);
                String selectedFrequency = selectedRadioButton.getText().toString();

                // 将选择的频率设置到 CarbonFootprintData
                carbonFootprintData.setRecyclingFrequency(selectedFrequency);

                // 跳转到 ResultActivity
                Intent intent = new Intent(Question21.this, ResultActivity.class);
                startActivity(intent);
                finish(); // 结束当前 Activity
            } else {
                // 显示提示信息
                Toast.makeText(Question21.this, "Please select an option.", Toast.LENGTH_SHORT).show();
            }
        });

        // 获取 Back 按钮
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置 Back 按钮的点击事件
        backButton.setOnClickListener(v -> {
            // 返回到 Question20
            Intent intent = new Intent(Question21.this, Question20.class);
            startActivity(intent);
            finish(); // 结束当前 Activity
        });
    }
}
