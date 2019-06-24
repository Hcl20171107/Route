package com.picture.DrawRoute;

public class CoordinateConversion {
    private static final double x_pi = 3.14159265358979324 * 3000.0 / 180.0;

    private static final double pi = 3.14159265358979324;  
    private static final double a = 6378245.0; //卫星椭球坐标投影到平面地图坐标系的投影因子。
    private static final double ee = 0.00669342162296594323; //ee: 椭球的偏心率。

    /**
     * gg_lat 纬度 
     * gg_lon 经度 
     * GCJ-02转换BD-09 Google地图经纬度转百度地图经纬度
     * */
    public static Point google_bd_encrypt(double gg_latitude, double gg_longitude) {
        Point point = new Point();
        double x = gg_longitude, y = gg_latitude;
        double z = Math.sqrt(x * x + y * y) + 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) + 0.000003 * Math.cos(x * x_pi);
        double bd_longitude = z * Math.cos(theta) + 0.0065;
        double bd_latitude = z * Math.sin(theta) + 0.006;
        point.setLatitude(bd_latitude);
        point.setLongitude(bd_longitude);
        return point;
    }

    /**
     * wgLat 纬度 
     * wgLon 经度 
     * BD-09转换GCJ-02 百度转google
     * */
    public static Point bd_google_encrypt(double bd_latitude, double bd_longitude) {
        Point point = new Point();
        double x = bd_longitude - 0.0065, y = bd_latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_longitude = z * Math.cos(theta);
        double gg_latitude = z * Math.sin(theta);
        point.setLatitude(gg_latitude);
        point.setLongitude(gg_longitude);
        return point;
    }
    
    
    /**
     * wgLat 纬度 
     * wgLon 经度 
     * BD-09转换GCJ-02 百度转
     * */
    public static Point bd_google_baidu_encrypt(double bd_latitude, double bd_longitude) {
        Point point = new Point();
        point=wgs_gcj_encrypts(bd_latitude,bd_longitude);
        point=google_bd_encrypt(point.getLatitude(),point.getLongitude());
        return point;
    }
    

    /**
     * wgLat 纬度
     * wgLon 经度
     * WGS-84 到 GCJ-02 的转换（即 GPS 加偏）
     * */
    public static Point wgs_gcj_encrypts(double wgLatitude, double wgLongitude) {
        Point point = new Point();
        if (outOfChina(wgLatitude, wgLongitude)) {
            point.setLatitude(wgLatitude);
            point.setLongitude(wgLongitude);
            return point;
        }
        double dLatitude = transformLat(wgLongitude - 105.0, wgLatitude - 35.0);
        double dLongitude = transformLon(wgLongitude - 105.0, wgLatitude - 35.0);
        double radLatitude = wgLatitude / 180.0 * pi;
        double magic = Math.sin(radLatitude);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLatitude = (dLatitude * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLongitude = (dLongitude * 180.0) / (a / sqrtMagic * Math.cos(radLatitude) * pi);
        double latitude = wgLatitude + dLatitude;
        double longitude = wgLongitude + dLongitude;
        point.setLatitude(latitude);
        point.setLongitude(longitude);
        return point;
    }

    public static void transform(double wgLatitude, double wgLongitude, double[] latlng) {
        if (outOfChina(wgLatitude, wgLongitude)) {
            latlng[0] = wgLatitude;
            latlng[1] = wgLongitude;
            return;
        }
        double dLatitude = transformLat(wgLongitude - 105.0, wgLatitude - 35.0);
        double dLongitude = transformLon(wgLongitude - 105.0, wgLatitude - 35.0);
        double radLatitude = wgLatitude / 180.0 * pi;
        double magic = Math.sin(radLatitude);
        magic = 1 - ee * magic * magic;
        double sqrtMagic = Math.sqrt(magic);
        dLatitude = (dLatitude * 180.0) / ((a * (1 - ee)) / (magic * sqrtMagic) * pi);
        dLongitude = (dLongitude * 180.0) / (a / sqrtMagic * Math.cos(radLatitude) * pi);
        latlng[0] = wgLatitude + dLatitude;
        latlng[1] = wgLongitude + dLongitude;
    }

    private static boolean outOfChina(double latitude, double longitude) {
        if (longitude < 72.004 || longitude > 137.8347)
            return true;
        if (latitude < 0.8293 || latitude > 55.8271)
            return true;
        return false;
    }

    private static double transformLat(double x, double y) {
        double ret = -100.0 + 2.0 * x + 3.0 * y + 0.2 * y * y + 0.1 * x * y
                + 0.2 * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(y * pi) + 40.0 * Math.sin(y / 3.0 * pi)) * 2.0 / 3.0;
        ret += (160.0 * Math.sin(y / 12.0 * pi) + 320 * Math.sin(y * pi / 30.0)) * 2.0 / 3.0;
        return ret;
    }

    private static double transformLon(double x, double y) {
        double ret = 300.0 + x + 2.0 * y + 0.1 * x * x + 0.1 * x * y + 0.1
                * Math.sqrt(Math.abs(x));
        ret += (20.0 * Math.sin(6.0 * x * pi) + 20.0 * Math.sin(2.0 * x * pi)) * 2.0 / 3.0;
        ret += (20.0 * Math.sin(x * pi) + 40.0 * Math.sin(x / 3.0 * pi)) * 2.0 / 3.0;
        ret += (150.0 * Math.sin(x / 12.0 * pi) + 300.0 * Math.sin(x / 30.0
                * pi)) * 2.0 / 3.0;
        return ret;
    }

}
