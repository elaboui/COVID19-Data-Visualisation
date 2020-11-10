package me.nassimi.covid19_data_visualisation.models;

public class NewsItem {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private String url;

    public NewsItem(String mText1, String mText2, String url) {
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.url = url;
    }

    public NewsItem(String mText1,  String url) {
        this.mText1 = mText1;
        this.url = url;
    }

    public NewsItem(int mImageResource, String mText1, String mText2, String url) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public NewsItem(int mImageResource, String mText1, String mText2) {
        this.mImageResource = mImageResource;
        this.mText1 = mText1;
        this.mText2 = mText2;
    }

    public int getmImageResource() {
        return mImageResource;
    }


    public String getmText1() {
        return mText1;
    }


    public String getmText2() {
        return mText2;
    }


}
