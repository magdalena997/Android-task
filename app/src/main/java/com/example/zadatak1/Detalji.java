package com.example.zadatak1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class Detalji extends AppCompatActivity {
    Button button;
    Button buttonDelete;
    int idDetalji;
    int pos;
    int userDetalji;
    TextView textIme;
    TextView textEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalji);

        Bundle data_from_list= getIntent().getExtras();
        String naslovDetalji= data_from_list.getString("naslov");
        String teloDetalji= data_from_list.getString("telo");
        userDetalji= data_from_list.getInt("userId");
        idDetalji= data_from_list.getInt("id");
        pos= data_from_list.getInt("pos");

        TextView textNaslov = (TextView) findViewById(R.id.naslovDetalji);
        TextView textTelo = (TextView) findViewById(R.id.bodyDetalji);
        //TextView textUser = (TextView) findViewById(R.id.userIdDetalji);
        //TextView textId = (TextView) findViewById(R.id.idDetalji);
        textIme = (TextView) findViewById(R.id.imeDetalji);
        textEmail = (TextView) findViewById(R.id.emailDetalji);

        Autor autor = new Autor();
        autor.execute();

        textNaslov.setText("Title " + "\n" + naslovDetalji + "\n");
        textTelo.setText("Full-body text " + "\n" +  teloDetalji + "\n");
        //textUser.setText("UserId " + "\n" + userDetalji + "\n");
        //textId.setText("Id " + "\n" + idDetalji + "\n");



        button = (Button) findViewById(R.id.pocetna);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
               finish();

            }
        });

        buttonDelete = (Button) findViewById(R.id.obrisi);

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Do stuff to update UI here!

                        //listView.setAdapter(null);
                        System.out.println("Uspesno obrisano");

                        Toast.makeText(getApplicationContext(), "Post successfully deleted!", Toast.LENGTH_LONG).show();
                    }
                });

                PodaciLista pod = (PodaciLista) MainActivity.listView.getAdapter();

                Podatak t = (Podatak) pod.getItem(pos);
                System.out.println(pos + " " + idDetalji + " " + t.getNaslov() );
                pod.remove(t);
                pod.notifyDataSetChanged();

                finish();

            }
        });

    }

    class Autor extends AsyncTask<Void, Void, Void> {
        String data = "";
        String dataParsed = "";
        String singleParsed = "";
        String ime = "";
        String email = "";


        @Override
        protected Void doInBackground(Void... voids) {
            URL url = null;
            try {
                url = new URL("https://jsonplaceholder.typicode.com/users");

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
                JSONArray JA = new JSONArray(data);
                for (int i = 0; i < JA.length(); i++) {
                    JSONObject JO = (JSONObject) JA.get(i);




                    int idUser = (int) JO.get("id");

                    if(userDetalji == idUser) {
                        ime = (String) JO.get("name");
                        email = (String) JO.get("email");
                        break;
                    }



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

            textIme.setText("Author name" + "\n" + ime + "\n");
            textEmail.setText("Author email" + "\n" + email + "\n");

        }
    }

}