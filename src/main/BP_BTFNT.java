/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

/**
 *
 * @author tatthang
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.Set;
import java.util.HashSet;
import java.util.HashMap;
import java.util.ArrayList;
import entity.LOC;
import predictor.Predictor_BTFNT;
import java.text.DecimalFormat;

public class BP_BTFNT {
    
    static Set branch_set = new HashSet<Long>();
    static HashMap<Long,Integer> branch_map = new HashMap<Long,Integer>();
    static int num_of_branches = 0;
    static ArrayList<LOC> program = new ArrayList<LOC>();
    
    static int numOfCorrectPrediction = 0;
    
    
    public static void main(String[] args){
        boolean success;
        if (args.length != 1)
        {
            System.out.println("Please check the arguments parameter. Program is terminated");
            return;
        }
        String filename = args[0];
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
        DecimalFormat df = new DecimalFormat("#.###");
        System.out.println(df.format(accuracy));
    }
    private static void doPrediction(){
        
        Predictor_BTFNT BTFNT_predictor = new Predictor_BTFNT(program, num_of_branches);
        LOC loc = null;
        boolean taken = false;
        boolean actual_outcome; 
        for (int j=0;j<program.size();j++)
        {
            loc = program.get(j);
            actual_outcome = loc.isTaken();
            taken = BTFNT_predictor.predictAtLine(j);
            if (taken == actual_outcome)
                numOfCorrectPrediction++;
        }
        
    }
    private static boolean readFile(String fileName){
        int line = 0;
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
            line++;
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
                branch = num_of_branches - 1;
                branch_map.put(BTA,branch);
            }
            else{
                branch = branch_map.get(BTA);
            }
            
            program.add(new LOC(BIA,BTA,branch,taken,line));
        }
        
        return true;
    }
}
