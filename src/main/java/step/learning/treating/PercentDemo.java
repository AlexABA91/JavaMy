package step.learning.treating;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.*;

public class PercentDemo {
    private double sum; // общее ресурсы разные потоки будут его плюсовать
    private final Random random = new Random();
    private final Object sumLock = new Object(); // объект синхронизации - свободного
    // многозадачность начинается с настройки исполнителей, чаще всего это пул
    // ограниченное количество одновременно запущенных потоков
    private final ExecutorService pool = Executors.newFixedThreadPool(5);
    public void ThreadSum(){
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
    public void runOneTask(){
        // Многозадачность Future - главный интерфейс для задач
         Future<String> task1 = taskString();
         // код что выполняется паралельно с задачай
        System.out.println("Parallel");
        // Ожидание завершения задачи
        ;
        try {
          String resul = task1.get();
            System.out.println(resul);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        // чтобы основной поток не заканчивался до конца задач, пул потоков нужно заканчивать
        // принудительно. Если есть задачи закрытия пула завершит их
        pool.shutdown();
    }
    public void runForTask (){
        for (int i = 0; i < 15; i++) {
            printHello(i +1 );
        }
        try {
            // ожидание завершения всех задач, но не дольше временного ограничения
            pool.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        pool.shutdown();
    }
    public void run(){
        int month =12;
        sum = 100;
        for (int i = 0; i < month; i++) {
            PercentAdderTask(i+1);
        }
        pool.shutdown();
    }
    Future<?> printHello(int number){
        return pool.submit(()->{

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.println("Hello"+ number);
        });
    };

    Future<String> taskString(){       // Future<String> - задача что возвращает результат String
      return pool.submit(()-> {        // добавление задачи в очередь на выполнение
          Thread.sleep(1500);     // Callable - аналог Runnable() но с возвращением результата
          return "Hello";
      });
    }

    class PercentAdder extends Thread{

        private final Random random = new Random();
        public PercentAdder(int month) {
            this.month = month;
        }

        @Override
        public void run() {

            double percentage = Double.parseDouble(String.format(Locale.US,"%.1f",(1 + (2 - 1) * random.nextDouble())));;
            System.out.println("Month "+ month + " started");
         // имитируем долгий запрос к api
            int receivingTime = random.nextInt(300)+200;// 200-500 мс на запрос
            try {
                Thread.sleep(receivingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            double   localSum;
            synchronized (sumLock){
                localSum= sum;
                localSum *=percentage ; // конкуренция потоков - одновременная работа с общим потоком
                sum = localSum;
            }
            System.out.println("Month "+ month + " Percentage "+ percentage +" sum before = "+ localSum+ " finished with sum = " + sum);
        }
        private final int month;
    }

     Future<?> PercentAdderTask(int month){
        return pool.submit(()->{
            double percentage = Double.parseDouble(String.format(Locale.US,"%.1f",(1 + (2 - 1) * random.nextDouble())));;
            System.out.println("Month "+ month + " started");
            int receivingTime = random.nextInt(300)+200;// 200-500 мс на запрос
            try {
                Thread.sleep(receivingTime);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            double   localSum;
            synchronized (sumLock){
                localSum = sum;
                sum *= percentage ; // конкуренция потоков - одновременная работа с общим потоком

            }
            System.out.println("Month "+ month + " Percentage "+ percentage +" sum before = "+ localSum+ " finished with sum = " + sum);
        });
     }
}
//   конкуренция потоков - одновременная работа с общим потоком
// решение проблем с конкуренцией - задача синхронизации потоков

/*Синхронизация потоков (задач)
 Идея: создание транзакция - которая объединяет несколько операций в единый блок который не может быть прерваний другим потоком (задачей)
 Достигается системными инструментами "сигнальными объектами" - критичная секция, мьютекс, семофор, и т.д.
 Их набивают сигнальными объектами в понимании того что их изменения приводит к определенному сигнала который ожидает поток
 В JAva (JVM) и .NET все объекты автоматически имеют в своем составе имеют критическую секцию, которая использоваться для задач синхронизации.
 Средства языка имеют специальные операторы для упрощения управления этой секцией
 Java -- synchronized, C# -- lock
     = оглашаем объект свободного типа (reference type) - можно object можно и другого типа
       ак как от них нужна критическая секция
     = Объект должен быть доступный во всех потоках задачах, в которых нужна синхронизация
     = Выделяем "Транзакцию" - набор команд, которые не должны быть прерванные
     !! Намагаємось зробити цю область мінімальною. Чим вона більше, тип
       гірша паралельність. Якщо синхронізується все тіло, то паралельність зникає -
       все виконується послідовно
       Часто для цього треба переорганізувати код, зокрема, ввести локальні змінні
 = !! стежимо за тим, щоб протягом виконання синхронізованого блоку об'єкт
      синхронізації не зазнав змін
      synchronized(collection) {     -- нормально
         collection.add( object ) ;  -- колекція не змінює посилання
      }
      synchronized(text) {     -- не нормально
         text += "addon" ;     -- рядок змінюється - через додавання утворюється новий
      }
      блокується text (закривається його критична секція)
      після чого у змінну text передається новий об'єкт з відкритою секцією,
        відповідно інший блок synchronized(text) не буде зупинений
      по закінченню блоку відбудеться відкриття секції але у відкритого об'єкту - це конфлікт
      початковий об'єкт text передається на GC із закритою секцією (dead lock)

   = Рекомендация - задействовать в синхроблоке константные объекты
* */