package ua.com.azbest.apptest3.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import ua.com.azbest.apptest3.R;

public class TextFragment extends Fragment {

    View view;
    TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String data = getArguments().getString("data");
        view = inflater.inflate(R.layout.text_fragment, container, false);
        textView = view.findViewById(R.id.text_content);
        textView.setText(data);
        return view;
    }
}