package Boggle.WordlistTrie;

import Boggle.WordlistTrie.TrieNode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arch on 3/20/17.
 */

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
        readList();
    }

    public void readList() {
        String fileName = "src/Boggle/Board/woordenlijst.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
                insert(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Inserts a word into the trie.
    private void insert(String word) {
        HashMap<Character, TrieNode> children = root.children;

        for(int i=0; i<word.length(); i++){
            char c = word.charAt(i);

            TrieNode t;
            if(children.containsKey(c)){
                t = children.get(c);
            }else{
                t = new TrieNode(c);
                children.put(c, t);
            }

            children = t.children;

            //set leaf node
            if(i==word.length()-1)
                t.isLeaf = true;
        }
    }

    // Returns if the word is in the trie.
    public boolean search(String word) {
        TrieNode t = searchNode(word);

        return t != null && t.isLeaf;
    }

    // Returns if there is any word in the trie
    // that starts with the given prefix.
    public boolean startsWith(String prefix) {
        return searchNode(prefix) != null;
    }

    private TrieNode searchNode(String str){
        Map<Character, TrieNode> children = root.children;
        TrieNode t = null;
        for(int i=0; i<str.length(); i++){
            char c = str.charAt(i);
            if(children.containsKey(c)){
                t = children.get(c);
                children = t.children;
            }else{
                return null;
            }
        }

        return t;
    }
}