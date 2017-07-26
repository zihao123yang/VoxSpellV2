package sample;

/**
 * Representation of levels in the application
 * Created by zihao123yang on 21/09/16.
 */
public class SystemStatus {

    private static int _levelUnlocked = 0;
    private static int _currentLevel = 0;
    private static String _quizType;
    private static int _numberOfLevels = 0;
    private static boolean _newHighScore = false;

    public static void setLevel(int level) {
        _currentLevel = level;
    }

    public static int currentlevel() {

        return _currentLevel;
    }

    public static void setUnlockedlevel(int level) {

        if (level > _levelUnlocked) {
            _levelUnlocked = level;
        }
    }

    public static int Unlockedlevel() {

        return _levelUnlocked;
    }


    public static void goNextLevel() {


        _currentLevel++;

    }

    public static void nextLevelUnlocked() {
        if (_currentLevel == _levelUnlocked) {
            _levelUnlocked++;
        }
    }

    public static String quizType() {
        return _quizType;
    }

    public static void setQuizType(String type) {

        if (type == "new" || type == "revision" || type == "custom") {
            _quizType = type;
        } else {
            System.out.println("wrong quiz type provided");
        }
    }

    public static void setNumberofLevels(int num) {
        _numberOfLevels = num;
    }

    public static int numOfLevels() {
        return _numberOfLevels;
    }

    public static void newHighScoreReached() {
        _newHighScore = true;
    }

    public static boolean isNewHighScore() {
        return _newHighScore;
    }

    public static void resetHighScoreFlag() {
        _newHighScore = false;
    }


}
