/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import entity.LOC;
import java.util.ArrayList;

/**
 *
 * @author tatthang
 */
public class Predictor_TOURNAMENT {
    private ArrayList<LOC> program = new ArrayList<LOC>();
    private int num_of_branches ;
    
    private Predictor_2LEVEL twoLEVEL_predictor;
    private Predictor_2BIT twoBIT_predictor;
    
    public int counter_2BIT;
    private int counter_2LEVEL;
    
    private boolean beforeUpdate_predicted_outcome_2BIT;
    private boolean beforeUpdate_predicted_outcome_2LEVEL;
    
    public Predictor_TOURNAMENT(ArrayList<LOC> program, int num_of_branches){
        this.program = program;
        this.num_of_branches = num_of_branches;
        
        twoBIT_predictor = new Predictor_2BIT(this.program, this.num_of_branches);
        twoLEVEL_predictor = new Predictor_2LEVEL(this.program, this.num_of_branches);
        
        this.counter_2BIT = 0;
        this.counter_2LEVEL = 0;
    }
    
    public boolean predictAtLine(int line){
        
        boolean predicted_outcome_2BIT = twoBIT_predictor.predictAtLine(line);
        boolean predicted_outcome_2LEVEL = twoLEVEL_predictor.predictAtLine(line);
        
        beforeUpdate_predicted_outcome_2BIT = predicted_outcome_2BIT;
        beforeUpdate_predicted_outcome_2LEVEL = predicted_outcome_2LEVEL;
        
        if (counter_2BIT >= counter_2LEVEL)
            return predicted_outcome_2BIT;
        else 
            return predicted_outcome_2LEVEL;
    }
    
    public void updatePredictorAtLine(int line,boolean actualPrediction){
        
        if (actualPrediction == beforeUpdate_predicted_outcome_2BIT)
            counter_2BIT++;
        if (actualPrediction == beforeUpdate_predicted_outcome_2LEVEL)
            counter_2LEVEL++;
        
        twoBIT_predictor.updatePredictorAtLine(line, actualPrediction);
        twoLEVEL_predictor.updatePredictorAtLine(line, actualPrediction);
    }
    
}
