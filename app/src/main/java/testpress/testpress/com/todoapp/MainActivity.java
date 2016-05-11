package testpress.testpress.com.todoapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends AppCompatActivity implements ListView.OnItemClickListener {public static final String ROOT_URL = "https://todolistindjangorest.herokuapp.com";
    public static final String KEY_BOOK_ID = "key_book_id";
    public static final String KEY_BOOK_NAME = "key_book_name";
    public static final String KEY_BOOK_PRICE = "key_book_price";
    private ListView listView;
    private SwipeRefreshLayout swipeContainer;
    private List<Todo> toodos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listViewTodo);
        getTodos();
        listView.setOnItemClickListener(this);

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getTodos();
                swipeContainer.setRefreshing(false);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if(fab!=null)
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext()
                        , NewTodoActivity.class);
                startActivity(intent);
            }
        });
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select the Action");
        menu.add(0, v.getId(), 0, "Done Task");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getTitle()=="Done Task"){

        }
        else if(item.getTitle()=="Delete"){

        }else{
            return false;
        }
        return true;
    }


    private void getTodos(){
        final ProgressDialog loading = ProgressDialog.show(this,"Updating Todos","Please wait...",false,false);


        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(ROOT_URL)
                .build();


        TodosAPI api = adapter.create(TodosAPI.class);

        api.getTodos(new Callback<List<Todo>>() {
            @Override
            public void success(List<Todo> list, Response response) {
                loading.dismiss();
                toodos = list;

                showList();
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    private void showList(){

        String[] items = new String[toodos.size()];
        for(int i=0; i<toodos.size(); i++){
            items[i] = toodos.get(i).getTodo_text();
        }
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.todo_layout,items);


        listView.setAdapter(adapter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(this, ShowTodoDetails.class);
        Todo book = toodos.get(position);
        intent.putExtra(KEY_BOOK_ID,book.getId());
        intent.putExtra(KEY_BOOK_NAME,book.getTodo_text());
        intent.putExtra(KEY_BOOK_PRICE,book.getAdd_date());
        startActivity(intent);
    }


}