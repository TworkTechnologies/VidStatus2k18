package twork.video.status.adapter.status;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import twork.video.status.R;
import twork.video.status.interf.ItemClickListener;
import twork.video.status.object.status.CatagoryList;
import twork.video.status.object.status.StatusCategoryObject;

/**
 * Created by Jiyan on 5/3/2018.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;

    List<CatagoryList> dataArray;

    private ItemClickListener clickListener;

    public CategoryListAdapter(Context context, List<CatagoryList> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_status_category;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_status_category = itemView.findViewById(R.id.tv_status_category);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) clickListener.onClick(view, "0", getAdapterPosition());
        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_status_category, parent, false);
        //AppFont.setFont(context, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_status_category.setText(dataArray.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}
