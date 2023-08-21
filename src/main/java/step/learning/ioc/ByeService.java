package step.learning.ioc;

import com.google.inject.Singleton;
import com.google.inject.name.Named;

@Singleton
public class ByeService implements PartingService{
    @Override
    public void sayGoodbye() {
        System.out.println("Bye");
    }
}
