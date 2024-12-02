package com.example.planetz.Question;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        RadioButton backButton = view.findViewById(R.id.radio_back);

        // 设置返回按钮点击事件
        backButton.setOnClickListener(v -> {
            ((Question9) getActivity()).loadFragment(new Question9ChickenFrequencyFragment());
        });

        // 设置选项点击直接跳转
        radioGroupFishFrequency.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId != -1) {
                RadioButton selectedRadioButton = view.findViewById(checkedId);
                String selectedFrequency = selectedRadioButton.getText().toString();

                ((Question9) getActivity()).goToNextFragment("Fish", selectedFrequency);
            }
        });

        return view;
    }
}