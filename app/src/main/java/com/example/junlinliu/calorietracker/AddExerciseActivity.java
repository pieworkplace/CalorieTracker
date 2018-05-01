package com.example.junlinliu.calorietracker;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.junlinliu.calorietracker.data.DiaryItem;

public class AddExerciseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_exercise);
        Toolbar toolbar = findViewById(R.id.toolbar_add_exercise);
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

                String title = ((EditText) findViewById(R.id.diary_exercise_title_edit)).getText().toString();
                String calPerUnitText = ((EditText) findViewById(R.id.diary_exercise_calorie_per_unit_edit)).getText().toString();
                String unitNumberText = ((EditText) findViewById(R.id.diary_exercise_amount_edit)).getText().toString();
                String unitName = ((EditText) findViewById(R.id.diary_exercise_unit_name_edit)).getText().toString();
                if (title.equals("") || calPerUnitText.equals("") || unitNumberText.equals("") || unitName.equals("")) {
                    showErrorDialog();
                }
                else{
                    int caloriePerUnit = (int) Double.parseDouble(calPerUnitText);
                    double unitNumber = Double.parseDouble(unitNumberText);

                    DiaryItem exercise = new DiaryItem(title, caloriePerUnit, unitNumber, unitName);
                    String dateString = getIntent().getStringExtra("currentDateString");
                    MainActivity.getUserData().getDiary(dateString).addExerciseList(exercise);
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
