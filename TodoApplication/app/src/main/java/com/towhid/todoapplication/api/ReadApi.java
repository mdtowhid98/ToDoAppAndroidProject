package com.towhid.todoapplication.api;

import com.towhid.todoapplication.model.TodoModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ReadApi {

    @GET("todos")
    Call<List<TodoModel>> getTodo();

}
