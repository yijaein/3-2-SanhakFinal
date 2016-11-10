package fr.yaya_diallo.gps;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by yayacky on 05/09/2016.
 */
public class LieuxMenuAdapter extends BaseAdapter {

    private Context mContext;
    private LayoutInflater mInflater;
    private ArrayList<LieuxMenu> mDataSource;

    public LieuxMenuAdapter(Context context, ArrayList<LieuxMenu> items) {
        mContext = context;
        mDataSource = items;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return mDataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = mInflater.inflate(R.layout.list_item_lieux_menu, viewGroup, false);
        TextView titleTextView =
                (TextView) rowView.findViewById(R.id.mainmenu_list_title);

        TextView subtitleTextView =
                (TextView) rowView.findViewById(R.id.mainmenu_list_subtitle);

        TextView detailTextView =
                (TextView) rowView.findViewById(R.id.mainmenu_list_detail);

        ImageView thumbnailImageView =
                (ImageView) rowView.findViewById(R.id.mainmenu_list_thumbnail);

        LieuxMenu mainMenu = (LieuxMenu) getItem(position);
        titleTextView.setText(mainMenu.title);
        subtitleTextView.setText(mainMenu.description);
        //detailTextView.setText(recipe.label);
        if("sekoutoureyah".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.sekoutoureyah).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }else if("hopitaux".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.hopitaux).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }else if("aeroport".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.aeroport).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        } else if("palaisdupeuple".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.palaisdupeuple).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        } else if("ambassadedefrance".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.ambassadefrance).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }
        else if("ambassadeusa".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.ambassadeusa).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }
        else if("primacenter".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.primacenter).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }
        else if("plazadiamant".equals(mainMenu.imageUrl))
        {
            Picasso.with(mContext).load(R.mipmap.plazadiamant).placeholder(R.mipmap.ic_launcher).into(thumbnailImageView);
        }

        Typeface titleTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-Bold.ttf");
        titleTextView.setTypeface(titleTypeFace);

        Typeface subtitleTypeFace =
                Typeface.createFromAsset(mContext.getAssets(), "fonts/JosefinSans-SemiBoldItalic.ttf");
        subtitleTextView.setTypeface(subtitleTypeFace);

        Typeface detailTypeFace = Typeface.createFromAsset(mContext.getAssets(), "fonts/Quicksand-Bold.otf");
        detailTextView.setTypeface(detailTypeFace);
        return rowView;
    }
}