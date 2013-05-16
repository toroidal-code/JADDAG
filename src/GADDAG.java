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
//
//    public ArrayList<String> findWordsWithRackAndHook(Character[] rack, char hook){
//        Node trie = root;
//        ArrayList<String> words = new ArrayList<String>();
//        Arrays.sort(rack);
//
//        if (hook == ' '){
//            ArrayList<Character> rackList = new ArrayList<Character>(Arrays.asList(rack));
//            char tile;
//            while (rackList.size() > 1){
//                tile = rackList.remove(0);
//                findWordsRecurse(words, "",  rackList, tile, trie, true);
//
//            }
//        }
//        return words;
//    }
//
//    private void findWordsRecurse(ArrayList<String> words, String word, ArrayList<Character> rack, char hook, Node cur, boolean direction){
//        Node hookNode = cur.getChild(hook);
//
//        if (hookNode == null) return;
//
//        String hookCh = String.valueOf(hook == separator || hookNode.getFinite() ? "" : hook);
//        word = (direction ? hookCh + word : word + hookCh);
//
//        for ()
//    }
}
