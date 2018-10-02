package orcaella.com.wonderlabs.view;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import orcaella.com.wonderlabs.R;
import orcaella.com.wonderlabs.adapter.DateItemAdapter;
import orcaella.com.wonderlabs.model.DateModel;
import orcaella.com.wonderlabs.network.RestClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TreeSet;

import android.app.DatePickerDialog;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvDates;
    private ProgressBar progressBar;
    private TextView tvDate;
    private Button btSearch;
    private DateItemAdapter adapter;
    private List<DateModel> dateModelList = new ArrayList<>();
    private int yearchoose, mounthchoose, daychoose, yearsNow;
    private DateModel dateNow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvDates = findViewById(R.id.main_list);
        tvDate = findViewById(R.id.main_search_date);
        progressBar = findViewById(R.id.main_progressbar);
        rvDates.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DateItemAdapter(this, dateModelList);
        rvDates.setAdapter(adapter);
        findViewById(R.id.main_search_button).setOnClickListener(this);
        tvDate.setOnClickListener(this);
        daychoose = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        mounthchoose = Calendar.getInstance().get(Calendar.MONTH);
        yearchoose = Calendar.getInstance().get(Calendar.YEAR);
        yearsNow = Calendar.getInstance().get(Calendar.YEAR);
        getListDate();

    }

    private void getListDate() {
        progressBar.setVisibility(View.VISIBLE);
        RestClient.getClient().getListDate().enqueue(new Callback<List<DateModel>>() {
            @Override
            public void onResponse(Call<List<DateModel>> call, Response<List<DateModel>> response) {
                if (response.isSuccessful()) {
                    dateModelList.clear();
                    dateModelList.addAll(response.body());
                    Collections.sort(dateModelList);
                    adapter.notifyDataSetChanged();

                    Calendar calendar = Calendar.getInstance();
                    SimpleDateFormat mdformat = new SimpleDateFormat("dd/MM/yyyy");
                    String tempdateNow = mdformat.format(calendar.getTime());
                    try {
                        findNearestDate(tempdateNow);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Maaf Server Tidak Dapat Diakses", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<List<DateModel>> call, Throwable t) {

            }
        });
    }

    private void findNearestDate(String chooseDate) throws ParseException {
        DateModel dateMod = new DateModel(chooseDate);
        final int ind = dateModelList.indexOf(dateMod);
        if (ind != -1) {
            rvDates.post(new Runnable() {
                @Override
                public void run() {
                    rvDates.smoothScrollToPosition(ind);
                }
            });
        } else {
            List<Date> templist = new ArrayList<>();
            DateModel tempM = new DateModel();
            for (int i = 0; i < dateModelList.size(); i++) {
                templist.add(dateModelList.get(i).getDateData());
            }

            if (getDateNearestHigher(templist, toDate(chooseDate)) != null) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                String tt = String.valueOf(dateFormatter.format(getDateNearestHigher(templist, toDate(chooseDate)).getTime()));
                tempM.setDate(tt);
                final int indss = dateModelList.indexOf(tempM);
                rvDates.post(new Runnable() {
                    @Override
                    public void run() {
                        rvDates.smoothScrollToPosition(indss);
                    }
                });

            } else if (getDateNearestLower(templist, toDate(chooseDate)) != null) {
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                String tt = String.valueOf(dateFormatter.format(getDateNearestLower(templist, toDate(chooseDate)).getTime()));
                tempM.setDate(tt);
                final int indss = dateModelList.indexOf(tempM);
                rvDates.post(new Runnable() {
                    @Override
                    public void run() {
                        rvDates.smoothScrollToPosition(indss);
                    }
                });


            }

        }

    }


    private Date getDateNearestLower(List<Date> dates, Date targetDate) {
        return new TreeSet<Date>(dates).lower(targetDate);
    }

    private Date getDateNearestHigher(List<Date> dates, Date targetDate) {
        return new TreeSet<Date>(dates).higher(targetDate);
    }

    public static Date toDate(String value) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        return format.parse(value);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_search_button:
                try {
                    findNearestDate(tvDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.main_search_date:
                final SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
                new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        Calendar newDate = Calendar.getInstance();
                        newDate.set(year, monthOfYear, dayOfMonth);
                        tvDate.setText(String.valueOf(dateFormatter.format(newDate.getTime())));
                        yearchoose = year;
                        mounthchoose = monthOfYear;
                        daychoose = dayOfMonth;
                    }
                }, yearchoose, mounthchoose, daychoose).show();
                break;
        }
    }
}














