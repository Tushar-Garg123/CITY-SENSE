package com.project.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerview;
    EditText mSearchbar;
    cityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mSearchbar = (EditText) findViewById(R.id.editSearchbox);


        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("citynames"), Model.class)
                        .build();

        adapter = new cityAdapter(options);
        mRecyclerview.setAdapter(adapter);




        mSearchbar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt = mSearchbar.getText().toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("citynames").orderByChild("cityname").startAt(searchtxt).endAt(searchtxt + "\uf8ff"), Model.class)
                                .build();

                adapter = new cityAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String searchtxt = mSearchbar.getText().toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("citynames").orderByChild("cityname").startAt(searchtxt).endAt(searchtxt + "\uf8ff"), Model.class)
                                .build();

                adapter = new cityAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                String charSequence = editable.toString();
                FirebaseRecyclerOptions<Model> options =
                        new FirebaseRecyclerOptions.Builder<Model>()
                                .setQuery(FirebaseDatabase.getInstance().getReference().child("citynames").orderByChild("cityname").startAt(charSequence).endAt(charSequence + "\uf8ff"), Model.class)
                                .build();

                adapter = new cityAdapter(options);
                adapter.startListening();
                mRecyclerview.setAdapter(adapter);

            }
        });

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

class cityAdapter extends FirebaseRecyclerAdapter<Model, cityAdapter.cityViewholder> {

    public cityAdapter(@NonNull FirebaseRecyclerOptions<Model> options) {

        super(options);

    }

    @Override
    protected void onBindViewHolder(@NonNull cityViewholder holder, int position, @NonNull Model model) {

        String cityname=model.getCityname().toString();

        holder.mTextview.setText(cityname);
        Picasso.get().load(model.getImage()).into(holder.mimageView);

        holder.mTextview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent=new Intent(context,CitydetailsActivity.class);
                intent.putExtra("cityname",cityname);
                context.startActivity(intent);

            }
        });
        holder.mimageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context=view.getContext();
                Intent intent=new Intent(context,CitydetailsActivity.class);
                intent.putExtra("cityname",cityname);
                context.startActivity(intent);

            }
        });

    }

    @NonNull
    @Override
    public cityViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_city_name, parent, false);
        return new cityViewholder(view);
    }

    class cityViewholder extends RecyclerView.ViewHolder {

        TextView mTextview;
        ImageView mimageView;

        public cityViewholder(@NonNull View itemView) {
            super(itemView);

            mTextview = (TextView) itemView.findViewById(R.id.txtCityname);
            mimageView = (ImageView) itemView.findViewById(R.id.city_image);
        }
    }

}