package step.learning.treating;

import javax.naming.ldap.PagedResultsControl;
import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Random;

public class PercentDemo {
    private double sum; // общее ресурсы разные потоки будут его плюсовать
    private final Random random = new Random();
    public void run(){
        sum = 100;
        int month = 12;
        Thread[] threads = new Thread[month];
        for (int i = 0; i < month; i++) {
            threads[i] = new PercentAdder(i+1);
            threads[i].start();
        }
        try {
            for (int i = 0; i < month; i++) {
                threads[i].join();
            }

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Final sum = " + sum);
    }
    class PercentAdder extends Thread{

        private final Random random = new Random();
        public PercentAdder(int month) {
            this.month = month;
        }

        @Override
        public void run() {
            double localSum = sum;

            double percentage = Double.parseDouble(String.format(Locale.US,"%.1f",(1 + (2 - 1) * random.nextDouble())));;

            System.out.println("Month "+ month + " started");
         // имитируем долгий запрос к api
            int receivingTime = random.nextInt(300)+200;// 200-500 мс на запрос

            try {
                Thread.sleep(receivingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //localSum*= 1.1;
            sum*=percentage ; // конкуренция потоков - одновременная работа с общим потоком
            System.out.println("Month "+ month + " Percentage "+ percentage +" sum before = "+ localSum+ " finished with sum = " + sum);
        }
        private final int month;
    }
}
//   конкуренция потоков - одновременная работа с общим потоком
// решение проблем с конкуренцией - задача синхронизации потоков