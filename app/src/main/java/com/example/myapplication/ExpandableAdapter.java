package com.example.myapplication;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
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
                    if (position == count) {
                        ((SubViewHolder)holder).init(i, item);
                        ((SubViewHolder)holder).bind(subItem);
                        return;
                    }
                    ++count;
                    ++i;
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

            itemView.findViewById(R.id.addSub).setOnClickListener(v -> {
                int position = getAdapterPosition();
                ActionItem item = getItemAtPosition(position);
                if (item != null) {
                    List<SubItem> sub = item.getSubItems();
                    if (sub != null) {
                        sub.add(new SubItem(10, 1, false));
                        notifyDataSetChanged();
                    }
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
        private Button complete;
        private int idx;
        private ActionItem actionItem;

        public SubViewHolder(View itemView) {
            super(itemView);
            no = itemView.findViewById(R.id.no);
            weight = itemView.findViewById(R.id.weight);
            repetitions = itemView.findViewById(R.id.repetitions);
            complete = itemView.findViewById(R.id.complete);

            complete.setOnClickListener(v -> {
                SubItem sub = actionItem.getSubItems().get(idx);
                sub.setCompleted(!sub.isCompleted());
                updateButtonText();
            });

            weight.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    if (actionItem != null) {
                        List<SubItem> sub = actionItem.getSubItems();
                        if (sub != null) {
                            weight.setText(sub.get(idx).getWeight() + " kg");
                        }
                    }
                }
            });

            repetitions.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    if (actionItem != null) {
                        List<SubItem> sub = actionItem.getSubItems();
                        if (sub != null) {
                            repetitions.setText(sub.get(idx).getWeight() + " reps");
                        }
                    }
                }
            });

            weight.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (actionItem != null) {
                        List<SubItem> sub = actionItem.getSubItems();
                        if (sub != null) {
                            Integer i = Utils.convertPrefixToInt(s.toString());
                            sub.get(idx).setWeight(i);
                        }
                    }
                }
            });

            repetitions.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {}
                @Override
                public void afterTextChanged(Editable s) {
                    if (actionItem != null) {
                        List<SubItem> sub = actionItem.getSubItems();
                        if (sub != null) {
                            Integer i = Utils.convertPrefixToInt(s.toString());
                            sub.get(idx).setRepetitions(i);
                        }
                    }
                }
            });
        }

        public void bind(SubItem subItem) {
            weight.setText(subItem.getWeight() + " kg");
            repetitions.setText(subItem.getRepetitions() + " reps");
            updateButtonText();
        }

        public void init(int i, ActionItem actionItem) {
            idx = i;
            this.actionItem = actionItem;
            no.setText(Integer.toString(i));
        }

        public void updateButtonText() {
            SubItem sub = actionItem.getSubItems().get(idx);
            if (sub.isCompleted())
                complete.setText("已完成");
            else
                complete.setText("完成");
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
