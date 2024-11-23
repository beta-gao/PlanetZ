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

public class Question9BeefFrequencyFragment extends Fragment {

    private RadioGroup radioGroupBeefFrequency;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beef_frequency, container, false);

        radioGroupBeefFrequency = view.findViewById(R.id.radioGroup_beef_frequency);
        Button nextButton = view.findViewById(R.id.next_button);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroupBeefFrequency.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    RadioButton selectedRadioButton = view.findViewById(selectedId);
                    String selectedFrequency = selectedRadioButton.getText().toString();

                    // Call the activity method to go to the next fragment
                    ((Question9) getActivity()).goToNextFragment("Beef", selectedFrequency);
                }
            }
        });

        return view;
    }
}