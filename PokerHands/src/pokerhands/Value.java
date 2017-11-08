/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerhands;

/**
 *
 * @author senna.vanlondersele
 */
public enum Value {
    Ace(13), King(12), Queen(11), Ten(10), Nine(9), Eight(8), Seven(7), Six(6), Five(5), Four(4), Three(3), Two(2), One(1);
    
    public int rank;
    
    Value(int rank){
        this.rank = rank;
    }
}
