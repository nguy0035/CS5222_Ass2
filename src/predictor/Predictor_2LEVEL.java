/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

import entity.LOC;
import entity.branchHistory_2bitPredictor;
import java.util.ArrayList;

/**
 *
 * @author tatthang
 */
public class Predictor_2LEVEL {
    
    private ArrayList<LOC> program = new ArrayList<LOC>();
    private int num_of_branches ;
    
    private int shared_BHSR;
    private ArrayList<branchHistory_2bitPredictor>[] branchHistory ;
    private int number_of_predictor_per_branch = 4;
    
    public Predictor_2LEVEL(ArrayList<LOC> program, int num_of_branches){
        this.program = program;
        this.num_of_branches = num_of_branches;
        
        this.shared_BHSR = 0;
        branchHistory = new ArrayList[number_of_predictor_per_branch];
        
        for (int j=0;j<number_of_predictor_per_branch;j++)
            branchHistory[j] = new ArrayList<branchHistory_2bitPredictor>();
            
        for (int j=0;j<number_of_predictor_per_branch;j++)
            for (int i=0;i<num_of_branches;i++)
                branchHistory[j].add(new branchHistory_2bitPredictor(i));   
    }

    public boolean predictAtLine(int line){
        
        LOC loc = program.get(line);
        int branch_ID = loc.getBranch_ID();
        boolean predicted_outcome;
       
        predicted_outcome = branchHistory[shared_BHSR].get(branch_ID).DoPredict();
        return predicted_outcome;
    }
    
    public void updatePredictorAtLine(int line,boolean actualPrediction){
        LOC loc = program.get(line);
        int branch_ID = loc.getBranch_ID();
        branchHistory[shared_BHSR].get(branch_ID).UpdateState(actualPrediction);
        update_BHSR(line);
    }
    
    private void update_BHSR(int line){
        LOC loc = program.get(line);
        int branch_ID = loc.getBranch_ID();
        shared_BHSR = branchHistory[shared_BHSR].get(branch_ID).getCurrent_state();
    }
}
