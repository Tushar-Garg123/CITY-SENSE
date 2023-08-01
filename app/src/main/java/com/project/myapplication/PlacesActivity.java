package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class PlacesActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    PlacesAdapter adapter;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_places);

        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        //Accessing the values form intent using key
        String cityname=extras.getString("cityname");


        //Assiging recycler view to show the data in the apporiprate activity
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewFamousplaces);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        //Firebase Recyclerview to assign the data path and for accessing elements
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("famousplaces").child(cityname), Model.class)
                        .build();

        adapter = new PlacesAdapter(options);
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

class PlacesAdapter extends FirebaseRecyclerAdapter<Model, PlacesAdapter.PlacesViewholder> {

    public PlacesAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull PlacesViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.mfamoutplacesImg);

        //for loading restaurant name into recycler view
        holder.mfamoutplacesTitle.setText(model.getName());

        //for loading restaurant discription into recyclerview
        holder.mfamoutplacesData.setText(model.getDiscription());


    }



    @NonNull
    @Override
    public PlacesViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_item, parent, false);
        return new PlacesViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class PlacesViewholder extends RecyclerView.ViewHolder {


        ImageView mfamoutplacesImg;
        TextView mfamoutplacesTitle;
        TextView mfamoutplacesData;

        public PlacesViewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the data in appropriate location
            mfamoutplacesImg = (ImageView) itemView.findViewById(R.id.ImgView);
            mfamoutplacesTitle = (TextView) itemView.findViewById(R.id.titleTxtView);
            mfamoutplacesData = (TextView) itemView.findViewById(R.id.dataTxtView);
        }
    }

}