package com.example.macstudent.rfapp;

/**
 * Created by macstudent on 2018-01-18.
 */
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.bumptech.glide.Glide;
//import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private Realm realm = Realm.getDefaultInstance();
    public static RealmResults<AndroidVersion> data;

    public DataAdapter() {
        data = realm.where(AndroidVersion.class).findAll();
        for(int j=0; j < getItemCount(); j++) {
            Log.d("Data in DataAdapter", data.get(j).getDisplayName());
        }
    }


    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DataAdapter.ViewHolder viewHolder, int i) {

        viewHolder.disp_name.setText("Name: " + data.get(i).getDisplayName());
        viewHolder.disp_signature.setText("Bio: " + data.get(i).getSignature());
        viewHolder.disp_city.setText("City: " + data.get(i).getCity());
        if(data.get(i).getGender() == 0)
        {
            viewHolder.disp_gender.setText("Female");
        }
        else
        {
            viewHolder.disp_gender.setText("Male");
        }
        viewHolder.disp_height.setText("Height: " + data.get(i).getHeight() + " CM");
        viewHolder.disp_loc.setText("Location: " + data.get(i).getLatitude() + ", " + data.get(i).getLongitude());
        //viewHolder.disp_long.setText(data.get(i).getLongitude() + "");

      /*   Picasso.with(MainActivity.main)
                .load("http://eadate.com/images/userInfo/" + data.get(i).getMainImage())
                .into(viewHolder.disp_image);
        */
        //        Glide.with(MainActivity.main).load("http://eadate.com/images/user/" + data.get(i).getMainImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(viewHolder.disp_image);
//        viewHolder.disp_image.setImageURI(Uri.parse("http://eadate.com/images/user/" + data.get(i).getMainImage() + ""));


        Picasso.with(MainActivity.main)
                    .load("http://eadate.com/images/userInfo/" + data.get(i).getMainImage())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(viewHolder.disp_image, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            // Try again online if cache failed
                            Picasso.with(MainActivity.main)
                                    .load("http://eadate.com/images/userInfo/" + data.get(i).getMainImage())
                            .placeholder(R.drawable.user_placeholder)
                            .error(R.drawable.error)
                            .into(viewHolder.disp_image);
                        }
                    });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView disp_name, disp_gender, disp_signature, disp_city, disp_height, disp_loc; //, disp_long;
        private ImageView disp_image;
        private RelativeLayout rel;
        public ViewHolder(View view) {
            super(view);
            view.setOnClickListener(this);
            disp_name = (TextView)view.findViewById(R.id.disp_name);
            disp_gender= (TextView)view.findViewById(R.id.disp_gender);
            disp_signature = (TextView)view.findViewById(R.id.disp_signature);
            disp_city = (TextView)view.findViewById(R.id.disp_city);
            disp_height = (TextView)view.findViewById(R.id.disp_height);
            disp_loc = (TextView)view.findViewById(R.id.disp_loc);
            //disp_long = (TextView)view.findViewById(R.id.disp_long);
            disp_image = (ImageView)view.findViewById(R.id.disp_image);
            rel = (RelativeLayout)view.findViewById(R.id.rel);

        }

        @Override
        public void onClick(View view) {
            Log.d("Position", getLayoutPosition() + "");
            Toast.makeText(view.getContext(), "Position: " + getLayoutPosition(), Toast.LENGTH_SHORT);
            Intent intent = new Intent(view.getContext(), MapsActivity.class);
            intent.putExtra("Position", getLayoutPosition());
            view.getContext().startActivity(intent);
        }
    }

}
