package com.example.actionbarnavdrawerjava;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageOneFragment extends Fragment {


    public PageOneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_detail, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        View view = getView();

        if (view != null){

            TextView title = view.findViewById(R.id.textTitle);
            TextView desc = view.findViewById(R.id.textDesc);
            ImageView img = view.findViewById(R.id.imgView);

            title.setText(R.string.P11);
            desc.setText(R.string.P12);
            img.setImageResource(R.drawable.persiancat);
            img.setContentDescription("Image");
        }
    }


}
