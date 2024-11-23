package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.R;

public class Question1 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupUsingVehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question1);

        // 获取单例实例
        carbonFootprintData = CarbonFootprintData.getInstance();

        // 初始化视图
        radioGroupUsingVehicle = findViewById(R.id.radioGroup_using_vehicle);
        Button nextButton = findViewById(R.id.next_button);

        // 设置按钮点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupUsingVehicle.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    // 使用 if-else 判断选择的按钮
                    boolean isUsingVehicle;
                    if (selectedId == R.id.radio_yes) {
                        isUsingVehicle = true;
                    } else if (selectedId == R.id.radio_no) {
                        isUsingVehicle = false;
                    } else {
                        Toast.makeText(Question1.this, "Invalid selection. Please try again.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // 保存用户选择到数据模型
                    carbonFootprintData.setUsingVehicle(isUsingVehicle);

                    // 跳转到下一个问题
                    Intent intent;
                    if (isUsingVehicle) {
                        intent = new Intent(Question1.this, Question2.class);
                    } else {
                        intent = new Intent(Question1.this, Question4.class);
                    }
                    startActivity(intent);
                    finish();
                } else {
                    // 如果用户未选择任何选项，显示提示
                    Toast.makeText(Question1.this, "Please select an option before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}