package testpress.testpress.com.todoapp;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;


public interface TodosAPI {

    @GET("/api/v1/todo/")
    public void getTodos(Callback<List<Todo>> response);


    @FormUrlEncoded
    @POST("/api/v1/todo/")
    public void insertTodo(@Field("todo_text") String todo_text,Callback<Response> callback);


}
