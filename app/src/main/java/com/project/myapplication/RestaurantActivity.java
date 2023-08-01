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

public class RestaurantActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    restaurantAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        //Accessing the values form intent using key
        String cityname=extras.getString("cityname");


        // recycler view to show the data in the apporiprate activity
        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewRestaurant);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        //Firebase Recyclerview to assign the data path and for accessing elements
        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("restaurants").child(cityname), Model.class)
                        .build();

        adapter = new restaurantAdapter(options);
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

class restaurantAdapter extends FirebaseRecyclerAdapter<Model, restaurantAdapter.restaurantViewholder> {

    public restaurantAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull restaurantViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.mrestaurantImg);

        //for loading restaurant name into recycler view
        holder.mrestaurantTitle.setText(model.getName());

        //for loading restaurant discription into recyclerview
        holder.mrestaurantData.setText(model.getDiscription());


    }


    @NonNull
    @Override
    public restaurantViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //the data ojects are inflated into the xml file single_data_item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_item, parent, false);
        return new restaurantViewholder(view);
    }

    //we need view holder to hold each objet form recyclerview and to show it in recyclerview
    class restaurantViewholder extends RecyclerView.ViewHolder {


        ImageView mrestaurantImg;
        TextView mrestaurantTitle;
        TextView mrestaurantData;

        public restaurantViewholder(@NonNull View itemView) {
            super(itemView);

            //asssiginig the address of the materials to show the data in appropriate location
            mrestaurantImg = (ImageView) itemView.findViewById(R.id.ImgView);
            mrestaurantTitle = (TextView) itemView.findViewById(R.id.titleTxtView);
            mrestaurantData = (TextView) itemView.findViewById(R.id.dataTxtView);
        }
    }

}