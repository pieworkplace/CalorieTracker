package com.example.junlinliu.calorietracker.utils;



import java.util.List;

/**
 * Created by Liu Junlin on 2018/3/16.
 */

public class SearchFoodResults {
    public int total_hits;
    public double max_score;
    public List<SearchFoodHits> hits;


    public class SearchFoodHits {
        public String _index;
        public String _type;
        public String _id;
        public double _score;
        public SearchFoodItem fields;


        public class SearchFoodItem {
            public String item_name;
            public String brand_name;
            public double nf_calories;
            public double nf_serving_size_qty;
            public String nf_serving_size_unit;

        }
    }
}
