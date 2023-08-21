package step.learning.ioc;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

/**
 * Модуль конфигурации инжектора - тут назначаются отношения интерфейсов
 * и их реализация. А также главные средства поставки*/
public class ConfigModule  extends AbstractModule {
    @Override
    protected void configure() {
        // связывание по типу
        bind(GreetingService.class).to(HelloService.class);
        // именованное связывание (несколько реализаций одного типа)
        bind(PartingService.class).annotatedWith(Names.named("bye")).to(ByeService.class);
        bind(PartingService.class).annotatedWith(Names.named("goodbye")).to(GoodByeService.class);
        bind(HashService.class).annotatedWith(Names.named("md5")).to(Md5Hash.class);
        bind(HashService.class).annotatedWith(Names.named("sha1")).to(Sha1Hash.class);


    }
}
