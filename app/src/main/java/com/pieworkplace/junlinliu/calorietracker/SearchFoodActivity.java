package com.pieworkplace.junlinliu.calorietracker;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.pieworkplace.junlinliu.calorietracker.data.Diary;
import com.pieworkplace.junlinliu.calorietracker.data.DiaryItem;
import com.pieworkplace.junlinliu.calorietracker.utils.SearchFoodListAdapter;
import com.pieworkplace.junlinliu.calorietracker.utils.SearchFoodResults;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

public class SearchFoodActivity extends AppCompatActivity {

    public static final int CREATE_FOOD_IN_SEARCH = 21;
    public static final int CONNECT_TIMEOUT = 5000;
    private static final int CONNECT_ERROR = -1;

    String meal;
    String currentDateString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_food);
        meal = getIntent().getStringExtra("whichMeal");
        currentDateString = getIntent().getStringExtra("currentDateString");
        Toolbar toolbar = findViewById(R.id.toolbar_search_food);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        LinearLayout linearLayout = findViewById(R.id.search_food_result_from_internet);
        View createFoodButton = getLayoutInflater().inflate(R.layout.search_food_create_food_button, null);
        linearLayout.addView(createFoodButton);

        (createFoodButton.findViewById(R.id.create_food_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(SearchFoodActivity.this, AddFoodActivity.class), CREATE_FOOD_IN_SEARCH);
            }
        });

        SearchView searchView = findViewById(R.id.search_food_search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                FoodSearchAsyncTask foodSearchAsyncTask = new FoodSearchAsyncTask();
                foodSearchAsyncTask.execute(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        EditText textView = searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        textView.setHintTextColor(ContextCompat.getColor(SearchFoodActivity.this, R.color.colorPrimary));
        textView.setTextColor(ContextCompat.getColor(SearchFoodActivity.this, R.color.colorLightText));

        ImageView icon = searchView.findViewById(android.support.v7.appcompat.R.id.search_mag_icon);
//        icon.setAdjustViewBounds(true);
//        icon.setMaxWidth(0);
//        icon.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        icon.setImageDrawable(null);
        icon.setColorFilter(SearchFoodActivity.this.getResources().getColor(R.color.colorPrimary));


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CREATE_FOOD_IN_SEARCH && resultCode == RESULT_OK) {
            DiaryItem food = (DiaryItem) data.getSerializableExtra("Food");
            Diary diary = MainActivity.getUserData().getDiary(currentDateString);
            switch (meal){
                case "breakfast":
                    diary.addBreakfastList(food);
                    break;
                case "lunch":
                    diary.addLunchList(food);
                    break;
                case "dinner":
                    diary.addDinnerList(food);
                    break;
                case "snack":
                    diary.addSnackList(food);
                    break;
            }
            finish();
        }
    }

    private class FoodSearchAsyncTask extends AsyncTask<String, Integer, List<DiaryItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(SearchFoodActivity.this, "This is my Toast message!", Toast.LENGTH_LONG).show();
            LinearLayout linearLayout = findViewById(R.id.search_food_result_from_internet);
            linearLayout.removeAllViews();
            linearLayout.addView(getLayoutInflater().inflate(R.layout.progress_circle, null));
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values[0] == CONNECT_ERROR){
                Toast.makeText(SearchFoodActivity.this, R.string.internet_connection_error, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected List<DiaryItem> doInBackground(String... strings) {
            String appId = "1f011b62";
            String appKey = "438c2741ef39e1a9ef25eb0f1787fa8e";
            String initialPart = "https://api.nutritionix.com/v1_1/search/";
            String query = strings[0].replace(" ", "%20");//kong ge
            String resultsNum = "?results=0:20&";
            String fields = "fields=item_name,brand_name,nf_calories,nf_serving_size_qty,nf_serving_size_unit&";
            String idAndKey = "appId=" + appId + "&appKey=" + appKey;
            String URLString = initialPart + query + resultsNum + fields + idAndKey;

            try {
                URL url = new URL(URLString);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
                httpsURLConnection.setRequestMethod("GET");
                httpsURLConnection.connect();

                int responseCode = httpsURLConnection.getResponseCode();
                if (responseCode == HttpsURLConnection.HTTP_OK){
                    InputStreamReader inputStreamReader = new InputStreamReader(httpsURLConnection.getInputStream());
                    BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null){
                        stringBuilder.append(line);
                    }
                    String resultString = stringBuilder.toString();
                    Gson gson = new Gson();
                    SearchFoodResults results = gson.fromJson(resultString, SearchFoodResults.class);
                    List<DiaryItem> foodList = new ArrayList<>();
                    for (SearchFoodResults.SearchFoodHits hit :results.hits){
                        SearchFoodResults.SearchFoodHits.SearchFoodItem item = hit.fields;
                        foodList.add(new DiaryItem(item.item_name + ", " + item.brand_name, (int) item.nf_calories, item.nf_serving_size_qty, item.nf_serving_size_unit));
                    }
                    return foodList;

                }else{
                    publishProgress(CONNECT_ERROR);
                }

            } catch (MalformedURLException e) {
                publishProgress(CONNECT_ERROR);
                e.printStackTrace();

            } catch (IOException e) {
                publishProgress(CONNECT_ERROR);
                e.printStackTrace();
            }

            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(List<DiaryItem> foods) {
            super.onPostExecute(foods);
            LinearLayout linearLayout = findViewById(R.id.search_food_result_from_internet);
            linearLayout.removeAllViews();
            // hide keyboard
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

            View createFoodButton;

            if (foods.isEmpty()) {
                linearLayout.addView(getLayoutInflater().inflate(R.layout.no_result_error, null));
                createFoodButton = getLayoutInflater().inflate(R.layout.search_food_create_food_button, null);
                linearLayout.addView(createFoodButton);
            } else {
                linearLayout.addView(getLayoutInflater().inflate(R.layout.search_food_result, null));
                ListView listView = findViewById(R.id.search_food_result_list);
                listView.setAdapter(new SearchFoodListAdapter(foods, SearchFoodActivity.this));
                createFoodButton = getLayoutInflater().inflate(R.layout.search_food_create_food_button, null);
                listView.addFooterView(createFoodButton);
                listView.setDivider(null);//去除listview的下划线
            }
            (createFoodButton.findViewById(R.id.create_food_button)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SearchFoodActivity.this, AddFoodActivity.class);
                    startActivityForResult(intent, CREATE_FOOD_IN_SEARCH);
                }
            });
        }
    }
}
