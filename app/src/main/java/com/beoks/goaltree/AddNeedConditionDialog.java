package com.beoks.goaltree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.Node;

public class AddNeedConditionDialog extends AlertDialog.Builder {
    LinearLayout linearLayout;
    protected AddNeedConditionDialog(Context context, Graph graph, Node parentNode, GraphAdapter graphAdapter) {
        super(context);
        linearLayout= (LinearLayout) LayoutInflater.from(context).inflate(R.layout.add_need_contidion_dialog,null);
        setView(linearLayout);
        setPositiveButton("추가", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText=linearLayout.findViewById(R.id.condition_editText);
                graph.addEdge(parentNode,new Node(editText.getText().toString()));
                graphAdapter.notifyDataSetChanged();
            }
        });
        setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
