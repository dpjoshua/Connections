package com.example.connections;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class FaqFragment extends Fragment {

    private String phoneNo;
    private String getName;
    private String getEmail;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null) {
            phoneNo = args.getString("phoneNo");
            getName = args.getString("getName");
            getEmail = args.getString("getEmail");

        }


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_faq, container, false);
        Toast.makeText(getActivity(), "Phone number: " + phoneNo, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), " Email: " + getEmail, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), "Name: " + getName, Toast.LENGTH_SHORT).show();
        return view;
    }
}