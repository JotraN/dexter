package com.trasselbackstudios.dexter.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.trasselbackstudios.dexter.R;
import com.trasselbackstudios.dexter.fragments.TypeEffectivenessFragment;


public class TypeEffectivenessActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_type_effectiveness);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            if (savedInstanceState == null) {
                TypeEffectivenessFragment typeEffectivenessFragment = new TypeEffectivenessFragment();
                typeEffectivenessFragment.setArguments(extras);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container, typeEffectivenessFragment)
                        .commit();
            }
        }

        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
