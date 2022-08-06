package ua.com.azbest.apptest3.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ua.com.azbest.apptest3.R;
import ua.com.azbest.apptest3.model.ListItem;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemListViewHolder> {

    private Context context;
    private List<ListItem> itemList;
    private final RecyclerListBehavior recyclerListBehavior;

    public RecyclerListAdapter(
            Context context,
            List<ListItem> itemList,
            RecyclerListBehavior recyclerListBehavior
    ) {
        this.context = context;
        this.itemList = itemList;
        this.recyclerListBehavior = recyclerListBehavior;
    }

    @NonNull
    @Override
    public RecyclerListAdapter.ItemListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_view, parent, false);
        return new ItemListViewHolder(view, recyclerListBehavior);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerListAdapter.ItemListViewHolder holder, int position) {
        holder.textViewTitle.setText(itemList.get(position).getTitle());
        holder.textViewId.setText(Long.toString(itemList.get(position).getId()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class ItemListViewHolder extends RecyclerView.ViewHolder {

        TextView textViewTitle;
        TextView textViewId;

        public ItemListViewHolder(@NonNull View itemView, RecyclerListBehavior recyclerListBehavior) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewItemTitle);
            textViewId = itemView.findViewById(R.id.textViewItemId);

            itemView.setOnClickListener(view -> {
                if (recyclerListBehavior != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        recyclerListBehavior.onItemClick(position);
                    }
                }
            });
        }
    }
}
