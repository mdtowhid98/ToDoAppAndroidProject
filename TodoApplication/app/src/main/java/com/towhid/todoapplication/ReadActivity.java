package com.towhid.todoapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.towhid.todoapplication.adapter.TodoAdapter;
import com.towhid.todoapplication.api.ReadApi;
import com.towhid.todoapplication.apiClient.ApiClient;
import com.towhid.todoapplication.model.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReadActivity extends AppCompatActivity {

    private RecyclerView todoList;
    private TodoAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_read);

        // Set up window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Set up RecyclerView
        todoList = findViewById(R.id.noticeList);
        todoList.setLayoutManager(new LinearLayoutManager(this));

        // API Call to fetch Todo List
        ReadApi todoApi = ApiClient.getRetrofit().create(ReadApi.class);
        Call<List<TodoModel>> call = todoApi.getTodo();
        call.enqueue(new Callback<List<TodoModel>>() {
            @Override
            public void onResponse(Call<List<TodoModel>> call, Response<List<TodoModel>> response) {
                if (response.isSuccessful()) {
                    List<TodoModel> noticeList = response.body();
                    // Set up RecyclerView with the adapter
                    notificationAdapter = new TodoAdapter(noticeList, getApplicationContext());
                    todoList.setAdapter(notificationAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<TodoModel>> call, Throwable t) {
                // Handle failure
            }
        });

        // Floating Action Button for navigation to WriteActivity
        FloatingActionButton fabPost = findViewById(R.id.fabPost);
        fabPost.setOnClickListener(view -> {
            Intent intent = new Intent(ReadActivity.this, WriteActivity.class);
            startActivity(intent);
        });
    }
}

