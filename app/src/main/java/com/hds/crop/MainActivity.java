package com.hds.crop;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.BidiFormatter;
import android.text.TextDirectionHeuristics;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private CropLayout cropLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] timeIds = TimeZone.getAvailableIDs();
        for (String key : timeIds) {
            TimeZone timeZone = TimeZone.getTimeZone(key);
            final String gmt = displayTimeZone(timeZone);
            final String gmt1 = getTimeZoneText(timeZone, false);
            if (!TextUtils.equals(gmt, gmt1)) {
                Log.d("..", key + "," + gmt + "," + gmt1);
            }
        }
        cropLayout = findViewById(R.id.v_crop);
        textView = findViewById(R.id.v_content);
        cropLayout.setSizeUpdateListener(new CropLayout.SizeUpdateListener() {
            @Override
            public void update(CropLayout layout, int w, int h) {
                textView.setVisibility((h < 250 || w < 400) ? View.INVISIBLE : View.VISIBLE);
            }
        });
    }

    private static final String FORMAT_PH_P = "GMT+%02d:%02d";
    private static final String FORMAT_PH_N = "GMT%02d:%02d";

    private static String displayTimeZone(TimeZone tz) {
        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);
        // avoid -4:-30 issue
        minutes = Math.abs(minutes);
        if (hours >= 0) {
            return String.format(Locale.getDefault(), FORMAT_PH_P, hours, minutes);
        } else {
            return String.format(Locale.getDefault(), FORMAT_PH_N, hours, minutes);
        }
    }

    public static String getTimeZoneText(TimeZone tz, boolean includeName) {
        Date now = new Date();

        // Use SimpleDateFormat to format the GMT+00:00 string.
        SimpleDateFormat gmtFormatter = new SimpleDateFormat("ZZZZ");
        gmtFormatter.setTimeZone(tz);
        String gmtString = gmtFormatter.format(now);

        // Ensure that the "GMT+" stays with the "00:00" even if the digits are RTL.
        BidiFormatter bidiFormatter = BidiFormatter.getInstance();
        Locale l = Locale.getDefault();
        boolean isRtl = TextUtils.getLayoutDirectionFromLocale(l) == View.LAYOUT_DIRECTION_RTL;
        gmtString = bidiFormatter.unicodeWrap(gmtString,
                isRtl ? TextDirectionHeuristics.RTL : TextDirectionHeuristics.LTR);

        if (!includeName) {
            return gmtString;
        }

        // Optionally append the time zone name.
        SimpleDateFormat zoneNameFormatter = new SimpleDateFormat("zzzz");
        zoneNameFormatter.setTimeZone(tz);
        String zoneNameString = zoneNameFormatter.format(now);

        // We don't use punctuation here to avoid having to worry about localizing that too!
        return gmtString + " " + zoneNameString;
    }

}
