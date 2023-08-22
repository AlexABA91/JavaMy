package step.learning.ioc;
import com.google.inject.Inject;
import com.google.inject.name.Named;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IocApp {
    /* @Inject
    private GreetingService helloService;
    @Inject @Named("bye")
    private PartingService byeService;
    @Inject @Named("goodbye")
    private PartingService goodByeService;
    */
    @Inject
    private GreetingService helloService;
    private final PartingService byeService;
    private final PartingService goodByeService;
    @Inject @Named("md5")
    private HashService md5;
    private final HashService sha1;
    private final Random random;
    @Inject @Named("planetConnection")   // Возможна "Смешенная Инжекция" - и через конструктор
    private String connectionString;     //  и через поля - обе работаю одновременно
    @Inject @Named("logfileName")
    private String logfileName;
    @Inject @Named("jur")
    Random random2;
    private final Logger logger; // Guise автоматом поставляет Logger (java.util)
   @Inject
    public IocApp(

                  @Named("bye") PartingService byeService,
                  @Named("goodbye") PartingService goodByeService,
                  @Named("sha1") HashService sha1,
                  @Named("jur") Random random,
                  Logger logger
                  ) {
        this.byeService = null;
        this.goodByeService = goodByeService;
        this.sha1 = null;
        this.random = random;
        this.logger = logger;
    }


    public void run(){
        System.out.println("App Work");

        if (connectionString == null) {
            logger.log(Level.SEVERE, "connectionString is Null");
        } else {
            logger.log(Level.INFO, "connectionString is Ok");
            System.out.println( connectionString);
        }

        if (helloService == null) {
            logger.log(Level.SEVERE, String.format("SEVERE: Служба %s NULL", GreetingService.class));
        } else {
            logger.log(Level.SEVERE, String.format("INFO: Служба %s инициализирована", GreetingService.class));
        }

        if (byeService == null) {
            logger.log(Level.SEVERE, String.format("SEVERE: Служба %s NULL", PartingService.class));
        } else {
            logger.log(Level.SEVERE, String.format("INFO: Служба %s инициализирована", PartingService.class));
        }

        if (goodByeService == null) {
            logger.log(Level.SEVERE, String.format("SEVERE: Служба %s NULL", PartingService.class));
        } else {
            logger.log(Level.SEVERE, String.format("INFO: Служба %s инициализирована", PartingService.class));
        }

        if (md5 == null) {
            logger.log(Level.SEVERE, String.format("SEVERE: Служба %s NULL", HashService.class));
        } else {
            logger.log(Level.SEVERE, String.format("INFO: Служба %s инициализирована", HashService.class));
        }

        if (sha1 == null) {
            logger.log(Level.SEVERE, String.format("SEVERE: Служба %s NULL", HashService.class));
        } else {
            logger.log(Level.SEVERE, String.format("INFO: Служба %s инициализирована", HashService.class));
        }
        if (random == null) {
            logger.log(Level.SEVERE, String.format("SEVERE: Служба %s NULL", Random.class));
        } else {
            logger.log(Level.SEVERE, String.format("INFO: Служба %s инициализирована", Random.class));
        }

//        System.out.println(logfileName);
//        System.out.println( random.nextInt());
//        System.out.println(random.hashCode() +" "+ random2.hashCode() );
//        logger.log(Level.INFO,"Logger info");
//        logger.log(Level.WARNING,"Logger Warning");
//        logger.log(Level.SEVERE,"Logger Severe");

       // helloService.sayHello();
       // byeService.sayGoodbye();
       // goodByeService.sayGoodbye();
//        System.out.print("Введите строку : ");
//        Scanner scanner = new Scanner(System.in);
//
//        String text = scanner.nextLine();
//        System.out.printf( "MD5 From : %s -- To : %s \n",text, md5.getHash(text));
//        System.out.printf( "SHA1 From : %s -- To : %s \n",text, sha1.getHash(text));


    }
}
/*
------------Инверсия управления (inversion of control), инжекция зависимостей (DI dependency injection)-----------
Управления чем? Жизниным циклом объектов
    - Без инверсии (обычное управление)
        instance = new Type() - создание объекта
        instance = null - "уничтожения" - выдача до GC
    - С инверсией
        service <- type [singleton] (Регистрация)
        @Inject instance  (Резолюция)---- Средство Ioc даст ссылку на объект

SOLID
 O - open/closed - дополняй но не меняй
 D - DIP dependency inversion (не injection) principle
     не рекомендуется зависимость от конкретного тита а  от интерфейса (абстрактного типа)
 Пример :
        разрабатываем новую версию (улучшаем шифратор Cipher)
        (не рекомендуйся ) - вносить изменения в клас Cipher
        (рекомендуемся) - создаем наследника CipherNew и изменять
        зависимость от Cipher на CipherNew
        = для упрощения второго этапа применяемся DIP:
        вместо того чтобы создавать зависимость от класс (Cipher)
        private Cipher cipher
        Желательно создать интерфейс и ввести зависимость через него
        ICipher
        private ICipher cipher
        а в IOC  что под интерфейсом ICipher будет класс Cipher
        это значительно упростит замену новой реализации на CipherNew и обратные изменения
Техника:
        К проекту добавляется инвертор (инжектор), например, Google Guice
        Стартовая точка настраивает сервисы, решает (resolve) первый класс
        (чаше за все App)
        другие классы вместо создания новых объектов-служб указывают на зависимость от них
       - Google Guice - maven зависимость
       -класс iocApp
       -Main (см. Main)

* */

/*
Принципы инжекции
а) если класс известен в проекте инжектор может применять объект данного класса без дополнительных
конфигураций достаточно инжектировать @Inject (приватные поля инжектируются)
  недостаток инжекции через поля - они остаются переменными (не константными )б
б) через конструктор возможна инициализация неизменных полей (Final)
  преимущество
    - защита ссылок на службы
    - защита от создания объекта без служб (наличие конструктора убирает конструктор по умолчанию)
*) Правело инжекторов - если у класса есть несколько конструкторов
   то брать з наибольшим количеством параметров

* */