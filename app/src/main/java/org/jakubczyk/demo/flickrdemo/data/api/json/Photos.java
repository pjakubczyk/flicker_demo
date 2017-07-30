package org.jakubczyk.demo.flickrdemo.data.api.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Photos {

    @SerializedName("page")
    public Integer pageNumber;

    @SerializedName("pages")
    public Integer totalPages;

    @SerializedName("perpage")
    public Integer pageSize;

    @SerializedName("total")
    String totalElements;

    @SerializedName("photo")
    public List<Photo> photoList;

}