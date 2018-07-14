package com.example.halfdevil.dictionaryapp;


public class DictionaryHelper {

    String word;
    String meaning;

    public DictionaryHelper(String word, String meaning) {
        this.word = word;
        this.meaning = meaning;
    }
    public String getMeaning() {
        return meaning;
    }
    public String getWord() {
        return word;
    }
}
