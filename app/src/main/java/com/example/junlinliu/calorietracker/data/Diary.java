package com.example.junlinliu.calorietracker.data;

import com.example.junlinliu.calorietracker.utils.DateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Diary implements Serializable{
    private String date;
    private List<DiaryItem> breakfastList;
    private List<DiaryItem> lunchList;
    private List<DiaryItem> dinnerList;
    private List<DiaryItem> snackList;
    private List<DiaryItem> exerciseList;

    public Diary(String date) {
        this.date = date;
        breakfastList = new ArrayList<>();
        lunchList = new ArrayList<>();
        dinnerList = new ArrayList<>();
        snackList = new ArrayList<>();
        exerciseList = new ArrayList<>();
    }

    public int getBreakfastCalorie(){
        int res = 0;
        for (DiaryItem foodItem:breakfastList){
            res += foodItem.getTotalCalorie();
        }
        return res;
    }

    public int getLunchCalorie(){
        int res = 0;
        for (DiaryItem foodItem:lunchList){
            res += foodItem.getTotalCalorie();
        }
        return res;
    }

    public int getDinnerCalorie(){
        int res = 0;
        for (DiaryItem foodItem:dinnerList){
            res += foodItem.getTotalCalorie();
        }
        return res;
    }

    public int getSnackCalorie(){
        int res = 0;
        for (DiaryItem foodItem:snackList){
            res += foodItem.getTotalCalorie();
        }
        return res;
    }

    public int getExerciseCalorie(){
        int res = 0;
        for (DiaryItem exerciseItem:exerciseList){
            res += exerciseItem.getTotalCalorie();
        }
        return res;
    }

    public int getGainedCalorie(){
        return getBreakfastCalorie() + getDinnerCalorie() + getLunchCalorie() + getSnackCalorie();
    }

    public int getBurnedCalorie(){
        return getExerciseCalorie();
    }

    public int getRemainingCalorie(int goal){
        return goal - getGainedCalorie() + getBurnedCalorie();
    }

    public List<DiaryItem> getBreakfastList() {
        return breakfastList;
    }

    public void setBreakfastList(List<DiaryItem> breakfastList) {
        this.breakfastList = breakfastList;
    }

    public List<DiaryItem> getLunchList() {
        return lunchList;
    }

    public void setLunchList(List<DiaryItem> lunchList) {
        this.lunchList = lunchList;
    }

    public List<DiaryItem> getDinnerList() {
        return dinnerList;
    }

    public void setDinnerList(List<DiaryItem> dinnerList) {
        this.dinnerList = dinnerList;
    }

    public List<DiaryItem> getSnackList() {
        return snackList;
    }

    public void setSnackList(List<DiaryItem> snackList) {
        this.snackList = snackList;
    }

    public List<DiaryItem> getExerciseList() {
        return exerciseList;
    }

    public void setExerciseList(List<DiaryItem> exerciseList) {
        this.exerciseList = exerciseList;
    }

    public void addBreakfastList(DiaryItem food) {
        breakfastList.add(food);
    }

    public void addLunchList(DiaryItem food) {
        lunchList.add(food);
    }

    public void addDinnerList(DiaryItem food) {
        dinnerList.add(food);
    }

    public void addSnackList(DiaryItem food) {
        snackList.add(food);
    }

    public void addExerciseList(DiaryItem exercise){
        exerciseList.add(exercise);
    }

    public void removeBreakfastList(int position){
        if (position < breakfastList.size() && position >= 0){
            breakfastList.remove(position);
        }
    }

    public void removeLunchList(int position){
        if (position < lunchList.size() && position >= 0){
            lunchList.remove(position);
        }
    }

    public void removeDinnerList(int position){
        if (position < dinnerList.size() && position >= 0){
            dinnerList.remove(position);
        }
    }

    public void removeSnackList(int position){
        if (position < snackList.size() && position >= 0){
            snackList.remove(position);
        }
    }

    public void removeExerciseList(int position){
        if (position < exerciseList.size() && position >= 0){
            exerciseList.remove(position);
        }
    }

    public void editBreakfastList(int position, DiaryItem food){
        if (position < breakfastList.size() && position >= 0){
            breakfastList.set(position, food);
        }
    }

    public void editLunchList(int position, DiaryItem food){
        if (position < lunchList.size() && position >= 0){
            lunchList.set(position, food);
        }
    }

    public void editDinnerList(int position, DiaryItem food){
        if (position < dinnerList.size() && position >= 0){
            dinnerList.set(position, food);
        }
    }

    public void editSnackList(int position, DiaryItem food){
        if (position < snackList.size() && position >= 0){
            snackList.set(position, food);
        }
    }

    public void editExerciseList(int position, DiaryItem exercise){
        if (position < exerciseList.size() && position >= 0){
            exerciseList.set(position, exercise);
        }
    }

}
