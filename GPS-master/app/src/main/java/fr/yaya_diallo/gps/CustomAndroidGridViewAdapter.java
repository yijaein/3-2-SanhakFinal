package fr.yaya_diallo.gps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yayacky on 11/09/2016.
 */
public class CustomAndroidGridViewAdapter  extends BaseAdapter {
    private Context mContext;
    private List<Item> items = new ArrayList<Item>();

    public CustomAndroidGridViewAdapter(Context c) {
        mContext = c;
        items.add(new Item("Ma position", R.mipmap.aller));
        items.add(new Item("Envoyer mes coordonnées", R.mipmap.envoie));
        items.add(new Item("Aller à ...", R.mipmap.google_map));
        items.add(new Item("Les lieux importants", R.mipmap.voir));
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int p) {
        return items.get(p);
    }

    @Override
    public long getItemId(int p) {
        return items.get(p).drawableId;
    }

    @Override
    public View getView(int p, View convertView, ViewGroup parent) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TextView textView;
        ImageView imageView;

        if (convertView == null) {

            grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_item, parent, false);
            grid.setTag(R.id.gridview_image, grid.findViewById(R.id.gridview_image));
            grid.setTag(R.id.gridview_text, grid.findViewById(R.id.gridview_text));
        } else {
            grid = (View) convertView;
        }

        textView = (TextView) grid.getTag(R.id.gridview_text);
        imageView = (ImageView) grid.getTag(R.id.gridview_image);

        Item item = (Item) getItem(p);

        imageView.setImageResource(item.drawableId);
        textView.setText(item.name);
        return grid;
    }

    private class Item {
        final String name;
        final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}
