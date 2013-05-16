import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.layout.mxIGraphLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.swing.util.mxMorphing;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxEvent;
import com.mxgraph.util.mxEventObject;
import com.mxgraph.util.mxEventSource.mxIEventListener;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import java.awt.*;

public class Test {

    public Test(Trie trie) {
        JFrame f = new JFrame();
        f.setSize(500, 500);
        f.setLocation(300, 200);

        final mxGraph graph = new mxGraph();
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        f.getContentPane().add(BorderLayout.CENTER, graphComponent);
        f.setVisible(true);

        Object parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try {
            Object main = graph.insertVertex(parent, null, "root", 100,100, 80, 30);
            drawTrie(graph, parent, trie, main, trie.getRoot());
        } finally {
            graph.getModel().endUpdate();
        }

        // define layout
        mxIGraphLayout layout = new mxCompactTreeLayout(graph);

        // layout using morphing
        graph.getModel().beginUpdate();
        try {
            layout.execute(graph.getDefaultParent());
        } finally {
            mxMorphing morph = new mxMorphing(graphComponent, 20, 1.2, 20);

            morph.addListener(mxEvent.DONE, new mxIEventListener() {

                @Override
                public void invoke(Object arg0, mxEventObject arg1) {
                    graph.getModel().endUpdate();
                }

            });

            morph.startAnimation();
        }

    }

    public void drawTrie(mxGraph graph, Object parent, Trie trie, Object root, Node current){

        Object o;
        if (!(current.getChildren() == null)){
            for (Node node : current.getChildren()){
                o = graph.insertVertex(parent, null, node.toString(), 100, 100, 80, 30);
                if (node.getFinite())
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{o});
                graph.insertEdge(parent, null, "", root, o);
                drawTrie(graph, parent, trie, o, node);

            }
        }
    }

    public static void main(String[] args) throws Exception {
        Trie g = new GADDAG(); // change to trie to see a trie.
        g.addAll(new String[]{"Abstract", "Absolute", "Absolve", "Butts", "Boats", "Bannana", "Abs"});
        Test t = new Test(g);
    }
}