package com.example.myapplication.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.adapter.SliderAdapter;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.IndicatorView.draw.controller.DrawController;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TextToSpeech.OnInitListener {
    SliderView sliderView;

    private TextToSpeech repeatTTS;

  //  Toolbar
    Window window;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = initToolbar();
        initDrawer(toolbar);
        initNavigationView();
        window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(MainActivity.this,R.color.colorPrimary));

        sliderView = findViewById(R.id.imageSlider);

        final SliderAdapter adapter = new SliderAdapter(this);
        adapter.setCount(5);

        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.SLIDE); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.CUBEINROTATIONTRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setOnIndicatorClickListener(new DrawController.ClickListener() {
            @Override
            public void onIndicatorClicked(int position) {
                sliderView.setCurrentPagePosition(position);
            }
        });

        repeatTTS = new TextToSpeech(MainActivity.this, this);
        LinearLayout weather = (LinearLayout) findViewById(R.id.weather);
        weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // repeatTTS.speak("Check the weather status!",
                     //   TextToSpeech.QUEUE_FLUSH, null);

                Intent i = new Intent(MainActivity.this, WeatherActivity.class);
                startActivity(i);

            }
        });
        LinearLayout topplaces = (LinearLayout) findViewById(R.id.topplaces);
        topplaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, TopPlacesActivity.class);
                startActivity(i);

            }
        });

        LinearLayout explorer = (LinearLayout) findViewById(R.id.explorer);
        explorer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int lan = 27;

                String uri = "http://maps.google.com/maps?saddr=" + "27" + "," + "83" + "&daddr=" + "27" + "," + "83";
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                startActivity(intent);

            }
        });

        LinearLayout hotel = (LinearLayout) findViewById(R.id.hotels);
        hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, HotelActivity.class);
                startActivity(i);

            }
        });

        LinearLayout currency = (LinearLayout) findViewById(R.id.currency);
        currency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, CurrencyConverterActivity.class);
                startActivity(i);

            }
        });

        LinearLayout restaurants = (LinearLayout) findViewById(R.id.restaurants);
        restaurants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, RestaurantsActivity.class);
                startActivity(i);

            }
        });

        LinearLayout flightbook = (LinearLayout) findViewById(R.id.flightbook);
        flightbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, PlaneTicketActivity.class);
                startActivity(i);

            }
        });

        LinearLayout sos = (LinearLayout) findViewById(R.id.sos);
        sos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, EmergencyActivity.class);
                startActivity(i);

            }
        });


        LinearLayout events = (LinearLayout) findViewById(R.id.events);
        events.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, EventActivity.class);
                startActivity(i);

            }
        });



    }
    private void initNavigationView(){
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.nav_home){
            startActivity(new Intent(this, MainActivity.class));
        }
        else if (id == R.id.nav_share) {
            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody = "Best Free Vpn app download now. https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName();
            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Share App");
            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, "Share via"));
        }else if (id == R.id.rate_us) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getApplicationContext().getPackageName())));
        }
        else if (id == R.id.about_me) {
          //  aboutMyApp();

        }


        else if (id == R.id.privacypolicy) {
           // startActivity(new Intent(MainActivity.this, TOSActivity.class));

        }

        else if (id == R.id.moreapp) {

            Uri uri = Uri.parse("https://play.google.com/store/apps/dev?id=8774282826145816300");
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/dev?id=8774282826145816300")));
            }


        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    private void initDrawer(Toolbar toolbar) {
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }
        });
        toggle.syncState();
    }

    private Toolbar initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        return toolbar;
    }

    @Override
    public void onInit(int arg0) {
        // TODO Auto-generated method stub
    }

}
