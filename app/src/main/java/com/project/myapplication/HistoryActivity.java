package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //To Get City name for refference To show the related Data
        Bundle extras = getIntent().getExtras();
        //Accessing the values form intent using key
        String cityname=extras.getString("cityname");


        //Assiging recycler view to show the data in the apporiprate activity
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewHistory);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        //Firebase Recyclerview to assign the data path and for accessing elements
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("history").child(cityname), Model.class)
                        .build();

        adapter = new HistoryAdapter(options);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        //starts the listening for values when activity starts
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        //stops looking for value when activity stops
        adapter.stopListening();
        super.onStop();

    }
}

class HistoryAdapter extends FirebaseRecyclerAdapter<Model, HistoryAdapter.HistoryViewholder> {

    public HistoryAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull HistoryViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.mhistoryImg);

        //for loading history name into recycler view
        holder.mhistoryTitle.setText(model.getName());

        //for loading history discription into recyclerview
        holder.mhistoryData.setText(model.getDiscription());

    }
    @NonNull
    @Override
    public HistoryViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_item, parent, false);
        return new HistoryViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class HistoryViewholder extends RecyclerView.ViewHolder {


        ImageView mhistoryImg;
        TextView mhistoryTitle;
        TextView mhistoryData;

        public HistoryViewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the data in appropriate location
            mhistoryImg = (ImageView) itemView.findViewById(R.id.ImgView);
            mhistoryTitle = (TextView) itemView.findViewById(R.id.titleTxtView);
            mhistoryData = (TextView) itemView.findViewById(R.id.dataTxtView);
        }
    }

}