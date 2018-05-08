package com.pieworkplace.junlinliu.calorietracker.utils;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pieworkplace.junlinliu.calorietracker.AddFoodActivity;
import com.pieworkplace.junlinliu.calorietracker.R;
import com.pieworkplace.junlinliu.calorietracker.SearchFoodActivity;
import com.pieworkplace.junlinliu.calorietracker.data.DiaryItem;

import java.util.List;

import static com.pieworkplace.junlinliu.calorietracker.SearchFoodActivity.CREATE_FOOD_IN_SEARCH;

/**
 * Created by Liu Junlin on 2018/3/16.
 */

public class SearchFoodListAdapter extends BaseAdapter {
    private List<DiaryItem> foodList;
    private Context context;

    public SearchFoodListAdapter(List<DiaryItem> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return foodList.size();
    }

    @Override
    public Object getItem(int i) {
        return foodList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = ((LayoutInflater)(context.getSystemService(Context.LAYOUT_INFLATER_SERVICE))).inflate(R.layout.diary_content, null);
        }
        ((TextView) view.findViewById(R.id.diary_content_title)).setText(foodList.get(i).getTitle());
        ((TextView) view.findViewById(R.id.diary_content_value)).setText(Integer.toString(foodList.get(i).getCaloriePerUnit()));
        ((TextView) view.findViewById(R.id.diary_content_amount)).setText(Double.toString(foodList.get(i).getUnitNumber()) + " " + foodList.get(i).getUnitName());
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AddFoodActivity.class);
                intent.putExtra("Food", foodList.get(i));
                ((SearchFoodActivity)context).startActivityForResult(intent, CREATE_FOOD_IN_SEARCH);
            }
        });
        return view;
    }
}
