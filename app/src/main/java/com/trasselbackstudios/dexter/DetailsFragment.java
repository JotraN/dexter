package com.trasselbackstudios.dexter;

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
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.trasselbackstudios.dexter.data.PokemonTypes;

import java.io.IOException;
import java.io.InputStream;

public class DetailsFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        TextView name = (TextView) rootView.findViewById(R.id.text_view_name);
        TextView types = (TextView) rootView.findViewById(R.id.text_view_types);
        TextView species = (TextView) rootView.findViewById(R.id.text_view_species);
        TextView id = (TextView) rootView.findViewById(R.id.text_view_id);
        TextView heightWidth = (TextView) rootView.findViewById(R.id.text_view_height_width);
        TextView desc = (TextView) rootView.findViewById(R.id.text_view_desc);

        Pokemon pokemon = new Pokemon(rootView.getContext(), getArguments().getString(Intent.EXTRA_TEXT));
        String paddedId = String.format("%03d", Integer.parseInt(pokemon.id));
        String formattedHeight = Integer.parseInt(pokemon.height) / 12 + "'"
                + String.format("%02d", (Integer.parseInt(pokemon.height) - 12 * (Integer.parseInt(pokemon.height) / 12))) + "\" ";

        name.setText(pokemon.name);
        species.setText(pokemon.species);
        types.setText(Html.fromHtml(getColoredTypes(pokemon.types)));
        id.setText("No." + paddedId);
        heightWidth.setText("Ht: " + formattedHeight + "Wt: " + pokemon.weight + " lbs");
        desc.setText(pokemon.desc);

        ImageView image = (ImageView) rootView.findViewById(R.id.image_view_pokemon);
        try {
            Bitmap bitmap = getBitmap("images/" + pokemon.id + ".png");
            image.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rootView;
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
        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
        bitmap = resizeBitmap(bitmap, 500, 500);
        bitmap = circleBitmap(bitmap, 500/2, 3);
        return bitmap;
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
}
