package testpress.testpress.com.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ShowTodoDetails extends AppCompatActivity implements View.OnClickListener {


    private TextView textViewBookId;
    private TextView textViewBookName;
    private TextView textViewBookPrice;
    private Button delete_button;
    public static final String ROOT_URL = "https://todolistindjangorest.herokuapp.com/";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_todo_details);
        delete_button = (Button) findViewById(R.id.delete_button);
        assert delete_button != null;
        delete_button.setOnClickListener(this);

        textViewBookId = (TextView) findViewById(R.id.textViewBookId);
        textViewBookName = (TextView) findViewById(R.id.textViewBookName);
        textViewBookPrice = (TextView) findViewById(R.id.textViewBookPrice);



        Intent intent = getIntent();


        textViewBookId.setText(String.valueOf(intent.getIntExtra(MainActivity.KEY_BOOK_ID, 0)));
        textViewBookName.setText(intent.getStringExtra(MainActivity.KEY_BOOK_NAME));
        textViewBookPrice.setText(intent.getStringExtra(MainActivity.KEY_BOOK_PRICE));

    }

    @Override
    public void onClick(View v) {
        deleteTodo(Integer.parseInt(textViewBookId.getText().toString()));
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void deleteTodo(int id) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();


        TodosAPI api = adapter.create(TodosAPI.class);


        api.deleteTodo(id,new Callback<Response>() {
            @Override
            public void success(Response result, Response response) {

                BufferedReader reader = null;


                String output = "";
                String success = "Todo Task Deleted Successfully";

                try {

                    reader = new BufferedReader(new InputStreamReader(result.getBody().in()));


                    output = reader.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                Toast.makeText(ShowTodoDetails.this, success, Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {

                Toast.makeText(ShowTodoDetails.this, error.toString(),Toast.LENGTH_LONG).show();
            }
        });

    }


}