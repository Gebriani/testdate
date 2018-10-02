package orcaella.com.wonderlabs.model;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateModel {

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
}
