package com.tangyu.component.util;

import android.app.PendingIntent;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;

import com.tangyu.component.ActionCfg;

/**
 * Before use GPSHelper. u should add permission in AndroidManifest.xml</br>
 * "android.permission.ACCESS_FINE_LOCATION"
 *
 * @author bin
 */
public class GPSHelper {

    public static final int MIN_DISTANCE = 500;

    public static final int MIN_TIME = 120 * 1000;

    public static int minDistance = MIN_DISTANCE;

    public static int minTime = MIN_TIME;

    public static final String ACTION_GPS = ActionCfg.APP_NAME + ActionCfg.ACT_GPS;

    public static boolean isGPSEnable(Context ctx) {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    /**
     * @param ctx
     * @return
     */
    public static Location getSimpleLocation(Context ctx) {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        String provider = buildBestProvider(ctx);
        return lm.getLastKnownLocation(provider);
    }

    public static String buildBestProvider(Context ctx) {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(true);
        criteria.setSpeedRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        return lm.getBestProvider(criteria, true);
    }

    /**
     * add location update listener.
     * u shoud call {@link GPSHelper#removePendingIntent(android.content.Context, android.app.PendingIntent)} in somewhere
     *
     * @param ctx
     * @param provider get from {@link GPSHelper#getSimpleLocation(android.content.Context)}
     * @param pi       your broadcast
     */
    public static void addLocationUpdate(Context ctx, String provider, PendingIntent pi) {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        lm.requestLocationUpdates(provider, minTime, minDistance, pi);
    }

    public static void removePendingIntent(Context ctx, PendingIntent pi) {
        LocationManager lm = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        lm.removeUpdates(pi);
    }

}
