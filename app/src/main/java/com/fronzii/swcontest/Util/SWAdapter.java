package com.fronzii.swcontest.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.fronzii.swcontest.R;

public class SWAdapter extends ArrayAdapter<ApkUtils.SWAppInfo>{
    private final int resourceId;

    public SWAdapter(@NonNull Context context, int resource, @NonNull ApkUtils.SWAppInfo[] objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        ApkUtils.SWAppInfo swAppInfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.icon = (ImageView)view.findViewById(R.id.icon);
            viewHolder.packageName = (TextView)view.findViewById(R.id.package_name);
            viewHolder.isMal = (TextView)view.findViewById(R.id.is_mal);
            view.setTag(viewHolder);
        }else{
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }

        viewHolder.icon.setImageDrawable(swAppInfo.icon);
        viewHolder.packageName.setText(swAppInfo.appName);
        viewHolder.isMal.setText(swAppInfo.isMal? "Malicious" : "Benign");
        viewHolder.isMal.setTextColor(swAppInfo.isMal? Color.rgb(200, 0, 0) : Color.rgb(0, 200, 0));

        return view;
    }

    static class ViewHolder{
        ImageView icon;
        TextView packageName;
        TextView isMal;
    }
}
