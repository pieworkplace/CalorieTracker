package com.pieworkplace.junlinliu.calorietracker.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class UserData implements Serializable{
    private int goal;
    private Map<String, Diary> stringDiaryHashMap;

    public UserData() {
        goal = 0;
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
