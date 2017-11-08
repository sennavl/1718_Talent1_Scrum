/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pokerhands;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author senna.vanlondersele
 */
public class PokerHandsTest {
    
    public PokerHandsTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class PokerHands.
    /
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        PokerHand.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }*/
    
    @Test
    public void testValue(){
        Card firstCard = new Card(Value.King, Symbol.heart);
        
        assertEquals(Value.King, firstCard.getValue());
        assertEquals(Symbol.heart, firstCard.getSymbol());
    }
    
    @Test
        public void testHand(){

            Hand firstHand = new Hand();
            assertEquals(true, checkUniqueCards(firstHand));
        }

        private boolean checkUniqueCards(Hand hand){
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if(i != j){
                        if(hand.cards[i].equals(hand.cards[j])){

                            return false;
                        }
                    }
                }
            }
            return true;
        }
        
    @Test
    public void testFlush(){
        Card card1 = new Card(Value.Ace, Symbol.club);
        Card card2 = new Card(Value.King, Symbol.club);
        Card card3 = new Card(Value.Ten, Symbol.club);
        Card card4 = new Card(Value.Eight, Symbol.club);
        Card card5 = new Card(Value.Two, Symbol.club);
        
        Card[] cards = new Card[5];
        
        cards[0] = card1;
        cards[1] = card2;
        cards[2] = card3;
        cards[3] = card4;
        cards[4] = card5;
        
        Hand hand = new Hand(cards);
        
        assertEquals(true, hand.isFlush());
    }
    
    @Test
    public void testStraightFlush(){
        Card card1 = new Card(Value.Three, Symbol.club);
        Card card2 = new Card(Value.King, Symbol.club);
        Card card3 = new Card(Value.Seven, Symbol.club);
        Card card4 = new Card(Value.Eight, Symbol.club);
        Card card5 = new Card(Value.Two, Symbol.club);
        
        Card[] cards = new Card[5];
        
        cards[0] = card1;
        cards[1] = card2;
        cards[2] = card3;
        cards[3] = card4;
        cards[4] = card5;
        
        Hand hand = new Hand(cards);
        
        assertEquals(true, hand.isStraightFlush());
    }

}
