package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
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

public class BusstationActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    BusstationAdapter adapter;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busstation);

        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        //Accessing the values form intent using key
        String cityname=extras.getString("cityname");


        //Assiging recycler view to show the data in the apporiprate activity
        mRecyclerview = (RecyclerView) findViewById(R.id.busstation);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        //Firebase Recyclerview to assign the data path and for accessing elements
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("busstation").child(cityname), Model.class)
                        .build();

        adapter = new BusstationAdapter(options);
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

class BusstationAdapter extends FirebaseRecyclerAdapter<Model, BusstationAdapter.BusstationViewholder> {

    public BusstationAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull BusstationViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.mbusstationImg);

        //for loading restaurant name into recycler view
        holder.mbusstationTitle.setText(model.getName());

        //for loading restaurant discription into recyclerview
        holder.mbusstationData.setText(model.getDiscription());


    }


    @NonNull
    @Override
    public BusstationViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_item, parent, false);
        return new BusstationViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class BusstationViewholder extends RecyclerView.ViewHolder {


        ImageView mbusstationImg;
        TextView mbusstationTitle;
        TextView mbusstationData;

        public BusstationViewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the data in appropriate location
            mbusstationImg = (ImageView) itemView.findViewById(R.id.ImgView);
            mbusstationTitle = (TextView) itemView.findViewById(R.id.titleTxtView);
            mbusstationData = (TextView) itemView.findViewById(R.id.dataTxtView);
        }
    }

}