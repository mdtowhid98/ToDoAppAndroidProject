package com.towhid.todoapplication.api;

import com.towhid.todoapplication.model.TodoModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WriteApi {

    @POST("todos")
    Call<Void> createTodo(@Body TodoModel todo);
}
