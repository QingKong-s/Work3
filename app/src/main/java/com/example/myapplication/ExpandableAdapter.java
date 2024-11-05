package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpandableAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int VIEW_TYPE_MAIN = 0;
    private static final int VIEW_TYPE_SUB = 1;

    private List<ActionItem> items;

    public ExpandableAdapter(List<ActionItem> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        int count = 0;
        for (ActionItem item : items) {
            if (position == count) return VIEW_TYPE_MAIN;
            count++;
            if (item.isExpanded()) {
                if (position < count + item.getSubItems().size()) return VIEW_TYPE_SUB;
                count += item.getSubItems().size();
            }
        }
        throw new IllegalStateException("Invalid position");
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_MAIN) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
            return new MainViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sub, parent, false);
            return new SubViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int count = 0;
        for (ActionItem item : items) {
            if (position == count) {
                ((MainViewHolder) holder).bind(item);
                return;
            }
            count++;
            if (item.isExpanded()) {
                int i = 0;
                for (SubItem subItem : item.getSubItems()) {
                    ++i;
                    if (position == count) {
                        ((SubViewHolder) holder).bind(subItem);
                        ((SubViewHolder) holder).initNumber(i);
                        return;
                    }
                    count++;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (ActionItem item : items) {
            count++;
            if (item.isExpanded()) {
                count += item.getSubItems().size();
            }
        }
        return count;
    }

    class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView sets;

        public MainViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            sets = itemView.findViewById(R.id.sets);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                ActionItem item = getItemAtPosition(position);
                if (item != null) {
                    item.setExpanded(!item.isExpanded());
                    notifyDataSetChanged();
                }
            });
        }

        public void bind(ActionItem item) {
            title.setText(item.getTitle());
            sets.setText(item.getSets() + " sets");
        }
    }

    class SubViewHolder extends RecyclerView.ViewHolder {
        private EditText weight;
        private EditText repetitions;
        private TextView no;

        public SubViewHolder(View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            weight = itemView.findViewById(R.id.weight);
            repetitions = itemView.findViewById(R.id.repetitions);
        }

        public void bind(SubItem subItem) {
            weight.setText(subItem.getWeight() + " kg");
            repetitions.setText(subItem.getRepetitions() + " reps");
        }

        public void initNumber(int i) {
            no.setText(Integer.toString(i));
        }
    }

    private ActionItem getItemAtPosition(int position) {
        int count = 0;
        for (ActionItem item : items) {
            if (position == count) return item;
            count++;
            if (item.isExpanded()) {
                if (position < count + item.getSubItems().size()) return null;
                count += item.getSubItems().size();
            }
        }
        return null;
    }
}
