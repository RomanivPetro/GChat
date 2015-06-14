package com.android.romaniv.gchat;

import android.location.Location;
import android.util.Log;

import java.util.Map;

/**
 * Created by Петро on 07.06.2015.
 */
public class DistanceCalc {
    private static final double EARTH_RADIUS = 6371.;
    private  Point SourcePoint;
    private double distance;
    private Point AddressPoint;


    public DistanceCalc(Location loc, Point ploc){
        SourcePoint = new Point(loc.getLongitude(), loc.getLatitude());
        AddressPoint = ploc;
    }

    public double getDistance(){
        final double dlng = deg2rad(SourcePoint.lng - AddressPoint.lng);
        final double dlat = deg2rad(SourcePoint.lat - AddressPoint.lat);
        final double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) + Math.cos(deg2rad(AddressPoint.lat))
                * Math.cos(deg2rad(SourcePoint.lat)) * Math.sin(dlng / 2) * Math.sin(dlng / 2);
        final double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        distance = c*EARTH_RADIUS;
        Log.d("GChat","Distance : " + distance);
        return distance;
    }

    private static double deg2rad(final double degree) {
        return degree * (Math.PI / 180);
    }


    public static class Point {
        public double lat;
        public double lng;

        public Point(final double lng, final double lat) {
            this.lng = lng;
            this.lat = lat;
        }
    }
}


