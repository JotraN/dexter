package com.trasselbackstudios.dexter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;


public class DetailsActivity extends ActionBarActivity {
    private GestureDetectorCompat mDetector;
    private DetailsFragment detailsFragment;
    private String pokemonID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        mDetector = new GestureDetectorCompat(this, new NextPokemonGesture());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pokemonID = extras.getString(Intent.EXTRA_TEXT);
            if (savedInstanceState == null)
                sendToFragment(pokemonID);
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onPause(){
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private void sendToFragment(String id) {
        Bundle bundle = new Bundle();
        bundle.putString(Intent.EXTRA_TEXT, id);
        detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, detailsFragment)
                .commit();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class NextPokemonGesture extends GestureDetector.SimpleOnGestureListener {
        private int max = 719;
        private int min = 1;

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if (Math.abs(event1.getX() - event2.getX()) > 300 || Math.abs(velocityX) > 1000) {
                int next = Integer.parseInt(pokemonID) + 1;
                if (velocityX > 0)
                    next = Integer.parseInt(pokemonID) - 1;
                if (next < min || next > max) return true;
                detailsFragment.setupDetails(getWindow().getDecorView().getRootView(), new PokemonEntry(getApplicationContext(), String.valueOf(next)));
            }
            return true;
        }
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

    public void setPokemonID(String id) {
        pokemonID = id;
    }
}
