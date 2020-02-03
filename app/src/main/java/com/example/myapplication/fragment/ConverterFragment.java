package com.example.myapplication.fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.ApiController;
import com.example.myapplication.CurrenciesActivity;
import com.example.myapplication.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.StringTokenizer;

import com.example.myapplication.model.CurrencyModel;
import com.example.myapplication.model.DataValuesModel;

public class ConverterFragment extends Fragment {

    RelativeLayout from_layout,to_layout;
    ImageView from_image,to_image;
    EditText from_edittext;
    TextView result_textview;
    TextView from_country_name,to_country_name;
    View v;
    Button button_convert;
    Boolean flag_check_first_item=false;
    Boolean flag_check_second_item=false;
    public static String url_Result = null;
    public JSONObject jsonObj_result=null;
    String final_Result = null;
    String temp = null;
    String from_amount = null;
    String first_country_short;
    String second_country_short;
    private ProgressDialog pDialog;
    public static ArrayList<CurrencyModel> currences_names;
    public static String sDefSystemLanguage;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v  = inflater.inflate(R.layout.fragment_currency_converter,null);


        from_layout = (RelativeLayout)v.findViewById(R.id.from_field);
        to_layout  = (RelativeLayout)v.findViewById(R.id.to_field);
        from_image = (ImageView)v.findViewById(R.id.first_country_image);
        to_image = (ImageView)v.findViewById(R.id.second_country_flag);
        from_edittext = (EditText)v.findViewById(R.id.first_country_edittext);
        from_country_name = (TextView)v.findViewById(R.id.first_country_name);
        to_country_name = (TextView)v.findViewById(R.id.second_country_name);
        result_textview = (TextView)v.findViewById(R.id.text_result);
        button_convert = (Button)v.findViewById(R.id.button_convert);
        sDefSystemLanguage = Locale.getDefault().getLanguage();
        currences_names= new ArrayList<>();


        pDialog = new ProgressDialog(getActivity());

        first_country_short = "USD";
        second_country_short= "GBP";

        from_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                flag_check_first_item=true;
                flag_check_second_item=false;
                Intent intent = new Intent(getActivity(), CurrenciesActivity.class);
                startActivity(intent);
            }
        });


        to_layout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                flag_check_second_item=true;
                flag_check_first_item=false;
                Intent intent = new Intent(getActivity(), CurrenciesActivity.class);
                startActivity(intent);
            }
        });



        button_convert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                from_amount=from_edittext.getText().toString();
                String get_cc_link= getResources().getString(R.string.Free_CC_link);
                url_Result =get_cc_link+first_country_short+"_"+second_country_short+"&apiKey=d50fd58ebbaf78032dfd";
                if(isNetworkAvailable())
                {
                    new GetExchangeRates1().execute();
                }
                else
                {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Internet Connection")
                            .setMessage("Please check your internet connection")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();

                }


            }
        });
        return v;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class GetExchangeRates1 extends AsyncTask<Void, Void, Void> {

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
            String json_Result = sh.makeServiceCall(url_Result, ApiController.GET);
            try
            {
                jsonObj_result = new JSONObject(json_Result);

                final_Result  = jsonObj_result.getJSONObject("results").toString();

            }catch (JSONException e)
            {

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

              add_country_Result();
                  pDialog.dismiss();

        }
    }

    public void add_country_Result()  {

        final_Result=final_Result.replace("{","");
        final_Result=final_Result.replace("}","");
        final_Result=final_Result.replace("\"","");

        StringTokenizer stok= new StringTokenizer(final_Result,",");

        while(stok.hasMoreElements())
        {
            temp= stok.nextElement().toString();

            if(temp.indexOf("val") != -1){
                String split[]= temp.split(":");
                NumberFormat f = NumberFormat.getInstance(Locale.US);
                double temp_Amount2 = Double.parseDouble(split[2]);
               DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########",new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));

                f.setGroupingUsed(false);
                String refinedNumber = f.format(result);
                result_textview.setText(refinedNumber);
            }
            temp= stok.nextElement().toString();


            if(temp.indexOf("val") != -1){
                String split[]= temp.split(":");
                NumberFormat f = NumberFormat.getInstance(Locale.US);
                double temp_Amount2 = Double.parseDouble(split[1]);
              DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
               DecimalFormat df1 = new DecimalFormat("#.#########",new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));
                f.setGroupingUsed(false);
                String refinedNumber = f.format(result);
                result_textview.setText(refinedNumber);
            }

            temp= stok.nextElement().toString();

            if(temp.indexOf("val") != -1){

                String split[]= temp.split(":");


                NumberFormat f = NumberFormat.getInstance(Locale.US);

                double temp_Amount2 = Double.parseDouble(split[1]);
              DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));

                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########",new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));

                double result = temp_Amount1 * temp_Amount2;
               DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));
                f.setGroupingUsed(false);
                String refinedNumber = f.format(result);
                result_textview.setText(refinedNumber);
            }
            temp= stok.nextElement().toString();

            if(temp.indexOf("val") != -1){
                String split[]= temp.split(":");
                NumberFormat f = NumberFormat.getInstance(Locale.US);
                double temp_Amount2 = Double.parseDouble(split[1]);
               DecimalFormat df = new DecimalFormat("#.#########");
                temp_Amount2 = Double.valueOf(f.format(temp_Amount2));
                double temp_Amount1 = Double.parseDouble(from_amount);
                DecimalFormat df1 = new DecimalFormat("#.#########",new DecimalFormatSymbols(Locale.US));
                temp_Amount1 = Double.valueOf(df1.format(temp_Amount1));
                double result = temp_Amount1 * temp_Amount2;
                DecimalFormat df2 = new DecimalFormat("#.#########");
                result = Double.valueOf(df1.format(result));
                f.setGroupingUsed(false);
                String refinedNumber = f.format(result);
                result_textview.setText(refinedNumber);
            }
        }
    }


    @Override
    public void onResume()
    {
        super.onResume();
        if(flag_check_first_item)
        {
            if (DataValuesModel.global_image_id!=0 && !DataValuesModel.global_country_name.isEmpty())
            {
                from_image.setImageResource(DataValuesModel.global_image_id);
                from_country_name.setText(DataValuesModel.global_country_name);
                first_country_short= DataValuesModel.country_id;
                Log.d("Key",first_country_short);

            }
            else
            {

            }
        }

        if(flag_check_second_item)
        {

            if (DataValuesModel.global_image_id!=0 && !DataValuesModel.global_country_name.isEmpty())
            {

                to_image.setImageResource(DataValuesModel.global_image_id);
                to_country_name.setText(DataValuesModel.global_country_name);
                second_country_short= DataValuesModel.country_id;

            }
        }

    }
}
