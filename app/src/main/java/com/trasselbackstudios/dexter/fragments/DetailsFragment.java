package com.trasselbackstudios.dexter.fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trasselbackstudios.dexter.R;
import com.trasselbackstudios.dexter.activities.DetailsActivity;
import com.trasselbackstudios.dexter.data.PokemonItem;
import com.trasselbackstudios.dexter.data.PokemonTypes;
import com.trasselbackstudios.dexter.helpers.BitmapUtility;

import java.io.IOException;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {
    private View contentView;
    private View loadingView;
    private int shortAnimationDuration;

    public DetailsFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        PokemonItem pokemonItem = new PokemonItem(rootView.getContext(), getArguments().getString(Intent.EXTRA_TEXT));

        contentView = rootView.findViewById(R.id.layout_details);
        loadingView = rootView.findViewById(R.id.loading_spinner);
        shortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        setupDetails(rootView, pokemonItem);
        return rootView;
    }

    public void setupDetails(View rootView, PokemonItem pokemonItem) {
        if (((ActionBarActivity) getActivity()).getSupportActionBar() != null)
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(pokemonItem.name);

        crossfade();
        ((DetailsActivity) getActivity()).setPokemonId(pokemonItem.id);
        TextView name = (TextView) rootView.findViewById(R.id.text_view_name);
        TextView types = (TextView) rootView.findViewById(R.id.text_view_types);
        TextView species = (TextView) rootView.findViewById(R.id.text_view_species);
        TextView id = (TextView) rootView.findViewById(R.id.text_view_id);
        TextView heightWidth = (TextView) rootView.findViewById(R.id.text_view_height_width);
        TextView desc = (TextView) rootView.findViewById(R.id.text_view_desc);

        String paddedId = String.format("%03d", Integer.parseInt(pokemonItem.id));
        String formattedHeight = Integer.parseInt(pokemonItem.height) / 12 + "'"
                + String.format("%02d", (Integer.parseInt(pokemonItem.height) - 12 * (Integer.parseInt(pokemonItem.height) / 12))) + "\" ";

        name.setText(pokemonItem.name);
        species.setText(pokemonItem.species);
        types.setText(Html.fromHtml(getColoredTypes(pokemonItem.types)));
        id.setText("No." + paddedId);
        heightWidth.setText("Ht: " + formattedHeight + "Wt: " + pokemonItem.weight + " lbs");
        desc.setText(pokemonItem.desc);

        ImageView image = (ImageView) rootView.findViewById(R.id.image_view_pokemon);
        try {
            Bitmap bitmap = BitmapUtility.getBitmap(getActivity(), "images/" + pokemonItem.id + ".png");
            bitmap = BitmapUtility.resizeBitmap(bitmap, 500, 500);
            bitmap = BitmapUtility.circleBitmap(bitmap, 500 / 2, 3);
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            Log.e("DETAILS FRAGMENT", "Setting bitmap: " + e);
        }
        setupEvolutions(rootView, pokemonItem);
    }

    private void setupEvolutions(View rootView, PokemonItem pokemonItem) {
        ArrayList<PokemonItem> pokemonEntries = pokemonItem.getEvolutions(rootView.getContext());
        LinearLayout evolutionsLayout = (LinearLayout) rootView.findViewById(R.id.layout_evolutions);
        evolutionsLayout.removeAllViewsInLayout();
        if (pokemonEntries == null) {
            rootView.findViewById(R.id.separator2).setVisibility(View.GONE);
            rootView.findViewById(R.id.text_view_evolutions).setVisibility(View.GONE);
            return;
        }
        rootView.findViewById(R.id.separator2).setVisibility(View.VISIBLE);
        rootView.findViewById(R.id.text_view_evolutions).setVisibility(View.VISIBLE);
        int weight = pokemonEntries.size() == 1 ? 2 : 1;
        for (int i = 0; i < pokemonEntries.size(); i++) {
            try {
                Bitmap bitmap = BitmapUtility.getBitmap(getActivity(), "images/" + pokemonEntries.get(i).id + ".png");
                bitmap = BitmapUtility.resizeBitmap(bitmap, 300, 300);
                bitmap = BitmapUtility.circleBitmap(bitmap, 200 / 2, 3);
                String evoText = "→";
                if (pokemonItem.getEvoForm() > pokemonEntries.get(i).getEvoForm())
                    evoText = "←";
                bitmap = BitmapUtility.drawText(bitmap, evoText);
                ImageView imageView = new ImageView(rootView.getContext());
                imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, weight));
                imageView.setImageBitmap(bitmap);
                imageView.setOnClickListener(new OnEvolutionClickListener(pokemonEntries.get(i).id));
                evolutionsLayout.addView(imageView);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getColoredTypes(String types) {
        String coloredTypes = "";
        for (String type : types.split(",")) {
            coloredTypes += PokemonTypes.getColoredType(type);
            coloredTypes += ", ";
        }
        if (coloredTypes.endsWith(", "))
            coloredTypes = coloredTypes.substring(0, coloredTypes.length() - 2);
        return coloredTypes;
    }


    private class OnEvolutionClickListener implements View.OnClickListener {
        private final String id;

        public OnEvolutionClickListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            setupDetails(v.getRootView(), new PokemonItem(v.getContext(), id));
        }
    }

    private void crossfade() {
        loadingView.setVisibility(View.VISIBLE);
        contentView.setAlpha(0f);
        contentView.animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration)
                .setListener(null);
        loadingView.animate()
                .alpha(0f)
                .setDuration(shortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        loadingView.setVisibility(View.GONE);
                    }
                });
    }
}
