package com.example.project_android.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;
public class RouteResponse {
    @SerializedName("features")
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public static class Feature {
        @SerializedName("geometry")
        private Geometry geometry;  // Trường geometry

        @SerializedName("properties")
        private Properties properties;

        public Geometry getGeometry() {  // Phương thức để lấy Geometry
            return geometry;
        }

        public Properties getProperties() {
            return properties;
        }

        public static class Properties {
            @SerializedName("route_length")
            private double routeLength;
            private double distance;
            private double time;
            public double getRouteLength() {
                return routeLength;
            }

            public double getDistance(){
                return distance;
            }
            public double getTime(){
                return time;
            }
        }
    }

    public static class Geometry {
        @SerializedName("type")
        private String type;

        @SerializedName("coordinates")
        private List<List<List<Double>>> coordinates; // Mảng 3 chiều cho tọa độ

        public String getType() {
            return type;
        }

        public List<List<List<Double>>> getCoordinates() {
            return coordinates;
        }
    }
}