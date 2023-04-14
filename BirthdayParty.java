// Lester Miller
// COP 4520 Parallel and Distributed Process
// Instructor Juan Parra
// Homework 03
// UCF Spring 2023
// Due April 13, 2023

import java.util.Arrays;
import java.util.List;
import java.util.Collections;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.locks.ReentrantLock;


public class BirthdayParty {

    // class Servant implements Runnable{
    //     @Override
    //     public void run() {
    //             //System.out.println("Servant "+Thread.currentThread().getName()+" reporting for duty!");  
                    
    //     }
    // }
    
    static AtomicInteger counter = new AtomicInteger(0);
    static int totalGifts = 500000;
    static LazyList giftChain = new LazyList();
    static Integer[] gifts = new Integer[totalGifts];
    static ConcurrentLinkedDeque giftBag = new ConcurrentLinkedDeque();

    
    public static void main(String[] args) {
        
        
        //long startTime = System.currentTimeMillis(); 
       
        
        
        // creates 500,000 gifts
        for (int i = 0; i < totalGifts; i++) 
            gifts[i] = i;
    
        // shuffles the gifts
        List<Integer> intList = Arrays.asList(gifts);
        Collections.shuffle(intList);

        // places the gifts in a deque that will act as the gift bag.
        giftBag = new ConcurrentLinkedDeque(intList);

        // int j = 0;
        // int getGift() {
            
        //     synchronized(giftBag) {
        //         if(j < totalGifts && !giftBag.isEmpty()) {
        //             j++;
        //             int ans = giftBag.pop();
        //             return ans;
        //         }
        //         return -1;
        //     }
        // }
        
        
        
        
        Thread servant1 = new Thread(new Servant());
        Thread servant2 = new Thread(new Servant());
        Thread servant3 = new Thread(new Servant());
        Thread servant4 = new Thread(new Servant());

        servant1.start();
        servant2.start();
        servant3.start();
        servant4.start();

        // long endTime = System.currentTimeMillis();
        // long duration = (endTime - startTime); 
        // System.out.println(duration);
    }
}