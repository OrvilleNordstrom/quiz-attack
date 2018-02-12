package se.orvilnordstrom.quiz.attack.quiz.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import se.orvilnordstrom.quiz.attack.quiz.Quiz;

/**
 *
 * @author Orville Sebastian Nordström
 */
public class AdditionQuiz implements Quiz {
        
    private static final int DEFAULT_BOUND = 6;
    
    private final int answer, points, operandBond;
    
    private final long time;
    
    private final String quizText;
  
    private List<Integer> operandList = new ArrayList<>();
    
    public AdditionQuiz(Random random, long time, int currentQuestionIndex) {
        
        operandBond = generateBond(currentQuestionIndex);

        int operandA = random.nextInt(operandBond);
        
        int operandB = random.nextInt(operandBond);
        
        answer = operandA + operandB;
        
        quizText = operandA + " + " + operandB;
    
        points = ((operandA + operandB) / 2) + 5;
        
        this.time = time;
        
    }
    
    public int generateBond(int currentQuestionIndex) {
        return DEFAULT_BOUND + (currentQuestionIndex / 2);
    }
    
    @Override
    public String getQuiz() {
        return quizText;
    }
    
    @Override
    public String getAnswer() {
        return answer + "";
    }

    @Override
    public int getPoints() {
        return points;
    }

    @Override
    public long getTime() {
        return time;
    }
    
}