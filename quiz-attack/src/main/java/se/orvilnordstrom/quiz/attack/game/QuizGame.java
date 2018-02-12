package se.orvilnordstrom.quiz.attack.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import quizattack.quiz.Quiz;
import quizattack.quiz.math.AdditionQuiz;

/**
 *
 * @author Orville Sebastian Nordström
 */
public class QuizGame {
    
    private static final int DEFAULT_PENALTY_POINTS = -5;
    
    private static final long STARTING_ANSWER_TIME = 25 * 1000;
    
    private static final double REDUCE_ANSWER_TIME_FACTORY = 0.95;
    
    private long answerTime, answerCreatedMillis, gameTimeMillis;
    
    private int points, correctAnswerCount, attemptedAnswerCount;
            
    private boolean running, gameOver;
        
    private Quiz currentQuiz;
    
    private final Random random;
    
    private final List<Quiz> answeredQuizList;
    
    public QuizGame() {
     
        answeredQuizList = new ArrayList<>();
        
        random = new Random();
        
    }

    public void resetGame() {
                
        points = 0;
        
        answerTime = 0;
        
        answerCreatedMillis = 0;
        
        correctAnswerCount = 0;
        
        attemptedAnswerCount = 0;
        
        running = false;
        
        gameOver = false;
        
        currentQuiz = null;
        
        answeredQuizList.clear();
        
    }
    
    public void startGame() {
        
        resetGame();
        
        answerTime = STARTING_ANSWER_TIME;
        
        running = true;
        
        setNextQuiz();
        
    }
    
    public boolean answer(String answer) {
        
        attemptedAnswerCount++;
        
        if(currentQuiz.getAnswer().equals(answer.trim())) {
            
            correctAnswerCount++;
            
            addPoints(currentQuiz.getPoints());
            
            answeredQuizList.add(currentQuiz);
            
            setNextQuiz();
            
            return true;
            
        } else {
            
            addPoints(getPenaltyPoints());
            
            return false;
            
        }
        
    }
    
    private int getPenaltyPoints() {
        return DEFAULT_PENALTY_POINTS;
    }
    
    public String getCurrentQuiz() {
        if(currentQuiz == null) {
            return "(Loading)";
        } else {
            return currentQuiz.getQuiz();
        }
    }
    
    private void addPoints(int addPoints) {
        
        points += addPoints;
        
        if(points < 0) {
            points = 0;
        }
        
    }
    
    private void setNextQuiz() {
        
        currentQuiz = new AdditionQuiz(random, getCurrentAnswerTime(), correctAnswerCount);
        
        
        
        answerCreatedMillis = System.currentTimeMillis();
        
    }
    
    private long getCurrentAnswerTime() {
             
        answerTime = Math.round(answerTime * REDUCE_ANSWER_TIME_FACTORY);

        return answerTime;
        
    }
    
    /**
     * @return the points
     */
    public int getPoints() {
        return points;
    }

    /**
     * @return the correctAnswerCount
     */
    public int getCorrectAnswerCount() {
        return correctAnswerCount;
    }

    /**
     * @return the gameTimeMillis
     */
    public long getGameTimeMillis() {
        return gameTimeMillis;
    }

    /**
     * @return the running
     */
    public boolean isRunning() {
        return running;
    }

    public long getRemainingAnswerTime() {
        
        if(!running) {
            return 0;
        } 
        
        long timeElapsed = System.currentTimeMillis() - answerCreatedMillis;
        
        return answerTime - timeElapsed;
        
    }
    
    /**
     * @return the gameOver
     */
    public boolean checkGameOver() {
        
        if(gameOver) {
            return true;
        }
        
        long remainingAnswerTime = getRemainingAnswerTime();
                
        if(remainingAnswerTime <= 0) {
            gameOver = true;
            running = false;
            return true;
        } else {
            return false;
        }
                
        
    }
    
}
