package com.dima.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.dima.myapplication.util.NetworkUtils;

import org.json.JSONObject;

import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String url = NetworkUtils.buildURL(NetworkUtils.POPULARITY,1).toString();
        JSONObject jsonObject = NetworkUtils.getJSONFromNetwork(NetworkUtils.POPULARITY,1);
        if(jsonObject == null){
            Toast.makeText(this, "ошибка", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, "успешно", Toast.LENGTH_LONG).show();
        }
    }
}