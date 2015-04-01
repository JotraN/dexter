package com.trasselbackstudios.dexter.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;

import com.trasselbackstudios.dexter.R;
import com.trasselbackstudios.dexter.data.PokemonItem;
import com.trasselbackstudios.dexter.fragments.DetailsFragment;


public class DetailsActivity extends ActionBarActivity {
    private GestureDetectorCompat gestureDetector;
    private DetailsFragment detailsFragment = null;
    private static String pokemonId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        gestureDetector = new GestureDetectorCompat(this, new NextPokemonGesture());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            pokemonId = extras.getString(Intent.EXTRA_TEXT);
            if (savedInstanceState == null)
                sendToFragment(pokemonId);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void onResume() {
        super.onResume();
        if(detailsFragment == null)
            sendToFragment(pokemonId);
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
        this.gestureDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    private class NextPokemonGesture extends GestureDetector.SimpleOnGestureListener {
        private int maxId = 719;
        private int minId = 1;

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            if (Math.abs(event1.getX() - event2.getX()) > 300 || Math.abs(velocityX) > 1000) {
                int next = Integer.parseInt(pokemonId) + 1;
                if (velocityX > 0)
                    next = Integer.parseInt(pokemonId) - 1;
                if (next < minId || next > maxId) return true;
                pokemonId = String.valueOf(next);
                detailsFragment.setupDetails(getWindow().getDecorView().getRootView(), new PokemonItem(getApplicationContext(), pokemonId));
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
        switch (id) {
            case R.id.action_type_effectiveness:
                Intent intent = new Intent(this, TypeEffectivenessActivity.class).putExtra(Intent.EXTRA_TEXT, pokemonId);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setPokemonId(String id) {
        pokemonId = id;
    }
}
