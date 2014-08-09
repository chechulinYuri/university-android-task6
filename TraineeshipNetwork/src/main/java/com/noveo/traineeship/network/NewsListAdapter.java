package com.noveo.traineeship.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.noveo.traineeship.network.models.News;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

public class NewsListAdapter extends ArrayAdapter<News> {

    public NewsListAdapter(Context context, List<News> news) {
        super(context, 0, news);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        final TextView titleTextView = (TextView) convertView.findViewById(R.id.news_title);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.news_image);
        News news = getItem(position);

        titleTextView.setText(news.getTitle());
        if (news.getImage() != null) {
            imageView.setVisibility(View.VISIBLE);
            Picasso.with(getContext()).load(news.getImage()).into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        return convertView;
    }
}
