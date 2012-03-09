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
import predictor.Predictor_BTFNT;

/**
 *
 * @author tatthang
 */
public class BP_2BIT {
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
    
    private static void printAccuracy(){
        
        double accuracy = (double)numOfCorrectPrediction / (double)(program.size()) * 100;
        System.out.println(numOfCorrectPrediction);
        System.out.println(program.size());
        
        DecimalFormat df = new DecimalFormat("#.###");

        System.out.println(df.format(accuracy));
    }
    
    private static void doPrediction(){
        
        Predictor_2BIT twoBIT_predictor = new Predictor_2BIT(program, num_of_branches);
        
        boolean taken = false;
        boolean actual_outcome;
        LOC loc = null;
        for (int j=0;j<program.size();j++)
        {           
            loc = program.get(j);
            taken = twoBIT_predictor.predictAtLine(j);
            actual_outcome = loc.isTaken();
            twoBIT_predictor.updatePredictorAtLine(j, actual_outcome);
            
            if (taken == loc.isTaken())
                numOfCorrectPrediction++;
        }
    }
    
    private static boolean readFile(String fileName){
        File f = new File(fileName);
        Scanner sc = null ;
        String loc = null;
        StringTokenizer str = null;
        
        long BIA = 0,BTA = 0;
        int branch = 0;
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
            loc = sc.nextLine();
            
            str = new StringTokenizer(loc);
            BIA = Long.parseLong(str.nextToken().substring(2),16);
            BTA = Long.parseLong(str.nextToken().substring(2),16);
            taken_str = Integer.parseInt(str.nextToken());
            taken = taken_str == 0 ? false : true;
            
            if (!branch_set.contains(BTA))
            {
                num_of_branches++;
                branch_set.add(BTA);
                branch_map.put(BTA,num_of_branches);
                branch = num_of_branches;
            }
            else{
                branch = branch_map.get(BTA);
            }
            
            program.add(new LOC(BIA,BTA,branch,taken));
        }
        
        return true;
    }
}
