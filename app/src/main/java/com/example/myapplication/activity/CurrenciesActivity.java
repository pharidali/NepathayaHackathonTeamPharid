package com.example.myapplication.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.StringTokenizer;

import com.example.myapplication.base.ApiController;
import com.example.myapplication.adapter.CurrenciesAdapter;
import com.example.myapplication.R;
import com.example.myapplication.model.NameModel;


public class CurrenciesActivity extends Activity
{

    ListView                            listview        ;
    CurrenciesAdapter adaptr_listview ;
    String                              temp = null     ;


    public static String url_currency_id_name = "https://free.currconv.com/api/v7/currencies?apiKey=d50fd58ebbaf78032dfd";
    public JSONObject jsonObj_name_id = null;
    public static ArrayList<NameModel> currences_names  ;
    String s_rtes , s_names,s_ids_names;
    private ProgressDialog pDialog               ;

    String split[];
    StringTokenizer stok;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversion_listview);

        listview    =   (ListView)findViewById(R.id.listView);
        pDialog = new ProgressDialog(this);
        currences_names= new ArrayList<>();



        new GetExchangeRates().execute();

    }

    private class GetExchangeRates extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {

            ApiController sh = new ApiController();

            String json_curncy_id_name = sh.makeServiceCall(url_currency_id_name, ApiController.GET);

            try
            {

                jsonObj_name_id = new JSONObject(json_curncy_id_name);
                s_ids_names = jsonObj_name_id.getJSONObject("results").toString();

            }catch (JSONException e)
            {
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            add_country_ids_names();


        }

    }

    public void add_country_ids_names()  {

        s_ids_names=s_ids_names.replace("{","");
        s_ids_names=s_ids_names.replace("}","");
        s_ids_names=s_ids_names.replace("\"","");

        stok= new StringTokenizer(s_ids_names,",");


        while(stok.hasMoreElements())
        {
            temp= stok.nextElement().toString();

            if(temp.indexOf("currencySymbol") != -1){
                temp= stok.nextElement().toString();
            }

            String split[]= temp.split(":");

            temp= stok.nextElement().toString();

            if(temp.indexOf("currencySymbol") != -1){
                temp= stok.nextElement().toString();


            }

            String split2[]= temp.split(":");

            temp = null;

            currences_names.add(new NameModel(split[2], split2[1]));


        }

        Collections.sort(currences_names, new Comparator<NameModel>() {
            @Override
            public int compare(NameModel n1, NameModel n2) {

                return n1.short_name.compareTo(n2.short_name);
            }
        });

        adaptr_listview = new CurrenciesAdapter(CurrenciesActivity.this, currences_names);
        listview.setAdapter(adaptr_listview);
        pDialog.dismiss();
    }
}
