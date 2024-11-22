package com.example.planetz;

public class HabitItem {
    String category;
    String habit;
    int img;

    public HabitItem(String category, String habit, int img){
        this.category = category;
        this.habit = habit;
        this.img = img;
    }

    public String getCategory() {
        return category;
    }

    public String getHabit() {
        return habit;
    }

    public int getImg() {
        return img;
    }

}
