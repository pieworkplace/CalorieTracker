package com.pieworkplace.junlinliu.calorietracker;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.pieworkplace.junlinliu.calorietracker.data.Diary;
import com.pieworkplace.junlinliu.calorietracker.data.DiaryItem;
import com.pieworkplace.junlinliu.calorietracker.data.UserData;
import com.pieworkplace.junlinliu.calorietracker.utils.DateUtil;
import com.pieworkplace.junlinliu.calorietracker.utils.InternalStorageService;
import com.pieworkplace.junlinliu.calorietracker.utils.ItemListAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static UserData userData;
    private static String currentDateString;

    public static final int REQUEST_ADD_BREAKFAST = 11;
    public static final int REQUEST_ADD_LUNCH = 12;
    public static final int REQUEST_ADD_DINNER = 13;
    public static final int REQUEST_ADD_SNACKS = 14;
    public static final int REQUEST_ADD_EXERCISE = 15;

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userData = InternalStorageService.loadFromStorage(MainActivity.this);
        currentDateString = DateUtil.millisecondTimeToString(System.currentTimeMillis());
        setToolbar();
        setDatePicker();
    }

    private void setToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_settings_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInputDialog();
                setAllDataInMain();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setAllDataInMain();
    }

    private void setDatePicker() {
        final TextView textView = findViewById(R.id.main_toolbar_title);
        textView.setText(currentDateString);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = DateUtil.stringToDate(currentDateString);
                Dialog dialog = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        currentDateString = DateUtil.dateToString(new Date(year - 1900, monthOfYear, dayOfMonth));
                        textView.setText(currentDateString);
                        setAllDataInMain();
                    }
                }, date.getYear() + 1900, date.getMonth(), date.getDate());
                dialog.show();
            }
        });
    }

    private void setAllDataInMain(){
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
                switch (R_string_item) {
                    case R.string.diary_food_breakfast:
                        intent = new Intent(MainActivity.this, EditFoodActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DiaryItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "breakfast");
                        startActivityForResult(intent, REQUEST_ADD_BREAKFAST);
                        break;
                    case R.string.diary_food_lunch:
                        intent = new Intent(MainActivity.this, EditFoodActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DiaryItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "lunch");
                        startActivityForResult(intent, REQUEST_ADD_LUNCH);
                        break;
                    case R.string.diary_food_dinner:
                        intent = new Intent(MainActivity.this, EditFoodActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DiaryItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "dinner");
                        startActivityForResult(intent, REQUEST_ADD_DINNER);
                        break;
                    case R.string.diary_food_snacks:
                        intent = new Intent(MainActivity.this, EditFoodActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Food", (DiaryItem) itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "snack");
                        startActivityForResult(intent, REQUEST_ADD_SNACKS);
                        break;
                    case R.string.diary_exercise:
                        intent = new Intent(MainActivity.this, EditExerciseActivity.class);
                        intent.putExtra("position", position);
                        intent.putExtra("Exercise", itemList.get(position));
                        intent.putExtra("currentDateString", currentDateString);
                        startActivity(intent);
//                        startActivityForResult(intent, REQUEST_ADD_EXERCISE);
                        break;
                }
            }
        });
        listViewItem.setAdapter(itemListAdapter);


        //set footer (add button)
        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                switch (R_string_item) {
                    case R.string.diary_food_breakfast:
                        intent = new Intent(MainActivity.this, SearchFoodActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "breakfast");
                        startActivity(intent);
                        break;
                    case R.string.diary_food_lunch:
                        intent = new Intent(MainActivity.this, SearchFoodActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "lunch");
                        startActivity(intent);
                        break;
                    case R.string.diary_food_dinner:
                        intent = new Intent(MainActivity.this, SearchFoodActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "dinner");
                        startActivity(intent);
                        break;
                    case R.string.diary_food_snacks:
                        intent = new Intent(MainActivity.this, SearchFoodActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        intent.putExtra("whichMeal", "snack");
                        startActivity(intent);
                        break;
                    case R.string.diary_exercise:
                        intent = new Intent(MainActivity.this, AddExerciseActivity.class);
                        intent.putExtra("currentDateString", currentDateString);
                        startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        InternalStorageService.writeToStorage(MainActivity.this, userData);
    }

    private void showInputDialog() {
        View view = getLayoutInflater().inflate(R.layout.edittext_for_dialog, null);
        final EditText editText = view.findViewById(R.id.edittext_in_dialog_set_goal);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.set_goal_hint);
        builder.setView(editText);
//        editText.setText(userData.getGoal());
        builder.setPositiveButton(R.string.dialog_positive_button_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editText.getText().toString().equals("")){
                    return;
                }
                userData.setGoal(Integer.parseInt(editText.getText().toString()));
                setAllDataInMain();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public static UserData getUserData() {
        return userData;
    }

}
