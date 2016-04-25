package com.example.abhiu.myapplication;

import android.graphics.Bitmap;

/**
 * Created by bschiranth on 24-04-16.
 */
public class Complaint {
    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Bitmap getBmp() {
        return bmp;
    }

    public void setBmp(Bitmap bmp) {
        this.bmp = bmp;
    }

    String landmark;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReporter() {
        return reporter;
    }

    public void setReporter(String reporter) {
        this.reporter = reporter;
    }

    String description;
    String reporter;
    Bitmap bmp;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public static int count=0;

}
