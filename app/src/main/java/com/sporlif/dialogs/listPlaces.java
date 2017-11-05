package com.sporlif.dialogs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sporlif.R;

import java.util.ArrayList;

public class listPlaces extends BaseAdapter {
    private ArrayList<Genre> arrayListItem;
    private Context context;

    @Override
    public int getCount() {
        return arrayListItem.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListItem.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View item = layoutInflater.inflate(R.layout.lst_genre, parent, false);

        ImageView lstGenreImgIcon = (ImageView) item.findViewById(R.id.lstGenreImgIcon);
        TextView lstGenreTxtGenre = (TextView) item.findViewById(R.id.lstGenreTxtGenre);

        lstGenreImgIcon.setImageResource(arrayListItem.get(position).getIcon());
        lstGenreTxtGenre.setText(arrayListItem.get(position).getPlace());

        return item;
    }

    public class Genre{

        private int icon;
        private String place;

        public Genre(){
        }

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String genre) {
            this.place = genre;
        }
    }

}
