package ua.com.azbest.apptest3.activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import ua.com.azbest.apptest3.R;
import ua.com.azbest.apptest3.model.ListItem;
import ua.com.azbest.apptest3.model.ListLoader;

public class MainActivity extends AppCompatActivity implements RecyclerListBehavior {

    private List<ListItem> itemList = new LinkedList<>();
    FloatingActionButton refreshButton;
    RecyclerView recyclerViewList;
    RecyclerListAdapter recyclerListAdapter;
    String jsonList;
    private String baseHost = "https://demo7877231.mockable.io/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String textUrl = baseHost + "api/v1/hot";
        new JsonListLoader(this, this).execute(textUrl);
        setContentView(R.layout.activity_main);
        refreshButton = findViewById(R.id.fab_refressh);
        refreshButton.setOnClickListener((view) -> new JsonListLoader(this, this).execute(textUrl));
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, ContentActivity.class);
        intent.putExtra(getString(R.string.item_position), position);
        intent.putExtra(getString(R.string.items_list), itemList.stream().mapToLong(ListItem::getId).toArray());
        startActivity(intent);
    }

    private class JsonListLoader extends AsyncTask<String, String, String> {

        Context ctx;
        RecyclerListBehavior rlb;

        public JsonListLoader(Context context, RecyclerListBehavior rlb) {
            this.ctx = context;
            this.rlb = rlb;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            jsonList = result;
            itemList = ListLoader.fromJson(jsonList);
            if (itemList.size() == 0) {
                Toast toast = Toast.makeText(getApplicationContext(),"No content",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
            } else {
                recyclerViewList = findViewById(R.id.recycler_list);
                recyclerListAdapter = new RecyclerListAdapter(ctx, itemList, rlb);
                recyclerViewList.setAdapter(recyclerListAdapter);
                recyclerViewList.setLayoutManager(new LinearLayoutManager(ctx));
            }
        }
    }

}
