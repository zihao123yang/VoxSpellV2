package sample;

import java.io.Serializable;

/**
 * Created by zihao123yang on 16/09/16.
 */
public class Word implements Serializable, Comparable<Word> {

    private static final long serialVersionUID = 1L;



    private String _word;
    private int _numMastered;
    private int _numFaulted;
    private int _numFailed;
    private int _level;


    public Word(String word, int level) {
        _word = word;
        _level = level;
    }

    public void addMastered() {

        _numMastered++;
    }

    public void addFaulted() {

        _numFaulted++;
    }

    public void addFailed() {

        _numFailed++;
    }

    public String name() {

        return _word;
    }

    @Override
    public int compareTo(Word o) {
        return _word.compareTo(o._word);
    }

    public String get_word() {
        return this._word;
    }


    public int get_numMastered() {
        return _numMastered;
    }

    public int get_numFaulted() {
        return _numFaulted;
    }

    public int get_numFailed() {
        return _numFailed;
    }

    /*
    public void set_numMastered(int ma)

    public int getLevel() {
        return this._level;
    }
*/

    @Override
    public boolean equals(Object word){

        return this._word.equals(((Word)word)._word);
    }

}

