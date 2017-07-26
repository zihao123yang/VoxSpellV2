package sample.dataBase;

/**
 * Created by zihao123yang on 7/10/16.
 */
public class CurrentQuizStats {


        int _numWords;
        int _mastered;
        int _faulted;
        int _failed;
        int _wordsTested;

        int _sessionScore;

        /**
         * initialises the statistics object
         */
        public CurrentQuizStats() {
            _numWords = 0;
            _mastered = 0;
            _faulted = 0;
            _failed = 0;
            _wordsTested = 0;
            _sessionScore = 0;
        }

        /**
         * sets number of words
         * @param num
         */
        public void setNumWords(int num) {
            _numWords = num;
        }

        public int getNumWords() {
            return _numWords;
        }


        /**
         * increments the number of times a word has been mastered and the times it's been tested by 1
         */
        public void increaseMastered() {
            _mastered++;
            _wordsTested++;
            System.out.println("increased master");
        }

        /**
         * increments the number of times a word has been faulted and the times it's been tested by 1
         */
        public void increaseFaulted() {
            _faulted++;
            _wordsTested++;
        }

        /**
         * increments the number of times a word has been and the times it's been tested by 1
         */
        public void increaseFailed() {
            _failed++;
            _wordsTested++;
        }

        /**
         * returns accuarcy as a percentage
         * @return
         */
        public double calculateAccurracy() {


            return Math.round(((double)_mastered)/((double)_wordsTested)*100);
        }


        /**
         * returns a boolean value to indicate if the current level has been passed at the end of a test
         * @return
         */
        public boolean levelPassed() {
            if (_mastered >= 9) {
                return true;
            } else {
                return false;
            }
        }

        public void add1000Score() {
            _sessionScore += 1000;
        }

        public void add500() {
            _sessionScore += 500;
        }

        public int currentScore() {
            return _sessionScore;
        }

        public int getWordsTest() {
            return _wordsTested;
    }
}
