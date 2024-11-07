package com.example.myapplication;

public class SubItem {
    private int weight;
    private int repetitions;
    private boolean isCompleted;

    public SubItem() {}

    public SubItem(int weight, int repetitions, boolean isCompleted) {
        this.weight = weight;
        this.repetitions = repetitions;
        this.isCompleted = isCompleted;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }
}