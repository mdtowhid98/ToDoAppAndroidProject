package com.towhid.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Dasboard extends AppCompatActivity {
    private Button btnView, btnCreate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dasboard);

        // Initialize buttons
        btnView = findViewById(R.id.btnView);
        btnCreate = findViewById(R.id.btnCreate);

        // Set OnClickListener for the "View Todo" button
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to GetActivity
                Intent intent = new Intent(Dasboard.this, ReadActivity.class);
                startActivity(intent);
            }
        });

        // Set OnClickListener for the "Create Todo" button
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate to PostActivity
                Intent intent = new Intent(Dasboard.this, WriteActivity.class);
                startActivity(intent);
            }
        });
    }
}
