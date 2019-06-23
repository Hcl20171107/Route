package com.picture.DrawRoute;

public class Point {

    private double latitude;
    private double longitude;

    public Point() {
    }

    public Point(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Point) {
            Point bmapPoint = (Point) obj;
            return (bmapPoint.getLongitude() == longitude && bmapPoint.getLatitude() == latitude) ? true
                    : false;
        } else {
            return false;
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Point [latitude=" + latitude + ", longitude=" + longitude + "]";
    }

}
