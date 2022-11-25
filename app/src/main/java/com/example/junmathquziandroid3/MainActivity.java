package com.example.junmathquziandroid3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button go_btn;
    EditText nameRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
    }

    private void initialize() {

        go_btn = findViewById(R.id.go_btn);
        nameRequest = findViewById(R.id.name);
        go_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.go_btn){
            String name = nameRequest.getText().toString();

            Intent myIntent = new Intent(this, Play.class);
            myIntent.putExtra("name", name);
            startActivity(myIntent);
        }
    }
}