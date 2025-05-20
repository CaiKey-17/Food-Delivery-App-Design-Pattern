package com.example.project_android.Model;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class GeoapifyResponse {
    @SerializedName("features")
    private List<Feature> features;

    public List<Feature> getFeatures() {
        return features;
    }

    public static class Feature {
        @SerializedName("properties")
        private Properties properties;

        public Properties getProperties() {
            return properties;
        }

        public static class Properties {
            @SerializedName("formatted")
            private String formatted;

            public String getFormatted() {
                return formatted;
            }
        }
    }
}