package com.job.mypersonalapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vstechlab.easyfonts.EasyFonts;

import org.polaric.colorful.CActivity;
import org.polaric.colorful.Colorful;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = ResultActivity.class.getSimpleName();
    // SharedPreferences
    private SharedPreferences sharedPreferences;

    private TextView usernameText;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        usernameText = (TextView) findViewById(R.id.fullname_text);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        final String fuente = sharedPreferences.getString("fuente", null);
        if (fuente.equals("1")) {
            // Toast.makeText(this, "No hay valor", Toast.LENGTH_SHORT).show();
            usernameText.setTypeface(EasyFonts.greenAvocado(this));
        } else if (fuente.equals("2")) {
            // Toast.makeText(this, "asdasr", Toast.LENGTH_SHORT).show();
            usernameText.setTypeface(EasyFonts.androidNationBold(this));
        } else if (fuente.equals("3")) {
            usernameText.setTypeface(EasyFonts.walkwayBlack(this));
        }

        // get username from SharedPreferences
        final String username = sharedPreferences.getString("username", null);
        Log.d(TAG, "username: " + username);

        usernameText.setText(username);

        // Setear Toolbar como action bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Set DrawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, android.R.string.ok, android.R.string.cancel);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Set NavigationItemSelectedListener
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // Do action by menu item id
                switch (menuItem.getItemId()) {
                    case R.id.inicio:
                        Toast.makeText(ResultActivity.this, "Soy " + username, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.datos:
                        Toast.makeText(ResultActivity.this, "Me llamo " + username, Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.configuracion:
                        Intent intent = new Intent(ResultActivity.this, MyPreferencesActivity.class);
                        intent.putExtra("as", TAG);
                        startActivity(intent);
                        break;
                    case R.id.cerrar_sesion:
                        // remove from SharedPreferences
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        boolean success = editor.putBoolean("islogged", false).commit();
                        // boolean success = editor.clear().commit(); // not recommended
                        startActivity(new Intent(ResultActivity.this, MainActivity.class));
                        Toast.makeText(ResultActivity.this, "Sesion Cerrada", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                }
                // Close drawer
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        // Change navigation header information
        ImageView photoImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.menu_photo);
        photoImage.setBackgroundResource(R.drawable.ic_profile);

        TextView fullnameText = (TextView) navigationView.getHeaderView(0).findViewById(R.id.menu_fullname);
        fullnameText.setText("Bienvenido " + username);

    }

}