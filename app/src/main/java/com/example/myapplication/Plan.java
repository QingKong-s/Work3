package com.example.myapplication;

import java.util.ArrayList;

public class Plan {
    private ArrayList<ActionItem> actions;
    private String name;

    public Plan(ArrayList<ActionItem> actions,String name) {
        this.actions = actions;
        this.name = name;
    }

    ArrayList<ActionItem> getActions() { return actions; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
