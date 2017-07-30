package org.jakubczyk.demo.flickrdemo.data.api.json;

import com.google.gson.annotations.SerializedName;

public class Photo {

    public String id;

    public String owner;

    public String secret;

    public String server;

    public Integer farm;

    public String title;

    @SerializedName("ispublic")
    public Integer isPublic;

    @SerializedName("isfriend")
    public Integer isFriend;

    @SerializedName("isfamily")
    public Integer isFamily;

    /**
     *       {
     "id": "36227875676",
     "owner": "148036032@N07",
     "secret": "6b2914ce9d",
     "server": "4323",
     "farm": 5,
     "title": "Precious was very happy that folks liked the last pic I... - The Caturday",
     "ispublic": 1,
     "isfriend": 0,
     "isfamily": 0
     },
     */
}
