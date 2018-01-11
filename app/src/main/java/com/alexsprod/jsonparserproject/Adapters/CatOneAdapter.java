package com.alexsprod.jsonparserproject.Adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.alexsprod.jsonparserproject.R;
import com.alexsprod.jsonparserproject.fragments.ArticleFragment;
import com.alexsprod.jsonparserproject.items.Item;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.alexsprod.jsonparserproject.Utils.JsonParser.TAG;

public class CatOneAdapter extends ArrayAdapter<Item> {

    private Activity activity;
    private List<Item> items;
    private int row;

    public CatOneAdapter(Activity act, int resource, List<Item> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.items = arrayList;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        final ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);

            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((items == null) || ((position + 1) > items.size()))
            return view;

        Item objBean = items.get(position);

        holder.tvTitle = view.findViewById(R.id.title_cat1);
        holder.btnView = view.findViewById(R.id.btnViewCat1);
        holder.imgView = view.findViewById(R.id.img_cat1);

        if (holder.tvTitle != null && null != objBean.getTitle()
                && objBean.getTitle().trim().length() > 0) {
            holder.tvTitle.setText(Html.fromHtml(objBean.getTitle()));
        }
        if (holder.tvText != null && null != objBean.getText()
                && objBean.getText().trim().length() > 0) {
            holder.tvText.setText(Html.fromHtml(objBean.getText()));
        }
        if (holder.imgView != null) {
            if (null != objBean.getLink()
                    && objBean.getLink().trim().length() > 0) {
                Picasso.with(this.getContext().getApplicationContext())
                        .load(String.valueOf(Html.fromHtml(objBean.getLink())))
                        .resize(250, 190)
                        .placeholder(R.mipmap.ic_empty)
                        .centerCrop()
                        .into(holder.imgView);
            } else {
                holder.imgView.setImageResource(R.mipmap.ic_launcher);
            }
        }
        holder.btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item item = getItem(position);
                Log.v(TAG, "############### Item is: " + item.getId());
                Bundle bundle = new Bundle();
                bundle.putString("ID", item.getId());
                bundle.putString("Title", item.getTitle());
                bundle.putString("ImgLink", item.getLink());
                bundle.putString("Text", item.getText());
                bundle.putString("DopText", item.getDopText());

                Fragment fragment = null;
                try {
                    fragment = ArticleFragment.class.newInstance();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (fragment != null) {
                    fragment.setArguments(bundle);
                }
                /*FragmentManager fragmentManager = getContext().startActivity(ArticleFragment);
                fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();*/

                ((FragmentActivity) getContext()).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.flContent, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        return view;
    }


    private class ViewHolder {
        private TextView tvTitle, tvText;
        private ImageView imgView;
        private Button btnView;
    }
}