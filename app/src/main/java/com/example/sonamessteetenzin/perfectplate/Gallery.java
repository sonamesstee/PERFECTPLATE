package com.example.sonamessteetenzin.perfectplate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.sonamessteetenzin.perfectplate.Config.serv_url;

public class Gallery extends AppCompatActivity {

    Toolbar toolbar;
    ArrayList<Album> arrayList =new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, Config.serv_url, (String) null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {

                        int count = 0;
                        while(count<response.length())
                        {
                            try {
                                JSONObject jsonObject = response.getJSONObject(count);
                                arrayList.add(new Album(jsonObject.getString("id"), jsonObject.getString("title"), jsonObject.getString("path")));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        adapter =new RecyclerAdapter(arrayList, Gallery.this);
                        recyclerView.setAdapter(adapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        MySingleton.getmInstance(Gallery.this).addtoRequestqueue(jsonArrayRequest);
    }


    private void setSupportActionBar(Toolbar toolbar) {
    }
}
