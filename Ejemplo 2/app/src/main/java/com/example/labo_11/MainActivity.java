package com.example.labo_11;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

// MainActivity to select examples
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_case1).setOnClickListener(v -> startActivity(new Intent(this, Case1Activity.class)));
        findViewById(R.id.btn_case2).setOnClickListener(v -> startActivity(new Intent(this, Case2Activity.class)));
        findViewById(R.id.btn_case3).setOnClickListener(v -> startActivity(new Intent(this, Case3Activity.class)));
    }
}

