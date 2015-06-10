package net.issoa.helper;

import android.app.Application;

public class MyApplication extends Application {

    private String surahList;

    public String getSurahListVariable() {
        return surahList;
    }

    public void setSurahListVariable(String surahlist) {
        this.surahList = surahlist;
    }
}