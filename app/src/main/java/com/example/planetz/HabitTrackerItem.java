package com.example.planetz;

import java.util.Objects;

public class HabitTrackerItem {

    int progress;
    String habitName;
    int days;
    int cycle;

    HabitTrackerItem(){
    }

    public HabitTrackerItem(int progress, String habitName, int days, int cycle) {
        this.progress = progress;
        this.habitName = habitName;
        this.days = days;
    }


    public int getProgress() {
        return progress;
    }

    public String getHabitName() {
        return habitName;
    }

    public int getDays() {
        return days;
    }

    public String getDaysString() {
        return days + " days";
    }

    public int getCycle(){
        return cycle;
    }

    void logHabit(){
        if(progress < 100 && progress >= 0){
            progress++;
        }
        else if (progress == 100){
            cycle++;
            progress = 0;
        }
        days++;
    }

    void unLogHabit(){
        if(progress < 100 && progress > 0){
            progress--;
            days--;

        }
        else if(progress == 0 && cycle == 0){
           return;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HabitTrackerItem habitTrackerItem = (HabitTrackerItem) o;
        return habitName.equals(habitTrackerItem.habitName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(habitName);
    }

}

