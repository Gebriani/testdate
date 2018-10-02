package orcaella.com.wonderlabs.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import orcaella.com.wonderlabs.R;
import orcaella.com.wonderlabs.model.DateModel;

public class DateItemAdapter extends RecyclerView.Adapter<DateItemAdapter.DateItemViewHolder>{


    private Context context;
    private List<DateModel> dateModelList = new ArrayList<>();

    public DateItemAdapter(Context context, List<DateModel> dateModelList) {
        this.context = context;
        this.dateModelList = dateModelList;
    }

    @NonNull
    @Override
    public DateItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_date_item, viewGroup, false);
        return new DateItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateItemViewHolder dateItemViewHolder, int i) {
        DateModel item = dateModelList.get(i);
        dateItemViewHolder.tvTitle.setText(item.getDate());
    }

    @Override
    public int getItemCount() {
        return dateModelList.size();
    }

    class DateItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;

        public DateItemViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.adapter_date_text);
        }
    }
}
