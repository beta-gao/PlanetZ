package com.example.planetz;

public class HabitTrackerItem {

    int progress;
    String habitName;
    int days;
    int cycle;

    public HabitTrackerItem(int progress, String habitName, int days) {
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

    public String getDays() {
        return days + "days";
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
        }
        else if(progress == 0 && cycle == 0){
            TrackingHabit.habitTrackerList.remove(this);
        }
        days--;
    }


}

