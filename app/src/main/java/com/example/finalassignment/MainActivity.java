package com.example.finalassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> theIds = new ArrayList<>();
    private ArrayList<String> theImagesURL = new ArrayList<>();
    private ArrayList<String> theThumbnailURL = new ArrayList<>();
    private ArrayList<String> theTitles = new ArrayList<>();
    private ArrayList<String> theAlbumId = new ArrayList<>();

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initImageLoad();

        recyclerView =findViewById(R.id.recyclerView);
        getColors();
    }

    private void getColors() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);

        Call<List<Colors>> call = api.getColors();


        call.enqueue(new Callback<List<Colors>>() {
            @Override
            public void onResponse(Call<List<Colors>> call, Response<List<Colors>> response) {
                List<Colors> herolist = response.body();
                //Creating a string array for the listview
                String[] heroes = new String[herolist.size()];

                for(int i =0; i < herolist.size(); i++) {
                    heroes[i] = herolist.get(i).getAlbumId();
                    heroes[i] = herolist.get(i).getId();
                    heroes[i] = herolist.get(i).getTitle();
                    heroes[i] = herolist.get(i).getURL();
                    heroes[i] = herolist.get(i).getThumbnailURL();
                }

                //displaying the string array into RecycleView
                recyclerView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout., heroes));
            }

            @Override
            public void onFailure(Call<List<Colors>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    private void initImageLoad() {



        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(theIds,theImagesURL,
                theThumbnailURL,theTitles,theAlbumId,this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}