/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author tatthang
 */
public class LOC {
    private long BIA;
    private long BTA;
    private int branch_ID;
    private boolean taken;
    public LOC(long BIA, long BTA, int branch_ID,boolean taken) {
        this.BIA = BIA;
        this.BTA = BTA;
        this.branch_ID = branch_ID;
        this.taken = taken;
    }

    public long getBIA() {
        return BIA;
    }

    public void setBIA(long BIA) {
        this.BIA = BIA;
    }

    public long getBTA() {
        return BTA;
    }

    public void setBTA(long BTA) {
        this.BTA = BTA;
    }

    public int getBranch_ID() {
        return branch_ID;
    }

    public void setBranch_ID(int branch_ID) {
        this.branch_ID = branch_ID;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }
    
    
}
