/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import entity.LOC;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import predictor.Predictor_2BIT;
import predictor.Predictor_2LEVEL;

/**
 *
 * @author tatthang
 */
public class BP_2LEVEL {
    
    static Set branch_set = new HashSet<Long>();
    static HashMap<Long,Integer> branch_map = new HashMap<Long,Integer>();
    static int num_of_branches = 0;
    static ArrayList<LOC> program = new ArrayList<LOC>();
    
    static int numOfCorrectPrediction = 0;
    
    public static void main(String[] args){
        boolean success;
        String filename = "history.txt";
        success = readFile(filename);
        
        if (!success){
            System.out.println("Read file operation is failed. Program is terminated");
            return;
        }
        
        doPrediction();
        
        printAccuracy();
    }
    
    private static void doPrediction(){
        
        Predictor_2LEVEL twoLEVEL_predictor = new Predictor_2LEVEL(program, num_of_branches);
        
        boolean predicted_outcome = false;
        boolean actual_outcome;
        LOC loc = null;
        
        for (int j=0;j<program.size();j++)
        {           
            loc = program.get(j);
            
            
            predicted_outcome = twoLEVEL_predictor.predictAtLine(j);
            actual_outcome = loc.isTaken();
            twoLEVEL_predictor.updatePredictorAtLine(j, actual_outcome);    
            
            if (predicted_outcome == actual_outcome)
                numOfCorrectPrediction++;
        }
    }
    
    private static void printAccuracy(){
        
        double accuracy = (double)numOfCorrectPrediction / (double)(program.size()) * 100;
        DecimalFormat df = new DecimalFormat("#.###");        
        System.out.println(df.format(accuracy));
    }
    
    private static boolean readFile(String fileName){
        int line = 0;
        File f = new File(fileName);
        Scanner sc = null ;
        String loc = null;
        StringTokenizer str = null;
        
        long BIA = 0,BTA = 0;
        int branch_ID = 0;
        int taken_str;
        boolean taken;
        
        if (!f.exists()){
            System.out.println("No such file exists");
            return false;
        }
        try {
            sc = new Scanner(f);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BP_BTFNT.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (sc.hasNextLine()){
            line++;
            loc = sc.nextLine();
            
            str = new StringTokenizer(loc);
            
            BIA = Long.parseLong(str.nextToken().substring(2),16);
            BTA = Long.parseLong(str.nextToken().substring(2),16);
            taken_str = Integer.parseInt(str.nextToken());
            taken = taken_str == 0 ? false : true;
            
           
            if (!branch_set.contains(BIA))
            {
                num_of_branches++;
                branch_set.add(BIA);
                branch_ID = num_of_branches - 1;
                branch_map.put(BIA,branch_ID);               
            }
            else{
                branch_ID = branch_map.get(BIA);
            }
            
            program.add(new LOC(BIA,BTA,branch_ID,taken,line));
        }
        
        return true;
    }
}
