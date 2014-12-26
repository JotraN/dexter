package com.trasselbackstudios.dexter;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.trasselbackstudios.dexter.data.PokemonDatabase;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PokemonFragment())
                    .commit();
        }
        PokemonDatabase.forceDatabaseReload(this);
    }
}
