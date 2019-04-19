package com.example.streetfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class meFragment extends Fragment {

    private Button btnuser;
    private Button btnRoper;

    public meFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        btnuser = (Button) view.findViewById(R.id.btnuser);
        btnuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent user = new Intent(getActivity(),loginUser.class);
                startActivity(user);
            }
        });
        btnRoper = (Button) view.findViewById(R.id.btnRoper);
        btnRoper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent oper = new Intent(getActivity(),loginOper.class);
                startActivity(oper);
            }
        });
        return view;
    }

}
