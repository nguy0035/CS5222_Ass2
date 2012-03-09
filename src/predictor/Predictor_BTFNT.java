/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package predictor;

/**
 *
 * @author tatthang
 */
import java.util.ArrayList;
import entity.LOC;
public class Predictor_BTFNT {

    private ArrayList<LOC> program = new ArrayList<LOC>();
    private int num_of_branches ;
    
    public Predictor_BTFNT(ArrayList<LOC> program, int num_of_branches){
        this.program = program;
        this.num_of_branches = num_of_branches;
    }
    
    //start at line=0 
    //return true if taken and false otherwise
    public boolean predictAtLine(int line){
        
        LOC loc = program.get(line);
        boolean isForwardBranch = false;
        
        long BIA = loc.getBIA();
        long BTA = loc.getBTA();
        
        if (BTA < BIA)
            isForwardBranch = false; //backward branch
        else
            isForwardBranch = true;
        
        return (isForwardBranch == true ? false : true);
    }
}
