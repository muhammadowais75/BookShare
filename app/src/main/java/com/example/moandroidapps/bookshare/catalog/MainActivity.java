package com.example.moandroidapps.bookshare.catalog;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String LOG_CAT = "Main Activity";
    private CoordinatorLayout coordinatorLayout;

    private static String webURL = "https://www.nu.edu.pk/";
    private static String email1 = "rana.owais.aslam@gmail.com";
    private static String email2 = "L124129@lhr.nu.edu.pk";

    private List<Product> products = DataProvider.productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Choosing the Activity's layout*/
        setContentView(R.layout.activity_main);

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_main_coordinator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                //Implicit Intent allows to share data with different apps on same device
                //Send an Email
                String[] addresses = {email1, email2};
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                //To handle this intent specifically by an Email Application
                emailIntent.setData(Uri.parse("mailto:"));
                emailIntent.putExtra(Intent.EXTRA_EMAIL, addresses);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Information Request");
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Please send some Information");
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                }//end if
            }//end onClick
        });


        /*Getting data from the resource file and passing it to listView*/
        /*Four Steps:
        * 1. Get the Data
        * 2. Create the Adapter
        * 3. Get the reference to the listView
        * 4. Connect the adapter to the listView*/


        //1. Get the Data:
        //Retrieving data from the resource file
        String[] items = getResources().getStringArray(R.array.clothing);

        //2. Create the Adapter:
        //Binding data to the list
        /*ArrayAdapter<String> adapter =
                new ArrayAdapter<>(this,
                        android.R.layout.simple_list_item_1,
                        android.R.id.text1, items)*/;//new ArrayAdapter(Context,
        // Resource identifier for a layout file(Not created by us. Already included with SDK) It contains single text component,
        // ID of textView that will display my string for a single row,
        // objects, which we want to display. It can either be a simple array of strings or a complex list of strings)

        //For Customized List
        ProductListAdapter adapter = new ProductListAdapter
                (this, R.layout.list_item, products);

        //Now the adapter knows, what data it is using, it knows which layout it is using for each
        //row in the list and what id of the textView to display strings

        //3. Get reference to the listView:
        //Getting reference of a listView component. Which is a part of the layout.
        ListView lv = (ListView) findViewById(R.id.listView);

        //4. Connect the adapter to the listView:
        //Binding everything together
        lv.setAdapter(adapter);

        /*ImageView iv = (ImageView) findViewById(R.id.dummy_image);
        String imageName = "another_image";

        try {
            InputStream stream = getAssets().open(imageName + ".png");
            Drawable d = Drawable.createFromStream(stream, null);
            iv.setImageDrawable(d);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        Log.d(LOG_CAT, "onCreate");
    }//end MainActivity

    /*Event Handler: for configuration change*/
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        /*Inspecting the current state of Screen*/
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Toast.makeText(MainActivity.this,
                    "Orientation is Portrait", Toast.LENGTH_SHORT).show();
        }//end if
        else {
            Toast.makeText(MainActivity.this,
                    "Orientation is Landscape", Toast.LENGTH_SHORT).show();
        }//end else
    }//end onConfigurationChanged

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {
            case R.id.action_settings:
                Snackbar.make(coordinatorLayout, "Settings Pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_web:
                /*Snackbar.make(coordinatorLayout, "On the Web Pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                /*Implicit Intent*/
                /*Go to the Website*/
                Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(webURL));/*(to view something, web URL)*/
                /*Resolving the Activity*/
                /*Very Important for Implicit Intent
                *Making sure that there is an app registered, that is used to handle the content
                *which we are sending*/
                if (webIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(webIntent);
                }


                return true;
            case R.id.action_about:
                /*Snackbar.make(coordinatorLayout, "About Pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                /*Explicit Intent: Navigating from one activity to another in the same app*/
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                /*Calling method of current activity*/
                startActivity(intent);
                return true;
            case R.id.action_help:
                Snackbar.make(coordinatorLayout, "Help Pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
            case R.id.action_share:
                Snackbar.make(coordinatorLayout, "Share Pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return true;
        }//end switch

        return super.onOptionsItemSelected(item);
    }//end onOptionsItemSelected

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(LOG_CAT, "onStart()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(LOG_CAT, "onStop()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(LOG_CAT, "onResume()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_CAT, "onDestroy()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(LOG_CAT, "onPause()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(LOG_CAT, "onRestart()");
    }//end onRestart
}