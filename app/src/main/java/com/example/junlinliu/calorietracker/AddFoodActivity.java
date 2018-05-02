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

import com.example.junlinliu.calorietracker.data.DiaryItem;

public class AddFoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        // process data passed in
        DiaryItem food = (DiaryItem) getIntent().getSerializableExtra("Food");
        if (food != null){
            ((EditText) findViewById(R.id.diary_content_title_edit)).setText(food.getTitle());
            ((EditText) findViewById(R.id.diary_content_calory_per_unit_edit)).setText(Integer.toString(food.getCaloriePerUnit()));
            ((EditText) findViewById(R.id.diary_content_amount_edit)).setText(Double.toString(food.getUnitNumber()));
            ((EditText) findViewById(R.id.diary_content_unit_name_edit)).setText(food.getUnitName());
        }

        Toolbar toolbar = findViewById(R.id.toolbar_add_food);
        toolbar.inflateMenu(R.menu.check_menu);
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

                String titleText = ((EditText) findViewById(R.id.diary_content_title_edit)).getText().toString();
                String calPerUnitText = ((EditText) findViewById(R.id.diary_content_calory_per_unit_edit)).getText().toString();
                String amountText = ((EditText) findViewById(R.id.diary_content_amount_edit)).getText().toString();
                String unitNameText = ((EditText) findViewById(R.id.diary_content_unit_name_edit)).getText().toString();
                if (titleText.equals("") || calPerUnitText.equals("") || amountText.equals("") || unitNameText.equals("")) {
                    showErrorDialog();
                } else {
                    int caloryPerUnit = (int) Double.parseDouble(calPerUnitText);
                    double unitNumber = Double.parseDouble(amountText);
                    DiaryItem food = new DiaryItem(titleText, caloryPerUnit, unitNumber, unitNameText);
                    Intent intent = new Intent();
                    intent.putExtra("Food", food);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
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

}
