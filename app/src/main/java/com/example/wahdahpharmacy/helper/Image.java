package com.example.wahdahpharmacy.helper;

import android.net.Uri;

public class Image {
    String imageUri;

    public Image(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }
}
