package org.jakubczyk.demo.flickrdemo.data.api.json;

import com.google.gson.annotations.SerializedName;

public class SearchResponse {

    public Photos photos;

    @SerializedName("stat")
    public String responseStatus;
}
