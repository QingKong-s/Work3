package com.example.myapplication;

public class Action {
    private int actionId;    // 动作编号
    private int weight;    // 动作重量
    private int count;        // 动作数量

    // 构造方法
    public Action(int actionId, int weight, int count) {
        this.actionId = actionId;
        this.weight = weight;
        this.count = count;
    }

    // Getter 方法
    public int getActionId() {
        return actionId;
    }

    public int getWeight() {
        return weight;
    }

    public int getCount() {
        return count;
    }

    // 重写 toString 方法
    @Override
    public String toString() {
        return "Action{id=" + actionId + ", weight=" + weight + ", count=" + count + "}";
    }
}
