package app.drool.respite.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.text.Spanned;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;

import app.drool.respite.asyncloaders.AsyncDrawableCache;
import app.drool.respite.asyncloaders.AsyncDrawableURL;
import app.drool.respite.asyncloaders.PreviewFromCacheTask;
import app.drool.respite.asyncloaders.PreviewFromURLTask;

/**
 * Created by drool on 6/16/16.
 */

public class Utilities {
    private static final String TAG = "Utilities.java";

    public static String getReadableCreationTime(Date createdTime) {
        Date currentTime = new Date();
        long diffMs = currentTime.getTime() - createdTime.getTime();

        if (diffMs < 0)
            return "in the future";

        long diffMinutes = diffMs / (1000 * 60);
        int diffMinutesInt = (int) diffMinutes;

        if (diffMinutesInt < 2)
            return "just now";

        if (diffMinutesInt < 60)
            return String.valueOf(diffMinutesInt) + "m";

        long diffHours = (long) Math.ceil(diffMinutes / 60);
        int diffHoursInt = (int) diffHours;

        if (diffHoursInt < 24)
            return String.valueOf(diffHoursInt) + "h";

        long diffDays = (long) Math.ceil(diffHours / 24);
        int diffDaysInt = (int) diffDays;

        if (diffDaysInt < 365)
            return String.valueOf(diffDays) + "d";

        return "";
    }

    public static String getFormattedCreationTime(Date createdTime) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        return format.format(createdTime);
    }

    public static PreviewFromCacheTask getPreviewFromCacheTask(ImageView preview) {
        final Drawable drawable = preview.getDrawable();
        if (drawable instanceof AsyncDrawableCache) {
            final AsyncDrawableCache asyncDrawableCache = (AsyncDrawableCache) drawable;
            return asyncDrawableCache.getPreviewFromCacheTask();
        }
        return null;
    }

    public static PreviewFromURLTask getPreviewFromURLTask(ImageView preview) {
        final Drawable drawable = preview.getDrawable();
        if (drawable instanceof AsyncDrawableURL) {
            final AsyncDrawableURL asyncDrawable = (AsyncDrawableURL) drawable;
            return asyncDrawable.getPreviewFromURLTask();
        }
        return null;
    }

    public static int getPixelsFromDPs(Context mContext, int dps) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dps * scale + 0.5f);
    }

    public static Spanned getHTMLFromMarkdown(String md) {
        String one = Html.fromHtml(md).toString();
        String two = one.substring(16, one.length() - 11).replace("<p>", "").replace("</p>", "<br><br>");
        return Html.fromHtml(two);
    }
}
