package com.example.myapplication;

public class SubItem {
    private String weight;
    private String repetitions;
    private boolean isCompleted;

    public SubItem(String weight, String repetitions, boolean isCompleted) {
        this.weight = weight;
        this.repetitions = repetitions;
        this.isCompleted = isCompleted;
    }

    public String getWeight() { return weight; }
    public String getRepetitions() { return repetitions; }
    public boolean isCompleted() { return isCompleted; }
}