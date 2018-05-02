package com.example.junlinliu.calorietracker;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.junlinliu.calorietracker.data.Diary;
import com.example.junlinliu.calorietracker.data.DiaryItem;

import static com.example.junlinliu.calorietracker.EditExerciseActivity.DELETE_RESULT_OK;

public class EditFoodActivity extends AppCompatActivity {

    public static final int EDIT_RESULT_OK = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_food);

        // process data passed in
        DiaryItem food = (DiaryItem) getIntent().getSerializableExtra("Food");
        final int position = getIntent().getIntExtra("position", -1);
        if (food != null){
            ((EditText) findViewById(R.id.diary_content_edit_title_edit)).setText(food.getTitle());
            ((EditText) findViewById(R.id.diary_content_edit_calory_per_unit_edit)).setText(Integer.toString(food.getCaloriePerUnit()));
            ((EditText) findViewById(R.id.diary_content_edit_amount_edit)).setText(Double.toString(food.getUnitNumber()));
            ((EditText) findViewById(R.id.diary_content_edit_unit_name_edit)).setText(food.getUnitName());
        }

        //toolbar related
        Toolbar toolbar = findViewById(R.id.toolbar_edit_food);
        toolbar.inflateMenu(R.menu.delete_check_menu);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.toolbar_edit_delete:
                        showDeleteAlertDialog(position);
                        break;
                    case R.id.toolbar_edit_done:
                        String titleText = ((EditText) findViewById(R.id.diary_content_edit_title_edit)).getText().toString();
                        String calPerUnitText = ((EditText) findViewById(R.id.diary_content_edit_calory_per_unit_edit)).getText().toString();
                        String amountText = ((EditText) findViewById(R.id.diary_content_edit_amount_edit)).getText().toString();
                        String unitNameText = ((EditText) findViewById(R.id.diary_content_edit_unit_name_edit)).getText().toString();
                        if (titleText.equals("") || calPerUnitText.equals("") || amountText.equals("") || unitNameText.equals("")) {
                            showErrorDialog();
                        } else {
                            int caloryPerUnit = (int) Double.parseDouble(calPerUnitText);
                            double unitNumber = Double.parseDouble(amountText);
                            DiaryItem food = new DiaryItem(titleText, caloryPerUnit, unitNumber, unitNameText);
                            intent = new Intent();
//                            intent.putExtra("Food", food);
//                            intent.putExtra("position", position);
                            String currentDateString = getIntent().getStringExtra("currentDateString");
                            String meal = getIntent().getStringExtra("whichMeal");
                            Diary diary = MainActivity.getUserData().getDiary(currentDateString);
                            switch (meal){
                                case "breakfast":
                                    diary.editBreakfastList(position, food);
                                    break;
                                case "lunch":
                                    diary.editLunchList(position, food);
                                    break;
                                case "dinner":
                                    diary.editDinnerList(position, food);
                                    break;
                                case "snack":
                                    diary.editSnackList(position, food);
                                    break;
                            }
                            setResult(EDIT_RESULT_OK, intent);
                            finish();
                        }
                        return true;
                }
                return false;
            }
        });
    }

    private void showErrorDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.general_alert));
        alertDialog.setMessage(getString(R.string.required_item_empty_error));
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.dismiss), (DialogInterface.OnClickListener) null);
        alertDialog.show();
    }

    private void showDeleteAlertDialog(final int position){
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(getString(R.string.general_alert));
        alertDialog.setMessage(getString(R.string.delete_item_alert));
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), (DialogInterface.OnClickListener) null);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent();
//                intent.putExtra("position", position);
                String currentDateString = getIntent().getStringExtra("currentDateString");
                String meal = getIntent().getStringExtra("whichMeal");
                Diary diary = MainActivity.getUserData().getDiary(currentDateString);
                switch (meal){
                    case "breakfast":
                        diary.removeBreakfastList(position);
                        break;
                    case "lunch":
                        diary.removeLunchList(position);
                        break;
                    case "dinner":
                        diary.removeDinnerList(position);
                        break;
                    case "snack":
                        diary.removeSnackList(position);
                        break;
                }
                setResult(DELETE_RESULT_OK, intent);
                finish();
            }
        });
        alertDialog.show();
    }

}
