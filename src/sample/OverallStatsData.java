package sample;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * a class dedicated to updating Word object data, and calculating accuracy statistics from a database of words
 */
public class OverallStatsData {

    private static OverallStatsData instance = null;
    private HashMap<Integer, ArrayList<Word>> statistics;
    private HashMap<Integer, Integer> highScores;
    private ArrayList<Word> levelList;
    private int score;

    private File _statsFile = new File("stats.ser");
    private File _highScoreFile = new File("highScore.ser");



    private OverallStatsData() {

        if (statistics == null) {
            statistics = new HashMap<Integer, ArrayList<Word>>();
        }

        if (highScores == null) {
            highScores = new HashMap<Integer, Integer>();
        }

        score = 0;
    }

    public static OverallStatsData getInstance() {

        if (instance == null) {
            instance = new OverallStatsData();
        }

        return instance;
    }


    /**
     * saves statistics when program closes
     * @throws IOException
     */
    public void saveStats() throws IOException {
        FileOutputStream fileOut = new FileOutputStream(_statsFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(statistics);
        fileOut.close();
        objectOut.close();

        FileOutputStream fileOutScore = new FileOutputStream(_highScoreFile);
        ObjectOutputStream objectOutScore = new ObjectOutputStream(fileOutScore);
        objectOutScore.writeObject(highScores);
        fileOut.close();
        objectOut.close();

    }


    /**
     * load stats when program opens
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void loadStats() throws IOException, ClassNotFoundException {
        if (!_statsFile.exists()) {
            return;
        }
        FileInputStream fileIn = new FileInputStream(_statsFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        statistics = (HashMap<Integer, ArrayList<Word>>) objectIn.readObject();
        fileIn.close();
        objectIn.close();

        if (!_highScoreFile.exists()) {
            return;
        }
        FileInputStream fileInScore = new FileInputStream(_highScoreFile);
        ObjectInputStream objectInScore = new ObjectInputStream(fileInScore);
        highScores = (HashMap<Integer, Integer>) objectInScore.readObject();
        fileIn.close();
        objectIn.close();
    }

    //setting the level list for current quiz. only need to be set once through each quiz
    private void setLevelList() {

        if (levelList != null) {
            if (!statistics.containsKey(SystemStatus.currentlevel())) {
                levelList = new ArrayList<Word>();
                statistics.put(SystemStatus.currentlevel(), levelList);
                levelList = statistics.get(SystemStatus.currentlevel());
            } else {
                levelList = statistics.get(SystemStatus.currentlevel());
            }
        } else {
            levelList = new ArrayList<Word>();
        }
    }



    public void addMasteredWord(Word word) {
        setLevelList();
        if (levelList.contains(word)) {
            int index = levelList.indexOf(word);
            Word seenWord = levelList.get(index);
            seenWord.addMastered();
        } else {
            word.addMastered();
            levelList.add(word);
        }
    }

    public void addFaultedWord(Word word) {
        setLevelList();
        if (levelList.contains(word)) {
            int index = levelList.indexOf(word);
            Word seenWord = levelList.get(index);
            seenWord.addFaulted();
        } else {
            word.addFaulted();
            levelList.add(word);
        }
    }

    public void addFailedWord(Word word) {
        setLevelList();
        if (levelList.contains(word)) {
            int index = levelList.indexOf(word);
            Word seenWord = levelList.get(index);
            seenWord.addFailed();
        } else {
            word.addFailed();
            levelList.add(word);
        }
    }

    /**
     * takes in one integer parameter level, then calculates the cumulative accuracy for all words that have been tested
     * with the same level.
     * @param level
     * @return
     */
    public double calculateLevelAccuracy(int level) {

        int numMasteredOnLevel = 0;
        int wordsAppearedOnLevel = 0;

        if (statistics.containsKey(level)) {

            ArrayList<Word> levelList = statistics.get(level);


            for (Word word : levelList) {
                wordsAppearedOnLevel += word.get_numFailed() + word.get_numFaulted() + word.get_numMastered();
                numMasteredOnLevel += word.get_numMastered();
            }


            if (wordsAppearedOnLevel == 0){

                return 100.0;
            }
        }

        return Math.round(((double)numMasteredOnLevel)/((double)wordsAppearedOnLevel)*100);

    }

    public void clearStats() {
        statistics = new HashMap<Integer,ArrayList<Word>>();
        highScores = new HashMap<Integer,Integer>();
    }



    public ArrayList<Word> statsForLevel(int level) {
        return statistics.get(level);
    }

    public void addHighScore(int score) {
        if (highScores.get(SystemStatus.currentlevel()) == null) {
            highScores.put(SystemStatus.currentlevel(), score);
            SystemStatus.newHighScoreReached();
        } else {
            if (highScores.get(SystemStatus.currentlevel()) < score) {
                highScores.put(SystemStatus.currentlevel(), score);
                SystemStatus.newHighScoreReached();
            }
        }
    }

    public int getHighScore() {
        if (highScores.get(SystemStatus.currentlevel()) != null) {
            return highScores.get(SystemStatus.currentlevel());
        } else {
            return 0;
        }
    }

    public void addScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }


}
