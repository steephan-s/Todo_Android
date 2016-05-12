package testpress.testpress.com.todoapp;

import java.util.List;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.DELETE;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;


public interface TodosAPI {

    @GET("/api/v1/todo/sort/")
    public void getTodos(Callback<List<Todo>> response);


    @FormUrlEncoded
    @POST("/api/v1/todo/")
    public void insertTodo(@Field("todo_text") String todo_text,Callback<Response> callback);


    @DELETE("/api/v1/todo/{id}")
    void deleteTodo(@Path("id") int id, Callback<Response> callback);


}
