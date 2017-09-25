package com.prescription.doctorprescription.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.prescription.doctorprescription.R;

import java.util.ArrayList;



public class AutoCompleteTextViewAdapter extends ArrayAdapter<String> {
    final String TAG = "ACTextViewAdapter";
    ArrayList<String> items, tempItems, suggestions;

    public AutoCompleteTextViewAdapter(Context context, ArrayList<String> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.items = objects;
        /*for (String s : objects){
            Log.d(TAG,s);
        }*/
        this.tempItems = new ArrayList<String>(objects);
        this.suggestions = new ArrayList<String>(objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String item = items.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.auto_complete_adapter_item, parent, false);
        }
        TextView tvItems = (TextView) convertView.findViewById(R.id.tvItems);
        if (tvItems != null)
            tvItems.setText(item);

        // Now assign alternate color for rows
        if (position % 2 == 0)
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.White));
        else
            convertView.setBackgroundColor(getContext().getResources().getColor(R.color.AliceBlue));

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return myFilter;
    }

    Filter myFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String customer = (String) resultValue;
            return customer;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (String item : tempItems) {
                    if (item.toLowerCase().startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(item);
                    }
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ArrayList<String> c = (ArrayList<String>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (String cust : c) {
                    add(cust);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
