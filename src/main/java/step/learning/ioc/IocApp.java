package step.learning.ioc;
import com.google.inject.Inject;
import com.google.inject.name.Named;

public class IocApp {
    /* @Inject
    private GreetingService helloService;
    @Inject @Named("bye")
    private PartingService byeService;
    @Inject @Named("goodbye")
    private PartingService goodByeService;
    */
    private final GreetingService helloService;
    private final PartingService byeService;
    private final PartingService goodByeService;
   @Inject
    public IocApp(GreetingService helloService, @Named("bye") PartingService byeService, @Named("goodbye") PartingService goodByeService) {
        this.helloService = helloService;
        this.byeService = byeService;
        this.goodByeService = goodByeService;
    }


    public void run(){
        System.out.println("App Work");
        helloService.sayHello();
        byeService.sayGoodbye();
        goodByeService.sayGoodbye();
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
а) если класс известен в проекте  инжектор может применять объект данного класса без дополнительных
конфигураций достаточно инжектировать @Inject (приватные поля инжектируются)
  недостаток инжекции через поля - они остаются переменными (не константными )б
 б) через конструктор возможна инициализация неизменных полей (Final)
  преимущество
    - защита ссылок на службы
    - защита от создания объекта без служб (наличие конструктора убирает конструктор по умолчанию)
*) Правело инжекторов - если у класса есть несколько конструкторов
   то брать з наибольшим количеством параметров

* */