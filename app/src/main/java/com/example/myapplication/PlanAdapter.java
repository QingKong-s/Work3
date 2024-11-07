package com.example.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.ViewHolder> {

    private final List<Plan> data;
    private final Context context;

    public PlanAdapter(Context context, List<Plan> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String itemText = data.get(position).getName();
        holder.textView.setText(itemText);

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlanActivity.class);
            intent.putExtra("item_index", position);
            context.startActivity(intent);
        });

        holder.itemView.setOnLongClickListener(v -> {
            showEditDialog(position);
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void showEditDialog(int position) {
        Plan plan = data.get(position);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("编辑计划");

        final EditText input = new EditText(context);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        input.setText(plan.getName());
        builder.setView(input);

        builder.setPositiveButton("确定", (dialog, which) -> {
            String newTitle = input.getText().toString();
            if (!newTitle.isEmpty()) {
                plan.setName(newTitle);
                notifyItemChanged(position);
            }
        });

        builder.setNeutralButton("删除", (dialog, which) -> {
            data.remove(position);
            notifyItemRemoved(position);
        });

        builder.setNegativeButton("取消", (dialog, which) -> dialog.cancel());

        builder.show();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.item_text);
        }
    }
}
