package com.trasselbackstudios.dexter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.trasselbackstudios.dexter.data.PokemonContract;
import com.trasselbackstudios.dexter.data.PokemonDatabase;


public class DetailsActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String pokemonID = extras.getString(Intent.EXTRA_TEXT);
            setTitle(pokemonID);
            if (savedInstanceState == null)
                sendToFragment(pokemonID);
        }
    }

    private void setTitle(String id){
        PokemonDatabase mDb = new PokemonDatabase(this);
        Cursor cursor = mDb.getDetailsItems(id);
        String name = cursor.getString(cursor.getColumnIndex(PokemonContract.DetailsEntry.COLUMN_NAME));
        cursor.close();
        mDb.close();

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle(name);
    }

    private void sendToFragment(String id){
        Bundle bundle = new Bundle();
        bundle.putString(Intent.EXTRA_TEXT, id);
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detailsFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
