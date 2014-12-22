package com.trasselbackstudios.dexter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.trasselbackstudios.dexter.data.PokemonTypes;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class DetailsFragment extends Fragment {
    private View mContentView;
    private View mLoadingView;
    private int mShortAnimationDuration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);
        PokemonEntry pokemonEntry = new PokemonEntry(rootView.getContext(), getArguments().getString(Intent.EXTRA_TEXT));

        mContentView = rootView.findViewById(R.id.layout_details);
        mLoadingView = rootView.findViewById(R.id.loading_spinner);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        setupDetails(rootView, pokemonEntry);
        return rootView;
    }

    public void setupDetails(View rootView, PokemonEntry pokemonEntry) {
        if (((ActionBarActivity) getActivity()).getSupportActionBar() != null)
            ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(pokemonEntry.name);

        crossfade();
        ((DetailsActivity) getActivity()).setPokemonID(pokemonEntry.id);
        TextView name = (TextView) rootView.findViewById(R.id.text_view_name);
        TextView types = (TextView) rootView.findViewById(R.id.text_view_types);
        TextView species = (TextView) rootView.findViewById(R.id.text_view_species);
        TextView id = (TextView) rootView.findViewById(R.id.text_view_id);
        TextView heightWidth = (TextView) rootView.findViewById(R.id.text_view_height_width);
        TextView desc = (TextView) rootView.findViewById(R.id.text_view_desc);

        String paddedId = String.format("%03d", Integer.parseInt(pokemonEntry.id));
        String formattedHeight = Integer.parseInt(pokemonEntry.height) / 12 + "'"
                + String.format("%02d", (Integer.parseInt(pokemonEntry.height) - 12 * (Integer.parseInt(pokemonEntry.height) / 12))) + "\" ";

        name.setText(pokemonEntry.name);
        species.setText(pokemonEntry.species);
        types.setText(Html.fromHtml(getColoredTypes(pokemonEntry.types)));
        id.setText("No." + paddedId);
        heightWidth.setText("Ht: " + formattedHeight + "Wt: " + pokemonEntry.weight + " lbs");
        desc.setText(pokemonEntry.desc);

        ImageView image = (ImageView) rootView.findViewById(R.id.image_view_pokemon);
        try {
            Bitmap bitmap = getBitmap("images/" + pokemonEntry.id + ".png");
            bitmap = resizeBitmap(bitmap, 500, 500);
            bitmap = circleBitmap(bitmap, 500 / 2, 3);
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        setupEvolutions(rootView, pokemonEntry);
    }

    private void setupEvolutions(View rootView, PokemonEntry pokemonEntry) {
        ArrayList<PokemonEntry> pokemonEntries = pokemonEntry.getEvolutions(rootView.getContext());
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
                Bitmap bitmap = getBitmap("images/" + pokemonEntries.get(i).id + ".png");
                bitmap = resizeBitmap(bitmap, 300, 300);
                bitmap = circleBitmap(bitmap, 200 / 2, 3);
                String evoText = "→";
                if (pokemonEntry.getEvoLevel() > pokemonEntries.get(i).getEvoLevel())
                    evoText = "←";
                bitmap = drawText(bitmap, evoText);
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

    private Bitmap getBitmap(String strName) throws IOException {
        AssetManager assetManager = getActivity().getAssets();
        InputStream inputStream = assetManager.open(strName);
        return BitmapFactory.decodeStream(inputStream);
    }

    private Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / originalWidth;
        float scaleHeight = ((float) newHeight) / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, false);
    }

    private Bitmap circleBitmap(Bitmap orig, int radius, int border) {
        int width = orig.getWidth();
        int height = orig.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader bitmapShader = new BitmapShader(orig, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawCircle(width / 2, height / 2, radius, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.DKGRAY);
        paint.setStrokeWidth(border);
        canvas.drawCircle(width / 2, height / 2, radius - border / 2, paint);
        return bitmap;
    }

    private Bitmap drawText(Bitmap orig, String text) {
        int width = orig.getWidth();
        int height = orig.getHeight();
        int textSize = 75;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        BitmapShader bitmapShader = new BitmapShader(orig, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setShader(bitmapShader);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawBitmap(orig, 0, 0, paint);
        paint.setShader(null);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.DKGRAY);
        paint.setTextSize(textSize);
        canvas.drawText(text, width / 3, height, paint);
        return bitmap;
    }

    private class OnEvolutionClickListener implements View.OnClickListener {
        private final String id;

        public OnEvolutionClickListener(String id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            setupDetails(v.getRootView(), new PokemonEntry(v.getContext(), id));
        }
    }

    private void crossfade() {
        mContentView.setAlpha(0f);
        mContentView.animate()
                .alpha(1f)
                .setDuration(mShortAnimationDuration)
                .setListener(null);
        mLoadingView.animate()
                .alpha(0f)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mLoadingView.setVisibility(View.GONE);
                    }
                });
    }
}
