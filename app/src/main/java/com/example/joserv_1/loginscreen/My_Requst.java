package com.example.joserv_1.loginscreen;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by JoServ-1 on 15/02/2017.
 */

public class My_Requst extends Fragment {
    public My_Requst(){

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootview=inflater.inflate(R.layout.my_requst,container,false);
        return rootview;
    }
}
