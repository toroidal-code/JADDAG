import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GADDAG extends Trie {
    private static char separator = '>';

    public GADDAG(){ root = new Node(Node.root); }

    @Override
    public void add(String word){
        if (word.length() == 0) return;

        word = word.toLowerCase();

        String prefix;
        char[] ch;
        int i;
        for (i = 1; i < word.length(); i++){
            prefix = word.substring(0,i);
            ch = prefix.toCharArray();
            reverse(ch);
            super.add(new String(ch) + separator + word.substring(i));
        }
        ch = word.toCharArray();
        reverse(ch);
        super.add(new String(ch) + separator + word.substring(i));
    }

    private void reverse(char[] validData){
        for(int i = 0; i < validData.length/2; i++)
        {
            int temp = validData[i];
            validData[i] = validData[validData.length - i - 1];
            validData[validData.length - i - 1] = (char)temp;
        }
    }

    public HashSet<String> findWordsWithRackAndHook(Character[] rack, char hook){
        HashSet<String> words = new HashSet<String>();
        Arrays.sort(rack);
        ArrayList<Character> rackList = new ArrayList<Character>(Arrays.asList(rack));

        if (hook == ' '){
            char tile;
            while (rackList.size() > 1){
                tile = rackList.remove(0);
                findWordsRecurse(words, "",  rackList, tile, root, true);
            }
        } else {
            findWordsRecurse(words, "", rackList,  hook, root, true);
        }
        return words;
    }

    private void findWordsRecurse(HashSet<String> words, String word, ArrayList<Character> rack, char hook, Node cur, boolean direction){
        Node hookNode = cur.getChild(hook);

        //Base case
        if (hookNode == null)
            return;

        String hookCh = hook == separator ? "" : String.valueOf(hook); //Empty character if we're the separator
        word = (direction ? hookCh + word : word + hookCh); //Direction-based concatenation

        //if we've reached the end a word, add the word to output
        if (hookNode.getFinite())
            words.add(word);

        for (char nodeKey : hookNode.getKeys()) {
            if (nodeKey == separator)
                findWordsRecurse(words, word, rack, separator, hookNode, false);
            else if (rack.contains(nodeKey)){
                    //boolean duplicate = (rack.size() > 0 && (rack.get(nodeKey) == rack.get(rack.indexOf(nodeKey) - 1)));
                    ArrayList<Character> newRack = (ArrayList<Character>) rack.clone();
                    newRack.remove((Character)nodeKey);
                    findWordsRecurse(words, word, newRack, nodeKey, hookNode, direction);
                }
        }
    }
}
