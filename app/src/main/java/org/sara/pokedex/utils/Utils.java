package org.sara.pokedex.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

//acomoda las imágenes del drawable
public class Utils {
    public static Drawable getDrawable(Context context, String drawableName) {
        Resources res = context.getResources();
        int resID = res.getIdentifier(drawableName, "drawable", context.getPackageName());
        return res.getDrawable(resID);
    }
}
