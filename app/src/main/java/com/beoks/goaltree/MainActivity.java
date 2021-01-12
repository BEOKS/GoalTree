package com.beoks.goaltree;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import de.blox.graphview.Graph;
import de.blox.graphview.GraphAdapter;
import de.blox.graphview.GraphView;
import de.blox.graphview.Node;
import de.blox.graphview.tree.BuchheimWalkerAlgorithm;
import de.blox.graphview.tree.BuchheimWalkerConfiguration;

public class MainActivity extends AppCompatActivity {
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    GraphAdapter adapter=null;
    SharedPreferences sharedPreferences=null;
    ArrayList<Graph> graphs=null;
    Gson gson=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomAppBar=(BottomAppBar)findViewById(R.id.bar);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);

        sharedPreferences=getPreferences(MODE_PRIVATE);
        gson=new Gson();

        String json=sharedPreferences.getString("Graph","");
        graphs=gson.fromJson(json,new TypeToken<ArrayList<Graph>>(){}.getType());
        if(graphs==null||graphs.size()==0){
            graphs=new ArrayList<Graph>();
            graphs.add(new Graph());
        }
        initNewGraph(graphs.get(0));

        bottomAppBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("Graph",gson.toJson(graphs));
        editor.commit();
    }

    private void initNewGraph(Graph graph){
        GraphView graphView = findViewById(R.id.graph);

        // you can set the graph via the constructor or use the adapter.setGraph(Graph) method
        adapter = new GraphAdapter<GraphView.ViewHolder>(graph) {

            @Override
            public int getCount() {
                return graph.getNodeCount();
            }

            @Override
            public Object getItem(int position) {
                return graph.getNodeAtPosition(position);
            }

            @Override
            public boolean isEmpty() {
                return graph.hasNodes();
            }

            @NonNull
            @Override
            public GraphView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.node, parent, false);
                return new SimpleViewHolder(view);
            }

            @Override
            public void onBindViewHolder(GraphView.ViewHolder viewHolder, Object data, int position) {
                ((SimpleViewHolder) viewHolder).textView.setText(((Node)data).getData().toString());
                ((SimpleViewHolder) viewHolder).textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        AddNeedConditionDialog addNeedConditionDialog=new AddNeedConditionDialog(view.getContext(),graph,
                                (Node)data,adapter);
                        addNeedConditionDialog.show();
                    }
                });
                ((SimpleViewHolder) viewHolder).textView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        AlertDialog.Builder builder=new AlertDialog.Builder(view.getContext());
                        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                removeNode(graph,(Node)data);
                                notifyDataSetChanged();
                            }
                        });
                        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        builder.setMessage("이 조건과 하위 조건을 삭제하시겠습니까?");
                        builder.show();
                        return false;
                    }
                });
            }
        };
        graphView.setAdapter(adapter);

        // set the algorithm here
        final BuchheimWalkerConfiguration configuration = new BuchheimWalkerConfiguration.Builder()
                .setSiblingSeparation(100)
                .setLevelSeparation(300)
                .setSubtreeSeparation(300)
                .setOrientation(BuchheimWalkerConfiguration.ORIENTATION_TOP_BOTTOM)
                .build();
        graphView.setLayout(new BuchheimWalkerAlgorithm(configuration));
    }
    private void removeNode(Graph graph,Node node){
        if(graph.hasSuccessor(node)){
            for(Node node1:graph.successorsOf(node)){
                removeNode(graph,node1);
            }
            graph.removeNode(node);
        }
        else{
            graph.removeNode(node);
        }
    }

}
class SimpleViewHolder extends GraphView.ViewHolder {
    TextView textView;

    SimpleViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.text);
    }
}