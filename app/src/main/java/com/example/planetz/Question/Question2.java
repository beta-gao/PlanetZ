package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planetz.R;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;

public class Question2 extends AppCompatActivity {

    private CarbonFootprintData carbonFootprintData;
    private RadioGroup radioGroupCarType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question2);

        // 获取数据单例
        carbonFootprintData = CarbonFootprintData.getInstance();

        // 初始化视图
        radioGroupCarType = findViewById(R.id.radioGroup_car_type);
        RadioButton backButton = findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 返回到上一题
                Intent intent = new Intent(Question2.this, Question1.class);
                startActivity(intent);
                finish();
            }
        });

        // 设置RadioGroup的监听器，点击选项直接跳转到下一题
        radioGroupCarType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId != -1) {
                    // 根据选择设置车辆类型
                    VehicleType vehicleType;
                    if (checkedId == R.id.radio_gasoline) {
                        vehicleType = VehicleType.GASOLINE;
                    } else if (checkedId == R.id.radio_diesel) {
                        vehicleType = VehicleType.DIESEL;
                    } else if (checkedId == R.id.radio_hybrid) {
                        vehicleType = VehicleType.HYBRID;
                    } else if (checkedId == R.id.radio_electric) {
                        vehicleType = VehicleType.ELECTRIC;
                    } else {
                        vehicleType = VehicleType.UNKNOWN;
                    }

                    // 转换枚举为字符串后更新数据模型
                    carbonFootprintData.setVehicleType(vehicleType.name());

                    // 跳转到下一问题
                    Intent intent = new Intent(Question2.this, Question3.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(Question2.this, "Please select a car type.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}