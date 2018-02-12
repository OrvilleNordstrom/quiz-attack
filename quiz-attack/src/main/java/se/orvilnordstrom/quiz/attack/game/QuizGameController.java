package se.orvilnordstrom.quiz.attack.game;

import javafx.util.Callback;

/**
 *
 * @author Orville Sebastian Nordström
 */
public class QuizGameController implements Runnable {

    private final QuizGame quizGame;
    
    private final boolean isActive;
    
    private final Callback<QuizGame, Boolean> repaintGameCallback;
    
    public QuizGameController(Callback<QuizGame, Boolean> repaintGameCallback) {
        
        quizGame = new QuizGame();
        
        isActive = true;
        
        this.repaintGameCallback = repaintGameCallback;
        
    }
    
    @Override
    public void run() {
        
        while(isActive) {
            
            quizGame.checkGameOver();
            
            repaintGameCallback.call(quizGame);
            
            sleep(1);
            
        }
        
    }
    
    public void startGame() {
        quizGame.startGame();
    }
    
    public boolean answer(String answer) {
        
        if(!quizGame.isRunning()) {
            
            quizGame.startGame();
            
            return true;
            
        } else {
            
            return quizGame.answer(answer);
            
        }
        
    }
    
    private void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException ex) {
        }
    }
    
}
