package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        // 获取 Back 按钮
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置 Back 按钮的点击事件
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回到 Question20
                Intent intent = new Intent(Question21.this, Question20.class);
                startActivity(intent);
                finish(); // 结束当前 Activity
            }
        });

        // 设置 RadioGroup 的点击事件
        findViewById(R.id.next_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupRecyclingFrequency.getCheckedRadioButtonId();

                if (selectedId != -1) { // 检查是否选择了选项
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedFrequency = selectedRadioButton.getText().toString();

                    // 将选择的频率设置到 CarbonFootprintData
                    carbonFootprintData.setRecyclingFrequency(selectedFrequency);

                    // 跳转到 ResultActivity
                    Intent intent = new Intent(Question21.this, ResultActivity.class);
                    startActivity(intent);
                    finish(); // 结束当前 Activity
                } else {
                    // 显示提示信息
                    Toast.makeText(Question21.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
