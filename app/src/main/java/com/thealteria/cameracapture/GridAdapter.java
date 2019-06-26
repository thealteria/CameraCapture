package com.thealteria.cameracapture;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class GridAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context context;
    private ArrayList<String> arrayList;

    GridAdapter(ImageActivity imageActivity, ArrayList<String> imageList) {

        arrayList = imageList;
        context = imageActivity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint({"ViewHolder", "StaticFieldLeak"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();

        View view = inflater.inflate(R.layout.grid_item_layout, parent, false);
        holder.imageView = view.findViewById(R.id.iv_Grid);

        view.setTag(holder);

        new AsyncTask<Holder, Void, Bitmap>() {
            private Holder viewHolder;

            @Override
            protected Bitmap doInBackground(Holder... holders) {

                viewHolder = holders[0];
                BitmapFactory.Options options = new BitmapFactory.Options();

                options.inSampleSize = 8;

                return BitmapFactory.decodeFile(arrayList.get(position), options);
            }

            @Override
            protected void onPostExecute(final Bitmap bitmap) {
                super.onPostExecute(bitmap);

                Glide.with(context.getApplicationContext()).load(bitmap).centerCrop().apply(
                        new RequestOptions().override(100, 100)).into(viewHolder.imageView);
            }
        }.execute(holder);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Image", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
