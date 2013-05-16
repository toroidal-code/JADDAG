import java.util.ArrayList;

public class Trie {
    protected Node root; //The root node
    protected long nodeCount;
    protected int wordCount;
    protected short maxDepth;

    public Node getRoot(){ return root; }
    public long getNodeCount(){ return nodeCount; }
    public int getWordCount(){ return wordCount; }
    public short getMaxDepth(){ return maxDepth; }

    public Trie(){
        this.root = new Node(Node.root);
        this.nodeCount = 1;
        this.wordCount = 0;
        this.maxDepth = 0;
    }

    /**
     * Add all the words in an array to the trie
     * @param words the list of words to add
     */
    public void addAll(String[] words){
        for (String word: words)
            this.add(word);
    }

    /**
     * Add a word to the trie
     * @param word the word to add
     */
    public void add(String word){
        if (word.length() == 0) return;
        char[] characters = word.toUpperCase().toCharArray();
        Node current = this.root;

        for (char character : characters)
            if (current.hasChild(character))
                current = current.getChild(character);
            else {
                current.addChild(character);
                this.nodeCount++;
                current = current.getChild(character);
            }


        current.setFinite(true); // set our last letter's flag to indicate word conclusion

        if (characters.length > maxDepth)
            this.maxDepth = (short) characters.length;
        this.wordCount++;
    }

    /**
     * Check if the trie contains a word by traveling down
     * @param word the word to check for
     * @return boolean whether we've got it or not
     */
    public boolean contains(String word){
        char[] characters = word.toUpperCase().toCharArray();
        Node tmp = root;

        for (char character : characters)
            if (tmp.hasChild(character))
                tmp = tmp.getChild(character);
            else
                return false;

        return tmp.getFinite();
    }

    /**
     * Find the terminating node of a prefix
     * @param prefix A string to search from
     * @return The ending node
     */
    public Node find(String prefix){
        char[] characters = prefix.toUpperCase().toCharArray();

        Node tmp = root;
        for (char character : characters)
            if (tmp.hasChild(character))
                tmp = tmp.getChild(character);
            else
                return null;

        return tmp;
    }

    /**
     * Returns valid words contained in the Trie using recursion
     * @return an ArrayList of Strings (the words)
     */
    public ArrayList<String> getWords(){
        ArrayList<String> words = new ArrayList<String>();
        dig("", root, words);
        return words;
    }

    /**
     * A recursive diving function
     * @param word the word so far, based on past nodes
     * @param cur the current occupied node
     * @param words a list to fill with words
     */
    private void dig(String word, Node cur, ArrayList<String> words){
        //If we're at a word's end, we should add it to the list of words.
        if (cur.getFinite())
            words.add(word);

        // Otherwise, as long as we can go further down, we will
        if (cur.getChildren() != null)
            for (Node node: cur.getChildren())
                dig(word + node.getContent(), node, words);
    }

}
