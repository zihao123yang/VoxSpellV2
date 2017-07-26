package sample.dataBase;

import sample.SystemStatus;

import java.io.*;
import java.util.*;

/**
 * Created by zihao123yang on 7/10/16.
 */
public class RevisionData implements QuizDataBase {

    private static RevisionData instance = null;

    private File _revisionFile = new File("revisionList.ser");

    private HashMap<Integer, ArrayList<String>> _revisionList;

    private RevisionData() {

        if (_revisionList == null) {
            createDataBase();
        }
    }

    public static RevisionData getInstance() {

        if (instance == null) {
            instance = new RevisionData();
        }

        return instance;
    }


    public void loadFailed() throws IOException, ClassNotFoundException {
        if (!_revisionFile.exists()) {
            return;
        }
        FileInputStream fileIn = new FileInputStream(_revisionFile);
        ObjectInputStream objectIn = new ObjectInputStream(fileIn);
        _revisionList = (HashMap<Integer, ArrayList<String>>) objectIn.readObject();
        fileIn.close();
        objectIn.close();
    }

    public void saveFailed() throws IOException  {
        FileOutputStream fileOut = new FileOutputStream(_revisionFile);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(_revisionList);
        objectOut.close();
        fileOut.close();
    }


    @Override
    public void createDataBase() {
        _revisionList = new HashMap<Integer, ArrayList<String>>();
    }

    @Override
    public boolean addToDataBase(String word) {

        if (_revisionList.containsKey(SystemStatus.currentlevel())) {
            ArrayList<String> levelList = _revisionList.get(SystemStatus.currentlevel());
            if (!levelList.contains(word)) {
                levelList.add(word);
            }
        } else {
            ArrayList<String> levelList = new ArrayList<String>();
            levelList.add(word);
            _revisionList.put(SystemStatus.currentlevel(), levelList);
        }

        //doesn't matter in this case because cannot remove from revision list manually
        return true;
    }

    @Override
    public boolean removeFromDataBase(String word) {

        ArrayList<String> levelList = _revisionList.get(SystemStatus.currentlevel());
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
        if (_revisionList.containsKey(SystemStatus.currentlevel())) {

            ArrayList levelList = _revisionList.get(SystemStatus.currentlevel());
            randomizeData(levelList);


           if (levelList.size() > 10) {
               ArrayList<String> copyList = new ArrayList<String>(levelList.subList(0, 10));
               Collections.copy(copyList, levelList.subList(0, 10));
               return copyList;
           } else {
               ArrayList<String> copyList = new ArrayList<String>(levelList);
               Collections.copy(copyList, levelList);
               return copyList;
           }
        } else {
            return null;
        }
    }

    public boolean checkForWords(int level) {

        if (_revisionList.containsKey(level)) {
            if(_revisionList.get(level).size() > 0) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void clearData() {
        _revisionList = new HashMap<Integer, ArrayList<String>>();
    }
}
