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
public class Predictor_2BIT {
    
    private ArrayList<LOC> program = new ArrayList<LOC>();
    private int num_of_branches ;
    
    private ArrayList<branchHistory_2bitPredictor> branchHistory ;
    
    public Predictor_2BIT(ArrayList<LOC> program, int num_of_branches){
        this.program = program;
        this.num_of_branches = num_of_branches;
        this.branchHistory = new ArrayList<branchHistory_2bitPredictor>();
        
        for (int j=0;j<num_of_branches;j++)
            this.branchHistory.add(new branchHistory_2bitPredictor(j));
    }
    
    public boolean predictAtLine(int line){
        LOC loc = program.get(line);
        
        boolean predicted_outcome = branchHistory.get(loc.getBranch_ID()).DoPredict();
        
        return predicted_outcome;
    }
    
    public void updatePredictorAtLine(int line,boolean actualPrediction){
        LOC loc = program.get(line);
        branchHistory.get(loc.getBranch_ID()).UpdateState(actualPrediction);
    }
    
}
