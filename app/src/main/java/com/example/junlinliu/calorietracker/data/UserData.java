package com.example.junlinliu.calorietracker.data;

import com.example.junlinliu.calorietracker.utils.DateUtil;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserData implements Serializable{
    private int goal;
    private Map<String, Diary> stringDiaryHashMap;

    public UserData() {
        goal = 2000;
        stringDiaryHashMap = new HashMap<>();
    }

    public Diary getDiary(String dateString){
        Diary diary;
        if (stringDiaryHashMap.containsKey(dateString)){
            diary =  stringDiaryHashMap.get(dateString);
        } else {
            diary = new Diary(dateString);
            stringDiaryHashMap.put(dateString, diary);
        }
        return diary;
    }

    public int getGoal() {
        return goal;
    }

    public void setGoal(int goal) {
        this.goal = goal;
    }
}
