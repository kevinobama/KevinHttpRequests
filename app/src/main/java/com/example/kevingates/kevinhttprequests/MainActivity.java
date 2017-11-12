package com.example.kevingates.kevinhttprequests;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String[] foods = {"Bacon", "Ham", "Tuna", "Candy", "Meatball", "Potato"};
        ListAdapter buckysAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foods);
        ListView buckysListView = (ListView) findViewById(R.id.ListViewOne);
        buckysListView.setAdapter(buckysAdapter);

//        buckysListView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener(){
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        String food = String.valueOf(parent.getItemAtPosition(position));
//                        Toast.makeText(MainActivity.this, food, Toast.LENGTH_LONG).show();
//                    }
//                }
//        );
    }

    public void sendGetRequest(View View) {
        new GetClass(this).execute();
    }

    private class GetClass extends AsyncTask<String, Void, Void> {

        private final Context context;

        public GetClass(Context c){
            this.context = c;
        }

        @Override
        protected Void doInBackground(String... params) {
            try {

                final TextView outputView = (TextView) findViewById(R.id.textview1);
                URL url = new URL("https://robotrader.ai/api/front/v1/market/prices/crypto?base_currency=CNY");

                HttpURLConnection connection = (HttpURLConnection)url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("USER-AGENT", "Mozilla/5.0");
                connection.setRequestProperty("ACCEPT-LANGUAGE", "en-US,en;0.5");

                int responseCode = connection.getResponseCode();

                final StringBuilder output = new StringBuilder("Request URL " + url);
                //output.append(System.getProperty("line.separator") + "Request Parameters " + urlParameters);

                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                StringBuilder responseOutput = new StringBuilder();


                while((line = br.readLine()) != null ) {
                    responseOutput.append(line);
                }
                br.close();

                output.append(System.getProperty("line.separator") + "Response " + System.getProperty("line.separator") + System.getProperty("line.separator") + responseOutput.toString());

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView textview1 = (TextView)findViewById(R.id.textview1);
                        textview1.setText(output);
                    }
                });

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

    }
}
