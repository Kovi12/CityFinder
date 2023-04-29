package com.example.cityfinder;

import android.app.ListActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

class CityItem{
    public String name;
    public int img_res;

    public String getName() {
        return name;
    }
}

class CityItemAdapter extends BaseAdapter {
    private ListActivity context;
    private ArrayList<CityItem> city_items;

    public CityItemAdapter (ListActivity context) {
        this.context = context;
        city_items = new ArrayList<CityItem>();
    }

    public void add(String name, int img_res) {
        CityItem c = new CityItem();
        c.name = name;
        c.img_res = img_res;
        city_items.add(c);
        this.notifyDataSetChanged();
    }
    @Override
    public int getCount (){
        return city_items.size();
    }

    @Override
    public CityItem getItem(int position)
    {
        return city_items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View element;

        LayoutInflater inflater = context.getLayoutInflater();
        element = (View) inflater.inflate(R.layout.city_item, null);

        TextView name = (TextView) element.findViewById(R.id.text_view);
        ImageView image = (ImageView)  element.findViewById(R.id.image_view);

        name.setText(city_items.get(i).name);
        image.setImageResource(city_items.get(i).img_res);
        return element;
    }

    public ArrayList<CityItem> getList() {
        return city_items;
    }

    public void setFilteredList (ArrayList<CityItem> filteredList){
        this.city_items = filteredList;
        notifyDataSetChanged();
    }
}
