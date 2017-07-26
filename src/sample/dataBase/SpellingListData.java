package sample.dataBase;

import sample.SystemStatus;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by zihao123yang on 7/10/16.
 * this class stores the Spelling list and is the database containing the words for each level.
 */
public class SpellingListData implements QuizDataBase {

    private static SpellingListData instance = null;

    // stores the list of words for each level
    private HashMap<Integer, ArrayList<String>> _spellingListData;

    // This is a singleton class because there should always only be one Spelling list database object in the whole program
    // the level is the key and an ArrayList of String is the data
    private SpellingListData() {

        if (_spellingListData == null) {
            createDataBase();
        }
    }

    public static SpellingListData getInstance() {
        if (instance == null) {
            instance = new SpellingListData();
        }

        return instance;
    }


    /**
     * method fomr QuizDataBase interface, it creates the database for the spelling list. it reads the spelling list text
     * file and populates the _spellListData hashmap. it is only called once when the object is created
     */
    @Override
    public void createDataBase() {

        _spellingListData = new HashMap<Integer, ArrayList<String>>();

        String currentLine;
        int level = 1;
        //arraylist for words in a particular level
        ArrayList<String> levelList = new ArrayList<>();

        try {
            BufferedReader br = new BufferedReader(new FileReader("NZCER-spelling-lists.txt"));

            while ((currentLine = br.readLine()) != null) {
                //a new level is found in the text file
                if (currentLine.charAt(0) == '%') {

                    levelList = new ArrayList<String>();
                    // put a new arraylist into the hashmap with the level as the key
                    _spellingListData.put(level, levelList);

                    //level incremented
                    level++;
                } else {
                    //add word into ArrayList for current level
                    levelList.add(currentLine);
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        setNumOfLevels();
    }


    /**
     *
     * @param file
     */
    public void changeWordList(File file) {

        _spellingListData = new HashMap<Integer, ArrayList<String>>();

        String currentLine;
        int level = 1;
        ArrayList<String> levelList = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));

            while ((currentLine = br.readLine()) != null) {
                System.out.println(currentLine);
                if (currentLine.charAt(0) == '%') {

                    levelList = new ArrayList<String>();

                    _spellingListData.put(level, levelList);

                    level++;
                } else {
                    levelList.add(currentLine);
                }
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        setNumOfLevels();
    }


    @Override
    public boolean addToDataBase(String word) {
        ArrayList<String> levelList = _spellingListData.get(SystemStatus.currentlevel());
        if (levelList.contains(word)) {
            return false;
        } else {
            levelList.add(word);
            return true;
        }
    }

    @Override
    public boolean removeFromDataBase(String word) {
        ArrayList<String> levelList = _spellingListData.get(SystemStatus.currentlevel());
        if (levelList.contains(word)) {
            levelList.remove(word);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void randomizeData(ArrayList<String> levelList) {
        long seed = System.nanoTime();
        Collections.shuffle(levelList, new Random(seed));
    }


    @Override
    public int sizeOfLevel(ArrayList<String> levelList) {
        return levelList.size();
    }


    @Override
    public ArrayList<String> makeQuizList() {
        ArrayList<String> levelList = _spellingListData.get(SystemStatus.currentlevel());
        int size = levelList.size();
        randomizeData(levelList);

        if (SystemStatus.currentlevel() > SystemStatus.numOfLevels()) {
            return null;
        } else if (size >= 10) {
            return new ArrayList<String> (levelList.subList(0, 10));
        } else {
            return levelList;
        }

    }

    public void setNumOfLevels() {
        System.out.println("initialise numLevels" + _spellingListData.size());
        SystemStatus.setNumberofLevels(_spellingListData.size());
    }
}
