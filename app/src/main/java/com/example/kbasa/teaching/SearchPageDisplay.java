package com.example.kbasa.teaching;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Map;
import java.util.Vector;

public class SearchPageDisplay extends ArrayAdapter<String> implements Filterable{

    private final Activity context;
    private List<String> web;
    private Vector<Map<String,String>> vector;
    public SearchPageDisplay(Activity context,
                      List<String> web1, Vector<Map<String,String>> vector1) {
        super(context, R.layout.student_search_listview, web1);

            this.web = web1;
            this.vector = vector1;
            this.context = context;
    }


    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.student_search_listview, null, true);

        Map<String,String> courseDetails = vector.elementAt(position);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        txtTitle.setText(courseDetails.get("courseName"));

        TextView name = (TextView) rowView.findViewById(R.id.name);
        name.setText(courseDetails.get("name"));

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);


        Picasso.with(getContext()).load(courseDetails.get("profileUri")).into(imageView);
        return rowView;
    }
}