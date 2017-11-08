/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerhands;

import java.util.Arrays;
import static pokerhands.Card.getRandomCard;

/**
 *
 * @author senna.vanlondersele
 */
public class Hand {
    Card[] cards = new Card[5];
    
    public Hand(){
        for(int i = 0; i < cards.length; i++){
            cards[i] = getRandomCard();
        }
    }
    
    public Hand(Card[] cards){
        this.cards = cards;
    }
    
    public boolean isFlush(){
        for (int i = 0; i < cards.length; i++) {
                for (int j = 0; j < cards.length; j++) {
                    if(i != j){
                        if(!cards[i].getSymbol().equals(cards[j].getSymbol())){
                            return false;
                        }
                    }
                }
            }
        return true;
    
    }
    
    public boolean isStraightFlush(){
        if(isFlush()){
            Arrays.sort(cards);
            for (int i = 0; i < cards.length; i++) {
                for (int j = 0; j < cards.length; j++) {
                    
                    if(i!=j){
                        if(cards[i].getValue().rank - cards[j].getValue().rank != -1){
                            return false;
                        }   
                    }
                }
            }
            return true;
        }        
        return false;
    }
}
