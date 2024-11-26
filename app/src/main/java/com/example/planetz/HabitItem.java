package com.example.planetz;

public class HabitItem {
    String category;
    String habit;
    int img;
    String impact;

    public HabitItem(String category, String habit, int img, String impact){
        this.category = category;
        this.habit = habit;
        this.img = img;
        this.impact = impact;
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
