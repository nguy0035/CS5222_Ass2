/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author tatthang
 */
public class branchHistory_2bitPredictor {
    
    private int current_state;
    
    private int STATE_NN = 0;
    private int STATE_NT = 1;
    private int STATE_TN = 2;
    private int STATE_TT = 3;
    
    private int branch_ID;
    
    public branchHistory_2bitPredictor(int branch_ID){
        this.branch_ID = branch_ID;
        this.current_state = STATE_NN;
    }
    
    public boolean DoPredict(){
        boolean taken = false;
        
        if (current_state == STATE_NN)
            taken = false;
        else
            taken = true;
        
        
        return taken ;
    }
    
    //actualPrediction = false => not taken ; actualOutcome = true => taken
    public void UpdateState(boolean actualOutcome){
        if (current_state == STATE_NN){
            if (actualOutcome)
                current_state = STATE_NT;
        }
        else if (current_state == STATE_NT){
            if (actualOutcome)
                current_state = STATE_TT;
            else
                current_state = STATE_TN;
        }
        else if (current_state == STATE_TN){
            if (actualOutcome)
                current_state = STATE_NT;
            else
                current_state = STATE_NN;
        }
        else if (current_state == STATE_TT){
            if (!actualOutcome)
                current_state = STATE_TN;
        }
    }
}
