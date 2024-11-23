package com.example.planetz.Question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.planetz.R;

public class Question9FishFrequencyFragment extends Fragment {

    private RadioGroup radioGroupFishFrequency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fish_frequency, container, false);

        radioGroupFishFrequency = view.findViewById(R.id.radioGroup_fish_frequency);
        Button nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupFishFrequency.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    String selectedFrequency = selectedRadioButton.getText().toString();

                    // 调用 Question9Activity 的方法来保存数据并跳转到下一个问题
                    ((Question9) getActivity()).goToNextFragment("Fish", selectedFrequency);
                }
            }
        });

        return view;
    }
}