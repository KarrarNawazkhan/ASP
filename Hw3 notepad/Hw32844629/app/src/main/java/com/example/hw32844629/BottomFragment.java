package com.example.hw32844629;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.widget.GridLayout;

public class BottomFragment extends Fragment {
    private OnKeyInteractionListener mListener;

    public interface OnKeyInteractionListener {
        void onKeyEntered(String key);
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnKeyInteractionListener) {
            mListener = (OnKeyInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnKeyInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_fragment, container, false);
        GridLayout gridLayout = view.findViewById(R.id.keypad_grid);

        // To add letter buttons
        for (char letter = 'A'; letter <= 'Z'; letter++) {
            addButton(String.valueOf(letter), gridLayout);
        }
        // To add number buttons
        for (int number = 0; number <= 9; number++) {
            addButton(String.valueOf(number), gridLayout);
        }
        // now adding the symbols and space
        addButton(";", gridLayout);
        addButton("@", gridLayout);
        addButton("#", gridLayout);
        addButton("$", gridLayout);
        addButton("%", gridLayout);
        addButton("&", gridLayout);
        addButton(":", gridLayout);
        addButton("_", gridLayout);
        addButton("_", gridLayout);
        addButton("+", gridLayout);
        addButton("      ", gridLayout);
        return view;
    }
    private void addButton(String text, GridLayout gridLayout) {
        Button button = new Button(getContext());
        button.setText(text);
        button.setOnClickListener(v -> mListener.onKeyEntered(text));
        gridLayout.addView(button);
    }
}
