package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HotelActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    hotelAdapter adapter;
    EditText HTextview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);

        //To Get City name for refference
        Bundle extras = getIntent().getExtras();
        String cityname=extras.getString("cityname");

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerviewHotel);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        /*HTextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt = HTextview.getText().toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("hotelrooms").child("hotelrooms").startAt(searchtxt).endAt(searchtxt + "\uf8ff"), Model.class)
                                .build();

                adapter = new hotelAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt = HTextview.getText().toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("hotelrooms").child("hotelrooms").startAt(searchtxt).endAt(searchtxt + "\uf8ff"), Model.class)
                                .build();

                adapter = new hotelAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence = editable.toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("hotelrooms").child("hotelrooms").startAt(charSequence).endAt(charSequence + "\uf8ff"), Model.class)
                                .build();

                adapter = new hotelAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);

            }
        });*/


       FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("hotelrooms").child(cityname), Model.class)
                        .build();

        adapter = new hotelAdapter(options);
        mRecyclerview.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        adapter.startListening();
        super.onStart();
    }

    @Override
    public void onStop() {
        adapter.stopListening();
        super.onStop();

    }
}

class hotelAdapter extends FirebaseRecyclerAdapter<Model, hotelAdapter.hotelViewholder> {

    public hotelAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull hotelViewholder holder, int position, @NonNull Model model) {


        //For Loading image into imageview using url
        Picasso.get().load(model.getImage()).into(holder.mhotelImg);

        //for loading hotel name into recycler view
        holder.mhotelTitle.setText(model.getName());

        //for loading hotel discription into recyclerview
       holder.mhotelData.setText(model.getDiscription());


    }


    @NonNull
    @Override
    public hotelViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_data_item, parent, false);
        return new hotelViewholder(view);
    }

    class hotelViewholder extends RecyclerView.ViewHolder {


        ImageView mhotelImg;
        TextView mhotelTitle;
        TextView mhotelData;

        public hotelViewholder(@NonNull View itemView) {
            super(itemView);

            mhotelImg = (ImageView) itemView.findViewById(R.id.ImgView);
            mhotelTitle = (TextView) itemView.findViewById(R.id.titleTxtView);
            mhotelData = (TextView) itemView.findViewById(R.id.dataTxtView);
        }
    }

}