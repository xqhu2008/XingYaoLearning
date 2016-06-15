package com.bluehawk.xingyaolearning;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Alex on 2016/6/1.
 */
public class YaoWordImage {
    public static Bitmap loadBitmapFromAsset(Context context, String fileName) {
        Bitmap bmp = null;
        try {
            byte [] buffer = null;
            InputStream in = context.getAssets().open(fileName);

            int length = in.available();
            buffer = new byte[length];
            in.read(buffer);
            if (buffer != null) {
                bmp = BitmapFactory.decodeByteArray(buffer, 0, buffer.length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bmp;
    }
}
