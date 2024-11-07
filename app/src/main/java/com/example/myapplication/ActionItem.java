package com.example.myapplication;

import java.util.List;

public class ActionItem {
    private String title;
    private int sets;
    private boolean isExpanded;
    private List<SubItem> subItems;

    public ActionItem() {}

    public ActionItem(String title, int sets, List<SubItem> subItems) {
        this.title = title;
        this.sets = sets;
        this.subItems = subItems;
        this.isExpanded = false;
    }

    public boolean isExpanded() { return isExpanded; }
    public void setExpanded(boolean expanded) { isExpanded = expanded; }
    public List<SubItem> getSubItems() { return subItems; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public int getSets() { return sets; }
    public void setSets(int sets) { this.sets = sets; }
}