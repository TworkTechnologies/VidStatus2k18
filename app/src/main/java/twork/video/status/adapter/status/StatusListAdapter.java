package twork.video.status.adapter.status;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import twork.video.status.R;
import twork.video.status.interf.ItemClickListener;
import twork.video.status.object.status.CatagoryList;
import twork.video.status.object.status.StatusCategoryObject;

/**
 * Created by Jiyan on 5/3/2018.
 */

public class StatusListAdapter extends RecyclerView.Adapter<StatusListAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;

    List<String> dataArray;
    String catTitle;

    private ItemClickListener clickListener;

    public StatusListAdapter(Context context, List<String> dataArray, String catTitle) {
        this.context = context;
        this.dataArray = dataArray;
        this.catTitle = catTitle;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_status_category, tv_category;
        ImageView img_copy_clip_board, img_share;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_status_category = itemView.findViewById(R.id.tv_status_category);
            tv_category = itemView.findViewById(R.id.tv_category);

            img_copy_clip_board = itemView.findViewById(R.id.img_copy_clip_board);
            img_share = itemView.findViewById(R.id.img_share);

            //itemView.setOnClickListener(this);
            img_copy_clip_board.setOnClickListener(this);
            img_share.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                if (view.getId() == R.id.img_copy_clip_board) {
                    clickListener.onClick(view, "0", getAdapterPosition());
                } else if (view.getId() == R.id.img_share) {
                    clickListener.onClick(view, "1", getAdapterPosition());
                }
            }

        }
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.adapter_status_list, parent, false);
        //AppFont.setFont(context, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.tv_category.setText(catTitle);
        holder.tv_status_category.setText(dataArray.get(position));
    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}
