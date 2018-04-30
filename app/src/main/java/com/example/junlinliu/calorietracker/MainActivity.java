package com.example.junlinliu.calorietracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.example.junlinliu.calorietracker.data.Diary;
import com.example.junlinliu.calorietracker.data.DiaryItem;
import com.example.junlinliu.calorietracker.data.UserData;
import com.example.junlinliu.calorietracker.utils.DateUtil;
import com.example.junlinliu.calorietracker.utils.ItemListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserData userData;
    private String currentDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userData = new UserData();
        currentDateString = DateUtil.dateToString(new Date());
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTableData();
        Diary diary = userData.getDiary(currentDateString);
        setListData(R.id.diary_breakfast_header, R.id.diary_breakfast_footer, R.id.diary_breakfast, diary.getBreakfastList(), diary.getBreakfastCalorie(), R.string.diary_food_breakfast, R.string.diary_add_food);
        setListData(R.id.diary_lunch_header, R.id.diary_lunch_footer, R.id.diary_lunch, diary.getLunchList(), diary.getLunchCalorie(), R.string.diary_food_lunch, R.string.diary_add_food);
        setListData(R.id.diary_dinner_header, R.id.diary_dinner_footer, R.id.diary_dinner, diary.getDinnerList(), diary.getDinnerCalorie(), R.string.diary_food_dinner, R.string.diary_add_food);
        setListData(R.id.diary_snack_header, R.id.diary_snack_footer, R.id.diary_snack, diary.getSnackList(), diary.getSnackCalorie(), R.string.diary_food_snacks, R.string.diary_add_food);
        setListData(R.id.diary_exercise_header, R.id.diary_exercise_footer, R.id.diary_exercise, diary.getExerciseList(), diary.getExerciseCalorie(), R.string.diary_exercise, R.string.diary_add_exercise);

    }

    private void setTableData() {
        Diary diary = userData.getDiary(currentDateString);
        ((TextView) findViewById(R.id.goal_number)).setText(String.format("%,d", userData.getGoal()));
        ((TextView) findViewById(R.id.food_number)).setText(String.format("%,d", diary.getGainedCalorie()));
        ((TextView) findViewById(R.id.exercise_number)).setText(String.format("%,d", diary.getBurnedCalorie()));
        ((TextView) findViewById(R.id.remaining_number)).setText(String.format("%,d", diary.getRemainingCalorie(userData.getGoal())));
    }

    private void setListData(int R_id_header, int R_id_footer, int R_id_item, final List<DiaryItem> itemList, int totalCalorie, final int R_string_item, int R_string_add_item) {
        View header = getLayoutInflater().inflate(R.layout.diary_tag_header, null);
        View footer = getLayoutInflater().inflate(R.layout.diary_tag_footer, null);
        ((TextView) header.findViewById(R.id.diary_tag_title)).setText(R_string_item);
        ((TextView) header.findViewById(R.id.diary_tag_value)).setText(Integer.toString(totalCalorie));
        ((TextView) footer.findViewById(R.id.diary_add_button)).setText(R_string_add_item);
        ((FrameLayout) findViewById(R_id_header)).addView(header);
        ((FrameLayout) findViewById(R_id_footer)).addView(footer);

        RecyclerView listViewItem = findViewById(R_id_item);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        listViewItem.setLayoutManager(layoutManager);
        ItemListAdapter itemListAdapter = new ItemListAdapter(new ArrayList<>(itemList));
        itemListAdapter.setOnItemClickListner(new ItemListAdapter.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                Intent intent;
//                switch (R_string_item) {
//                    case R.string.diary_food_breakfast:
//                        intent = new Intent(MainActivity.this, EditFoodActivity.class);
//                        intent.putExtra("position", position);
//                        intent.putExtra("Food", (Food) itemList.get(position));
//                        startActivityForResult(intent, REQUEST_ADD_BREAKFAST);
//                        break;
//                    case R.string.diary_food_lunch:
//                        intent = new Intent(getActivity(), EditFoodActivity.class);
//                        intent.putExtra("position", position);
//                        intent.putExtra("Food", (Food) itemList.get(position));
//                        startActivityForResult(intent, REQUEST_ADD_LUNCH);
//                        break;
//                    case R.string.diary_food_dinner:
//                        intent = new Intent(getActivity(), EditFoodActivity.class);
//                        intent.putExtra("position", position);
//                        intent.putExtra("Food", (Food) itemList.get(position));
//                        startActivityForResult(intent, REQUEST_ADD_DINNER);
//                        break;
//                    case R.string.diary_food_snacks:
//                        intent = new Intent(getActivity(), EditFoodActivity.class);
//                        intent.putExtra("position", position);
//                        intent.putExtra("Food", (Food) itemList.get(position));
//                        startActivityForResult(intent, REQUEST_ADD_SNACKS);
//                        break;
//                    case R.string.diary_exercise:
//                        intent = new Intent(getActivity(), EditExerciseActivity.class);
//                        intent.putExtra("position", position);
//                        intent.putExtra("Exercise", (Exercise) itemList.get(position));
//                        startActivityForResult(intent, REQUEST_ADD_EXERCISE);
//                        break;
//                }
            }
        });
        listViewItem.setAdapter(itemListAdapter);


        //set footer (add button)
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                switch (R_string_item) {
//                    case R.string.diary_food_breakfast:
//                        startActivityForResult(new Intent(getActivity(), SearchFoodActivity.class), REQUEST_ADD_BREAKFAST);
//                        break;
//                    case R.string.diary_food_lunch:
//                        startActivityForResult(new Intent(getActivity(), SearchFoodActivity.class), REQUEST_ADD_LUNCH);
//                        break;
//                    case R.string.diary_food_dinner:
//                        startActivityForResult(new Intent(getActivity(), SearchFoodActivity.class), REQUEST_ADD_DINNER);
//                        break;
//                    case R.string.diary_food_snacks:
//                        startActivityForResult(new Intent(getActivity(), SearchFoodActivity.class), REQUEST_ADD_SNACKS);
//                        break;
//                    case R.string.diary_exercise:
//                        startActivityForResult(new Intent(getActivity(), AddExerciseActivity.class), REQUEST_ADD_EXERCISE);
//                        break;
//                }
            }
        });
    }
}
