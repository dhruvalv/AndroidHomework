package com.example.rkjc.news_app_2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class NewsAdapter  extends RecyclerView.Adapter<NewsAdapter.NewsItemViewHolder> {

    Context mContext;
    ArrayList<NewsItem> itemList;

    public NewsAdapter(Context context, ArrayList<NewsItem> itemList){
        this.mContext = context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public NewsAdapter.NewsItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.news_item, parent, shouldAttachToParentImmediately);
        NewsItemViewHolder viewHolder = new NewsItemViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.NewsItemViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class NewsItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        TextView description;
        TextView date;

        public NewsItemViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tv_title);
            description = (TextView) itemView.findViewById(R.id.tv_description);
            date = (TextView) itemView.findViewById(R.id.tv_date);
        }

        void bind(final int listIndex) {
            title.setText("Title :"+ itemList.get(listIndex).getTitle());
            description.setText("Description :"+ itemList.get(listIndex).getDescription());
            date.setText("Date :"+itemList.get(listIndex).getPublishedAt());
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            String urlString = itemList.get(getAdapterPosition()).getUrl();

            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(urlString));
            mContext.startActivity(i);

        }
    }
}
