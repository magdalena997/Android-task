package com.example.zadatak1;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.ListActivity;
import android.graphics.Typeface;
import android.os.Handler;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    Button button;
    public static ListView listView;

    public static ArrayList<Podatak> podatak = new ArrayList<Podatak>();

    private ScheduledExecutorService scheduleTaskExecutor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setting header
       /* TextView textView = new TextView(this);
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("List of posts");
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP,30);
        textView.setTextColor(Color.parseColor("#993300"));
        textView.setBackgroundColor(Color.parseColor("#FFFFCC"));
        textView.setGravity(Gravity.CENTER); */
        //listView.addHeaderView(textView);

         listView = (ListView) findViewById(android.R.id.list);


        new Podaci1().execute();

        scheduleTaskExecutor = Executors.newScheduledThreadPool(300);

        //Schedule a task to run every 5 seconds (or however long you want)
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Do stuff here!

                new Podaci1().execute();

               runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Do stuff to update UI here!

                        //listView.setAdapter(null);
                        System.out.println("Proslo je 5 minuta");
                        Toast.makeText(getApplicationContext(),  "Its been 5 minutes!" , Toast.LENGTH_LONG).show();

                    }
                });

            }
        }, 0, 300, TimeUnit.SECONDS);

     /*  final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                //listView.setAdapter(null); //mozda ne treba proveri
                new Podaci1().execute();
                handler.postDelayed(this, 50000); //now is every 2 minutes
            }
        }, 50000); //Every 120000 ms (2 minutes) */

        Button buttonFetch = (Button) findViewById(R.id.refreshData);

        buttonFetch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                new Podaci1().execute();
                System.out.println("Osvezili ste stranicu");
                Toast.makeText(getApplicationContext(),  "Fetch fresh data!" , Toast.LENGTH_LONG).show();

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getApplicationContext(), "You Selected ", Toast.LENGTH_LONG).show();
            }
        });



    }

    class Podaci1 extends AsyncTask<Void, Void, Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";


        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("https://jsonplaceholder.typicode.com/posts");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            HttpURLConnection httpURLConnection = null;
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try (InputStream inputStream = httpURLConnection.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));) {


                String line = "";
                while (line != null) {
                    line = bufferedReader.readLine();
                    data = data + line;
                }
                if(podatak.size() > 0){
                    podatak.clear();
                }
                JSONArray JA = new JSONArray(data);
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);


                    singleParsed = "userId:" + JO.get("userId") + "\n" +
                            "id:" + JO.get("id") + "\n" +
                            "title:" + JO.get("title") + "\n" +
                            "body:" + JO.get("body") + "\n";


                    Podatak p = new Podatak((int) JO.get("id"), (int) JO.get("userId"), (String) JO.get("title"), (String) JO.get("body"));
                    podatak.add(p);

                    dataParsed = dataParsed + singleParsed + "\n";

                    // inputStream.close();

                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            System.out.println("Duzina niza " + podatak.size());
            PodaciLista listaPodataka = new PodaciLista(MainActivity.this, podatak);
            listView.setAdapter(listaPodataka);

        }
    }



}