package com.example.junlinliu.calorietracker.utils;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.junlinliu.calorietracker.R;
import com.example.junlinliu.calorietracker.data.DiaryItem;
import com.example.junlinliu.calorietracker.data.FoodItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liu Junlin on 2018/3/16.
 */

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ViewHolder> {

    private List<DiaryItem> itemList = new ArrayList<>();
    public ItemListAdapter(List<DiaryItem> itemList) {
        this.itemList = itemList;
    }
    private OnItemClickListner onItemClickListner;


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.diary_content, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (itemList.isEmpty()) {

        } else {
            holder.title.setText((itemList.get(position)).getTitle());
            holder.value.setText(Integer.toString((itemList.get(position)).getTotalCalorie()));
            holder.amount.setText(Double.toString((itemList.get(position)).getUnitNumber()) + " " + ((FoodItem) itemList.get(position)).getUnitName());
            holder.all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListner.onItemClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return itemList == null ? 0 : itemList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView value;
        TextView amount;
        RelativeLayout all;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.diary_content_title);
            value = itemView.findViewById(R.id.diary_content_value);
            amount = itemView.findViewById(R.id.diary_content_amount);
            all = itemView.findViewById(R.id.diary_item_layout);
        }
    }

    //interface for click events
    public interface OnItemClickListner{
        public abstract void onItemClick(int position);
    }

    //expose click interface
    public void setOnItemClickListner(OnItemClickListner onItemClickListner){
        this.onItemClickListner = onItemClickListner;
    }




}
