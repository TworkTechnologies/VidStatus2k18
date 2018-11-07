package twork.video.status.adapter.video;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import twork.video.status.R;
import twork.video.status.interf.ItemClickListener;
import twork.video.status.object.maincate.MainCateDataObject;

/**
 * Created by Jiyan on 5/3/2018.
 */

public class MainCategoryListAdapter extends RecyclerView.Adapter<MainCategoryListAdapter.ViewHolder> {
    Context context;
    LayoutInflater inflater;

    List<MainCateDataObject> dataArray;

    private ItemClickListener clickListener;

    public MainCategoryListAdapter(Context context, List<MainCateDataObject> dataArray) {
        this.context = context;
        this.dataArray = dataArray;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView img_category;
        TextView tv_category;
        ProgressBar prg_image;

        public ViewHolder(View itemView) {
            super(itemView);
            img_category = itemView.findViewById(R.id.img_category);
            tv_category = itemView.findViewById(R.id.tv_category);
            prg_image = itemView.findViewById(R.id.prg_image);

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
        View view = inflater.inflate(R.layout.adapter_category, parent, false);
        //AppFont.setFont(context, view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Glide.with(context).load(dataArray.get(position).getCategory_image())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        holder.prg_image.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        holder.prg_image.setVisibility(View.GONE);
                        return false;
                    }
                })
                .into(holder.img_category);
        holder.tv_category.setText(dataArray.get(position).getCategory_name());

    }

    @Override
    public int getItemCount() {
        return dataArray.size();
    }
}
