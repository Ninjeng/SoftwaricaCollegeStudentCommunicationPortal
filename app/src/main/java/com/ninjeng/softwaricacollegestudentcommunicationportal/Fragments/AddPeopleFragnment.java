package com.ninjeng.softwaricacollegestudentcommunicationportal.Fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ninjeng.softwaricacollegestudentcommunicationportal.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddPeopleFragnment extends Fragment {


    public AddPeopleFragnment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_people_fragnment, container, false);
    }

}
