package com.example.joserv_1.loginscreen;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class PoolFragment extends Fragment {
 private RecyclerView PoolList;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabase1;

    public PoolFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View PoolView =inflater.inflate(R.layout.fragment_pool, container, false);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Request");
        mDatabase1= FirebaseDatabase.getInstance().getReference().child("Users");
        PoolList=(RecyclerView)PoolView.findViewById(R.id.pool_list);
        PoolList.setHasFixedSize(true);
        PoolList.setLayoutManager(new LinearLayoutManager(getActivity()));

        return PoolView;
    }

    public static class RequestِAdapterHolder extends RecyclerView.ViewHolder {
        View pView;

        public RequestِAdapterHolder(View itemView) {
            super(itemView);

            pView=itemView;

        }
        public void setTitle(String title){

          TextView  pool_title=(TextView)pView.findViewById(R.id.txtTitle);
            pool_title.setText(title);

        }
        public void setLocation (String location){

            TextView  pool_location=(TextView)pView.findViewById(R.id.txtLocation);
            pool_location.setText(location);

        }

        public void setFirst_Name(String first_name){

            TextView  pool_name=(TextView)pView.findViewById(R.id.txtName);
            pool_name.setText(first_name);

        }


    }



    @Override
    public void onStart() {
        super.onStart( );
        FirebaseRecyclerAdapter<RequestِAdapter, RequestِAdapterHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<RequestِAdapter, RequestِAdapterHolder>(
                RequestِAdapter.class,
                R.layout.pool_row,
                RequestِAdapterHolder.class,
                mDatabase

        )
        {
            @Override
            protected void populateViewHolder(RequestِAdapterHolder viewHolder, RequestِAdapter model, int position) {
                viewHolder.setTitle(model.getTitle());
                viewHolder.setLocation(model.getLocation());


            }
        };
        PoolList.setAdapter(firebaseRecyclerAdapter);



    }


}
