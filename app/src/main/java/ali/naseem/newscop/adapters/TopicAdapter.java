package ali.naseem.newscop.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ali.naseem.newscop.R;
import ali.naseem.newscop.models.Topics;
import ali.naseem.newscop.utils.Utils;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.ViewHolder> {

    private List<Topics> items;
    private Context context;

    public TopicAdapter(List<Topics> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.text_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {
        final Topics item = items.get(i);
        holder.text.setText(item.getTopicName());
        if (isPresent(item.getId())) {
            holder.choiceContainer.setBackground(context.getResources().getDrawable(R.drawable.rouded));
            holder.text.setTextColor(Color.WHITE);
        }
        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPresent(item.getId())) {
                    Utils.getInstance().getDatabase().topicsDao().delete(item);
                    holder.choiceContainer.setBackground(context.getResources().getDrawable(R.drawable.rounded_initial));
                    holder.text.setTextColor(context.getResources().getColor(R.color.colorHeadings));
                } else {
                    Utils.getInstance().getDatabase().topicsDao().insertAll(item);
                    holder.choiceContainer.setBackground(context.getResources().getDrawable(R.drawable.rouded));
                    holder.text.setTextColor(Color.WHITE);
                }
            }
        });
    }

    private boolean isPresent(int id) {
        return Utils.getInstance().getDatabase().topicsDao().getTopic(id) != null;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView text;
        public View choiceContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            text = itemView.findViewById(R.id.text);
            choiceContainer = itemView.findViewById(R.id.choiceContainer);
        }
    }
}
