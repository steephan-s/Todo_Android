package testpress.testpress.com.todoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class NewTodoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText todo_message;

    private Button submit_button;
    public static final String ROOT_URL = "https://todolistindjangorest.herokuapp.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_todo);
        todo_message = (EditText) findViewById(R.id.todo_message);
        submit_button = (Button) findViewById(R.id.submit_button);
        assert submit_button != null;
        submit_button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        insertTodo();
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                InputMethodManager.HIDE_NOT_ALWAYS);
        todo_message.setText("");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void insertTodo(){
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();


        TodosAPI api = adapter.create(TodosAPI.class);


        api.insertTodo(


                todo_message.getText().toString(),



                new Callback<Response>() {
                    @Override
                    public void success(Response result, Response response) {

                        BufferedReader reader = null;


                        String output = "";
                        String success = "Successfully Inserted";

                        try {

                            reader = new BufferedReader(new InputStreamReader(result.getBody().in()));


                            output = reader.readLine();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }


                        Toast.makeText(NewTodoActivity.this, success, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void failure(RetrofitError error) {

                        Toast.makeText(NewTodoActivity.this, error.toString(),Toast.LENGTH_LONG).show();
                    }
                }
        );
    }
}
