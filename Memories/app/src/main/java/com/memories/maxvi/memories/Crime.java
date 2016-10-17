package com.memories.maxvi.memories;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;


public class Crime {
    private UUID mId;
    private String mTitle;
    private String mDate;
    private boolean mSolved;

    public String getDate() {
        DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH);
        mDate = dateFormat.format(new Date());
        DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT, Locale.ENGLISH);
        mDate += " " + timeFormat.format(new Date());
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    public Crime() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
