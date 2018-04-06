package com.example.macstudent.rfapp;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmObject;
import io.realm.RealmResults;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<AndroidVersion> data;
    private DataAdapter adapter;
    public static MainActivity main;
    dbHandler db = new dbHandler(this);
    public Realm realm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        setContentView(R.layout.activity_main);
        realm = Realm.getDefaultInstance();
        initViews();
        main = this;
        //realm = Realm.getInstance(config); // opens "myrealm.realm"
        //realm.setAutoRefresh(true);
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        if(realm.isEmpty()) {
            loadJSON();
        }
        else {
            realm.where(AndroidVersion.class).findAll();
            adapter = new DataAdapter();
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadJSON(){

        /*File httpCacheDirectory = new File(this.getCacheDir(), "httpCache");
        Cache cache = new Cache(httpCacheDirectory,10*1024*1024);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(chain -> {
                    try {
                        return chain.proceed(chain.request());
                    }
                    catch (Exception e){
                        Request offlineRequest = chain.request().newBuilder()
                                .header("Cache-Control", "public, only-if-cached," +
                                        "max-stale=" + 60 * 60 * 1024)
                                .build();
                        return chain.proceed(offlineRequest);
                    }
                })
                .build();*/

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://www.eadate.com")
                //.client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {

                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getAndroid()));

                realm.beginTransaction();
                for(AndroidVersion a : data)
                {
                    realm.copyToRealmOrUpdate(a);
                    Log.d("Inserted from JSON", a.getDisplayName());
                }
                realm.commitTransaction();

                Log.d("Realm Path", realm.getPath());

//                RealmResults<AndroidVersion> andrd = realm.where(AndroidVersion.class).findAll();
//                for(AndroidVersion a : andrd)
//                {
//                    a.getDisplayName();
//                    Log.d("Retrieved from Realm", a.getDisplayName());
//                }

                adapter = new DataAdapter();
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error",t.getMessage());
            }
        });
    }

}
