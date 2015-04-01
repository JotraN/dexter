package com.trasselbackstudios.dexter.helpers;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;

import java.io.IOException;
import java.io.InputStream;

public class BitmapUtility {

    public static Bitmap getBitmap(Context context, String strName) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream = assetManager.open(strName);
        return BitmapFactory.decodeStream(inputStream);
    }

    public static Bitmap resizeBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        float scaleWidth = ((float) newWidth) / originalWidth;
        float scaleHeight = ((float) newHeight) / originalHeight;
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        return Bitmap.createBitmap(bitmap, 0, 0, originalWidth, originalHeight, matrix, false);
    }

    public static Bitmap circleBitmap(Bitmap orig, int radius, int border) {
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

    public static Bitmap drawText(Bitmap orig, String text) {
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
}
