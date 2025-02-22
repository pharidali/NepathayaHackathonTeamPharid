package com.example.myapplication.activity;

import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.myapplication.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EmergencyActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech repeatTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimary));

        setContentView(R.layout.activity_emergency);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        }

        LinearLayout police = (LinearLayout) findViewById(R.id.police);
        police.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(EmergencyActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to cancel this action!")
                        .setConfirmText("Yes,It's a Emergency!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                repeatTTS.speak("Your case has issued! Police would help you !",
                                        TextToSpeech.QUEUE_FLUSH, null);

                                sDialog
                                        .setTitleText("Action Completed!")
                                        .setContentText("Your case has issued!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })

                        .show();

               // Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
             //   startActivity(i);

            }
        });


        repeatTTS = new TextToSpeech(EmergencyActivity.this, this);

        LinearLayout ambulance = (LinearLayout) findViewById(R.id.ambulance);
        ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(EmergencyActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to cancel this action!")
                        .setConfirmText("Yes,It's a Emergency!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                repeatTTS.speak("Your case has issued! Ambulance on the way !",
                                        TextToSpeech.QUEUE_FLUSH, null);

                                sDialog
                                        .setTitleText("Action Completed!")
                                        .setContentText("Your case has issued!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                // Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
                //   startActivity(i);

            }
        });


        LinearLayout firefighter = (LinearLayout) findViewById(R.id.firefighter);
        firefighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(EmergencyActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to cancel this action!")
                        .setConfirmText("Yes,It's a Emergency!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                repeatTTS.speak("Your case has issued! FireFighter gear up !",
                                        TextToSpeech.QUEUE_FLUSH, null);

                                sDialog
                                        .setTitleText("Action Completed!")
                                        .setContentText("Your case has issued!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                // Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
                //   startActivity(i);

            }
        });


        LinearLayout firstaids = (LinearLayout) findViewById(R.id.firstaids);
        firstaids.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(EmergencyActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to cancel this action!")
                        .setConfirmText("Yes,It's a Emergency!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                repeatTTS.speak("Your case has issued! People would bring the first aid within some time !",
                                        TextToSpeech.QUEUE_FLUSH, null);

                                sDialog
                                        .setTitleText("Action Completed!")
                                        .setContentText("Your case has issued!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                // Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
                //   startActivity(i);

            }
        });


        LinearLayout rescue = (LinearLayout) findViewById(R.id.rescue);
        rescue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new SweetAlertDialog(EmergencyActivity.this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("Are you sure?")
                        .setContentText("Won't be able to cancel this action!")
                        .setConfirmText("Yes,It's a Emergency!")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {

                                repeatTTS.speak("Your case has issued! Rescue Team on the way !",
                                        TextToSpeech.QUEUE_FLUSH, null);

                                sDialog
                                        .setTitleText("Action Completed!")
                                        .setContentText("Your case has issued!")
                                        .setConfirmText("OK")
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                            }
                        })
                        .show();

                // Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
                //   startActivity(i);

            }
        });


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onInit(int arg0) {
        // TODO Auto-generated method stub
    }


}

