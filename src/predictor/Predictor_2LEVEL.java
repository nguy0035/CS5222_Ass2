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
    
    public void updatePredictorAtLine(int line,boolean actual_outcome){
        LOC loc = program.get(line);
        int branch_ID = loc.getBranch_ID();
        branchHistory[shared_BHSR].get(branch_ID).UpdateState(actual_outcome);
        update_BHSR(actual_outcome);
    }
    
    private void update_BHSR(boolean actual_outcome){
        if (actual_outcome == false){
            if (shared_BHSR == 0 )
                shared_BHSR = 0 ;
            else if (shared_BHSR == 1 )
                shared_BHSR = 2;
            else if (shared_BHSR == 2 )
                shared_BHSR = 0;
            else if (shared_BHSR == 3 )
                shared_BHSR = 2;
        }
        else {
            if (shared_BHSR == 0 )
                shared_BHSR = 1 ;
            else if (shared_BHSR == 1 )
                shared_BHSR = 3;
            else if (shared_BHSR == 2 )
                shared_BHSR = 1;
            else if (shared_BHSR == 3 )
                shared_BHSR = 3;
        }
             
    }
}
