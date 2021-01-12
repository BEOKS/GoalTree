package com.beoks.goaltree;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;

import de.blox.graphview.Graph;

public class CustomBottomAppDialog extends BottomSheetDialog {
    MainActivity mainActivity;
    public CustomBottomAppDialog(@NonNull Context context) {
        super(context);
        mainActivity=(MainActivity)context;
        LinearLayout linearLayout= (LinearLayout) LayoutInflater.from(mainActivity).inflate(R.layout.boottom_sheet_dialog,null);
        setContentView(linearLayout);
        ListView listView=(ListView)linearLayout.findViewById(R.id.list_view);

    }
}
class CustomAdapter extends BaseAdapter{
    ArrayList<Graph> graphs;
    public CustomAdapter(ArrayList<Graph> graphs){
        this.graphs=graphs;
    }
    @Override
    public int getCount() {
        return graphs.size();
    }

    @Override
    public Object getItem(int i) {
        return graphs.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= new TextView(view.getContext());
        ((TextView)view).setText(graphs.get(i).getNodes().get(0).getData().toString());
        return view;
    }
}