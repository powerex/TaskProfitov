package ua.com.azbest.apptest3.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

import ua.com.azbest.apptest3.R;
import ua.com.azbest.apptest3.fragment.FragmentFactory;
import ua.com.azbest.apptest3.model.Content;

public class ContentActivity  extends AppCompatActivity  {

    FloatingActionButton nextContent;
    FloatingActionButton prevContent;

    int position = 0;
    long[] list;
    private String baseHost = "https://demo7877231.mockable.io/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_content);

        position = getIntent().getIntExtra(getString(R.string.item_position), 0);
        list = getIntent().getLongArrayExtra(getString(R.string.items_list));

        String api = baseHost + "api/v1/post/";
        new JsonContentLoader(list[position], this).execute(api);

        nextContent = findViewById(R.id.fabNextContent);
        nextContent.setOnClickListener((view) -> {
            position = (position + 1) % list.length;
            new JsonContentLoader(list[position], this).execute(api);
        });

        prevContent = findViewById(R.id.fabPrevContent);
        prevContent.setOnClickListener((view) -> {
            position = (position - 1 + list.length) % list.length;
            new JsonContentLoader(list[position], this).execute(api);
        });
    }

    private class JsonContentLoader extends AsyncTask<String, String, String> {

        Context context;
        long contentId;

        public JsonContentLoader(long position, Context context) {
            this.contentId = position;
            this.context = context;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0] + contentId);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuilder buffer = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line).append("\n");
                    Log.d("Response: ", "> " + line);

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

            if (result == null) {
                Toast toast = Toast.makeText(getApplicationContext(),"No content",Toast.LENGTH_SHORT);
                toast.setMargin(50,50);
                toast.show();
                return;
            }

            Content content = fromJson(result);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(
                    R.id.frameLayout,
                    FragmentFactory.getFragment(content)
            );
            fragmentTransaction.commit();
        }

        private Content fromJson(String json) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                return objectMapper.readValue(json, Content.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

}
