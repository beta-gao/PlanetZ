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

public class Question9ChickenFrequencyFragment extends Fragment {

    private RadioGroup radioGroupChickenFrequency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chicken_frequency, container, false);

        radioGroupChickenFrequency = view.findViewById(R.id.radioGroup_chicken_frequency);
        Button nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupChickenFrequency.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    String selectedFrequency = selectedRadioButton.getText().toString();

                    // 调用 Question9Activity 的方法来切换到下一个 Fragment
                    ((Question9) getActivity()).goToNextFragment("Chicken", selectedFrequency);
                }
            }
        });

        return view;
    }
}
