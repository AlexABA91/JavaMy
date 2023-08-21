package step.learning.ioc;

import com.google.inject.Singleton;

@Singleton
public class GoodByeService implements PartingService{
    @Override
    public void sayGoodbye() {
        System.out.println("Goodbye");
    }
}
