package sample.dataBase;

import java.util.ArrayList;

/**
 * Created by zihao123yang on 7/10/16.
 */
public interface QuizDataBase {


    public void createDataBase();

    public boolean addToDataBase(String word);

    public boolean removeFromDataBase(String word);

    public void randomizeData(ArrayList<String> levelList);

    public int sizeOfLevel(ArrayList<String> levelList);

    public ArrayList<String> makeQuizList();


}
