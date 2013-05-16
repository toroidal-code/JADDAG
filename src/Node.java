import java.util.Collection;
import java.util.TreeMap;

public class Node implements Comparable<Node>{
    public static char root = '~';
    private char content;
    private boolean finite = false;
    public TreeMap<Character, Node> children = new TreeMap<Character, Node>();

    public char getContent (){ return content; }
    public void setFinite(boolean value){ finite = value; }
    public boolean getFinite(){ return finite; }

    public Node(char c){
        this.content = c;
        this.finite = false;
    }

    /**
     * Get our child nodes from our Map
     * @return Some collection of child nodes, or null if empty;
     */
    public Collection<Node> getChildren(){
        return children.values();
    }

    /**
     * Does this node have a child with the contents c2?
     * @param c2 the content to check
     * @return true if child list contains such child
     */
    public boolean hasChild(char c2) {
        return children.containsKey(c2);
    }

    /**
     * return the child that corresponds to the content c2
     * @param c the character to check against
     * @return a Node or null
     */
    public Node getChild(char c){
        return children.get(c);
    }

    /**
     * Add a child to our Map of child nodes
     * @param c2 the character to add
     */
    public void addChild(char c2){
        children.put(c2, new Node(c2));
    }

    /**
     * A basic toString, only returns our contents
     * @return the character the node holds
     */
    @Override
    public String toString(){
        return String.valueOf(content);
    }

    @Override
    public boolean equals(Object o){
        return o instanceof Node && content == ((Node) o).getContent();
    }

    @Override
    public int compareTo(Node o) {
        return content - o.content;
    }
}
