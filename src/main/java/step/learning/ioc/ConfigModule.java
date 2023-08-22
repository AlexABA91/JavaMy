package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import com.google.inject.name.Names;

import java.util.Random;

/**
 * Модуль конфигурации инжектора - тут назначаются отношения интерфейсов
 * и их реализация. А также главные средства поставки*/
public class ConfigModule  extends AbstractModule {
    @Override
    protected void configure() {
        // связывание по типу
        bind(GreetingService.class).to(HelloService.class); // lazy
      //  bind(GreetingService.class).toInstance(new HelloService()); //eager

        // именованное связывание (несколько реализаций одного типа)
        bind(PartingService.class).annotatedWith(Names.named("bye")).to(ByeService.class);
        bind(PartingService.class).annotatedWith(Names.named("goodbye")).to(GoodByeService.class);
        bind(HashService.class).annotatedWith(Names.named("md5")).to(Md5Hash.class);
        bind(HashService.class).annotatedWith(Names.named("sha1")).to(Sha1Hash.class);

        // Связывание готовых объектов
        bind(String.class)
                .annotatedWith(Names.named("planetConnection"))
                .toInstance("The connection string ");
        bind(String.class)
                .annotatedWith(Names.named("logfileName"))
                .toInstance("The file log ");

    }

    // Методы-поставщики
    private Random _random;
    @Provides @Named("jur")
    Random randomProvider(){
         return new Random();     // ~транзиент

//        if(_random == null){        // ~Singleton
//            _random = new Random();
//        }
//        return  _random;
    }
}
