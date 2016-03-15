package com.example.android.iknowthat;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Learned5Things extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learned5_things);
    }

    public void clickedContinue(View view) {
        finish();
    }

    public void closeApp(View view)
    {
        MainActivity.mainActivity.finish();
        System.exit(0);
    }
}
