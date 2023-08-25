package step.learning.treating;

import javax.xml.bind.SchemaOutputResolver;

public class TreadDemo {
    public  void run(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Start");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }
                System.out.println("Finish thread");
            }
        }).start();
        InfoRunnable info = new InfoRunnable("Hello");
        Thread infoTread = new Thread(info);
        infoTread.start();

        new Thread(()->System.out.println("Start lambda")).start();
        new Thread(this::forThreads).start();
        try {
            infoTread.join();// wait for infoThread (~ await infoThread)
            System.out.println("Output from infoThread: " + info.getOutputData());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Min Finishes");
    }
    static class InfoRunnable implements Runnable
    {
        private final String inputData;
        private String outputData;

        public String getOutputData() {
            return outputData;
        }

        public InfoRunnable(String inputData) {
            this.inputData = inputData;
        }

        @Override
        public void run() {
            System.out.println("Processing  "+ inputData);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
            }
            System.out.println("Finish infoThread");
            outputData = "Processing result";
        }
    }
    private  void forThreads(){
        System.out.println( "Start forThread");
    }
}
/*
* Многопоточность и асинхронность
* (А)Синхронность - распределение кода во времени
*  - синхронный код - код выполняется последовательно инструкция за инструкцией
*  - асинхронность - возможность в некоторых промежутках времени одновременного выполнения процессов
*
* -----  Многопоточность - программирование с использованием системных объектов "поток"
*
 ------ Многопроцессорность - выполнение нескольких системных объектов "процесс"
* Grid, Network - технологии - использование узлов обеденных в сети

*------  Многозадачность - реализация кода (программы) с использованием программных сущностей "задача"
* реализация многозадачности может быть в одном потоке так и в нескольких.
 * */

/*
Многопоточность - робота с объектами класса Thread
 конструктор класса принимает то что будет выполниться в отдельном потоке, а том числе реализацию
 интерфейса Runnable(функциональный интерфейс - и. с одним методам).
 Запускается поток методом start() [! метод run() запускается синхронно]
  - !! Запуск через start() создает поток с приоритетом Normal, это значит что он не зависеть
  от основного потока и будет продолжать работу после завершения основного потока(завершения всей программы)
 Метод Runnable().run() не принимает параметры не возвращает значение, для необходимости возвращение значения
 разрабатывается класс (Thread или Runnable), к ним добавляются поля через которые осуществляется передача и возвращение значений
 !! Если необходимо возвращения значения нужно обеспечить ожидание завершения потока, иначе поток с которого будет запущен поток
 завершится до возвращения значения

Использование многопоточность:
 - долгие задачи
    Thread t1 = connectionToDb();
    t1.start()
    .... другие задачи не связанные с базой данных
    t1.join() // ждем завершения потока
    t1.getConnection() // используем данные из потока

    Если задать больше их стартуют в обратном порядке к длительности с начала самые долгие.
    ! Наоборот, плохим стилем есть последовательное ожидание задач/потоков

 - Задачи порядка независимые - последовательность действий в которых может быть независимый
   например задача подсчета сумм Ж порядок сложения данных не имеет роли
   Идея : посчитать годовую инфляцию если известны проценты за каждый месяц
* */

