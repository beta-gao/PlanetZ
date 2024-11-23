package com.example.planetz.Question;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.planetz.model.CarbonFootprintData;
import com.example.planetz.model.VehicleType;
import com.example.planetz.R;

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
        Button nextButton = findViewById(R.id.next_button);

        // 设置按钮点击事件
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupCarType.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    // 根据选择设置车辆类型
                    VehicleType vehicleType;
                    if (selectedId == R.id.radio_gasoline) {
                        vehicleType = VehicleType.GASOLINE;
                    } else if (selectedId == R.id.radio_diesel) {
                        vehicleType = VehicleType.DIESEL;
                    } else if (selectedId == R.id.radio_hybrid) {
                        vehicleType = VehicleType.HYBRID;
                    } else if (selectedId == R.id.radio_electric) {
                        vehicleType = VehicleType.ELECTRIC;
                    } else {
                        vehicleType = VehicleType.UNKNOWN;
                    }

                    // 更新数据模型
                    carbonFootprintData.setVehicleType(vehicleType);

                    // 跳转到下一问题
                    Intent intent = new Intent(Question2.this, Question3.class);
                    startActivity(intent);
                    finish();
                } else {
                    // 提示用户选择一个选项
                    Toast.makeText(Question2.this, "Please select a car type before proceeding.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}