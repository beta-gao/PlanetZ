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

public class Question9PorkFrequencyFragment extends Fragment {

    private RadioGroup radioGroupPorkFrequency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pork_frequency, container, false);

        radioGroupPorkFrequency = view.findViewById(R.id.radioGroup_pork_frequency);
        Button nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupPorkFrequency.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    String selectedFrequency = selectedRadioButton.getText().toString();

                    ((Question9) getActivity()).goToNextFragment("Pork", selectedFrequency);
                }
            }
        });

        return view;
    }
}
