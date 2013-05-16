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
import java.io.File;
import java.util.Scanner;

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
            drawTrie(graph, parent, main, trie.getRoot());
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

    public void drawTrie(mxGraph graph, Object parent, Object root, Node current){

        Object o;
        if (!(current.getChildren() == null)){
            for (Node node : current.getChildren()){
                o = graph.insertVertex(parent, null, node.toString(), 100, 100, 80, 30);
                if (node.getFinite())
                    graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{o});
                graph.insertEdge(parent, null, "", root, o);
                drawTrie(graph, parent, o, node);

            }
        }
    }

    public static Character[] toCharacterArray(String s) {
        if (s == null) {
            return null;
        }
        Character[] array = new Character[s.length()];
        for (int i = 0; i < s.length(); i++) {
            array[i] = s.charAt(i);
        }
        return array;
    }

    public static void main(String[] args) throws Exception {
        GADDAG g = new GADDAG(); // change to trie to see a trie.
        long startTime = System.nanoTime();

        if (args.length == 1){
            Scanner dictionary = new Scanner(new File(args[0]));
            while(dictionary.hasNext()) {
                g.add(dictionary.nextLine());
            }
            long duration = System.nanoTime() - startTime;
            System.out.println("Time to build GADDAG: " + duration/1000000000. + " seconds");
            repl(g);
        } else {
            g.addAll(new String[]{"Abstract", "Absolute", "Absolve", "Boats", "Bannana", "Abs"});
            new Test(g);
        }


    }

    public static void repl(GADDAG g){
        System.out.println("Welcome to the word finder");
        System.out.println("Enter in 'r', followed by the rack you want to use to search.");
        System.out.println("Use 'h' followed by a character to change the hook.");
        Scanner scan = new Scanner(System.in);
        System.out.print("> ");
        char hook = ' ';
        while(scan.hasNext()){
            String nextLine = scan.next();
            if (nextLine.equals("r")){
                String rack = scan.next();
                System.out.println(g.findWordsWithRackAndHook(toCharacterArray(rack.toUpperCase()), hook));
            }
            else if (nextLine.equals("h")){
                hook = scan.next().toUpperCase().charAt(0);
                System.out.println("Hook is now: " + hook);
            }
            System.out.print("> ");
        }
    }
}