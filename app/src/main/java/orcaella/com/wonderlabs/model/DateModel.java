package orcaella.com.wonderlabs.model;

import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateModel implements Comparable<DateModel> {

    @SerializedName("date")
    private String date;


    public DateModel() {
    }

    public DateModel(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Date getDateData() throws ParseException {
        return toDate(this.date);
    }

    @Override
    public boolean equals(Object obj) {
        DateModel object = (DateModel) obj;
        return object.date.equalsIgnoreCase(this.date);
    }

    public static Date toDate(String value) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return format.parse(value);
    }

    @Override
    public int compareTo(@NonNull DateModel o) {
        try {
            if (getDateData() == null || o.getDateData() == null) {
                return 0;
            } else {
                return getDateData().compareTo(o.getDateData());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }
}
