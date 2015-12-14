package org.ligboy.demo.stetho.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author Ligboy.Liu ligboy@gmail.com.
 */
public class Photo implements Parcelable {
    private String mTitle;
    private String mUrl;

    public String getTitle() {
        return mTitle;
    }

    public Photo(String title, String url) {
        this.mTitle = title;
        this.mUrl = url;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mUrl);
    }

    public Photo() {
    }

    protected Photo(Parcel in) {
        this.mTitle = in.readString();
        this.mUrl = in.readString();
    }

    public static final Parcelable.Creator<Photo> CREATOR = new Parcelable.Creator<Photo>() {
        public Photo createFromParcel(Parcel source) {
            return new Photo(source);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };
}
