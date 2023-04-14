import java.util.Random;

public class Servant implements Runnable{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+" reporting for duty!");  
        int count = BirthdayParty.counter.get();
        while (count < BirthdayParty.totalGifts) {

            Random rand = new Random();
            int n = rand.nextInt(10);

            if (n % 2 == 0){
                int value = (int)BirthdayParty.giftBag.pop();
                System.out.println(Thread.currentThread().getName()+" retrieved gift "+value);
                BirthdayParty.giftChain.add(value);
                System.out.println(Thread.currentThread().getName()+" placed gift "+value+" in the gift chain.");
                
                count = BirthdayParty.counter.incrementAndGet();
            }

            else {
                System.out.println(Thread.currentThread().getName()+" is removing a gift from the gift chain and writing a thank you card.");
                BirthdayParty.giftChain.remove();
            }
                

            
        }

        
    }
}