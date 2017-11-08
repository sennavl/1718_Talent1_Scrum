/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerhands;

import java.util.Random;

/**
 *
 * @author senna.vanlondersele
 */
public class Card implements Comparable<Card>{
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Card other = (Card) obj;
        if (this.value != other.value) {
            return false;
        }
        if (this.symbol != other.symbol) {
            return false;
        }
        return true;
    }
    
    private Value value;

    public Value getValue() {
        return value;
    }

    public Symbol getSymbol() {
        return symbol;
    }
    private Symbol symbol;
    
    public Card(Value value, Symbol symbol){
        this.value = value;
        this.symbol = symbol;
    }
    
    public static Card getRandomCard(){
        Value[] vals = Value.values();
        Random random = new Random();
        
        Symbol[] symbs = Symbol.values();
        
        Card randomCard = new Card(vals[random.nextInt(vals.length)], symbs[random.nextInt(symbs.length)]);
        return randomCard;
    }    

    @Override
    public int compareTo(Card card2) {
        return this.value.rank - card2.value.rank;
    }
}