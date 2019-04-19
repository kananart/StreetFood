package com.example.streetfood;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class DashboardLogoutOper extends Fragment {
    private SessionHandler session;

    public DashboardLogoutOper() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_logout_oper, container, false);
        session = new SessionHandler(getContext());
        Oper oper = session.getOperDetails();
        TextView welcomeText = view.findViewById(R.id.welcomeText);

        welcomeText.setText("Welcome "+oper.getName()+", your session will expire on "+oper.getSessionExpiryDate());

        Button logoutBtn = view.findViewById(R.id.btnLogout);

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session.logoutUser();
                Intent i = new Intent(String.valueOf(loginOper.class));
                startActivity(i);
                getActivity().finish();

            }
        });

        return view;
    }

}
