package step.learning.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.input.DataFormat;
import step.learning.oop.Book;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.text.DateFormat;

public class GsonDemo {
    public void run(){
        Gson gson2 = new GsonBuilder()  // Builder - создает стерилизатор с дополнительными настройками
                .serializeNulls()       // значение null будет додаваться к результату
                .disableHtmlEscaping() // отключение экранирования HTML тегов
                .setPrettyPrinting()   // форматирование вывода - отступи и перевод строк
                .serializeSpecialFloatingPointValues() // влечение специальных значений по типу infinity
                .excludeFieldsWithModifiers(Modifier.ABSTRACT) // изменение поведения - вместо всех модификаторов будет игнорироваться только ABSTRACT (а статик - не будет)
                .setDateFormat(DateFormat.LONG)
                .create();
        DataObject2 data = new DataObject2();
        data.setField2(10);
        DataObject2.staticField = 20 ;
        System.out.println(gson2.toJson(data));

        data.setField2(Double.POSITIVE_INFINITY); // IllegalArgument exception !
        System.out.println(gson2.toJson(data));
    }
    public  void  run3(){
        DataObject data = new DataObject().setField("value 1").setField2("value 2");
        Gson gson = new Gson();
        System.out.printf("%s --- %s %n", data.toString(), gson.toJson(data));
        data.setField( null);
        System.out.printf("%s --- %s %n", data.toString(), gson.toJson(data));

        DataObject data2 = gson.fromJson("{\"field2\":\"value 2\"}", DataObject.class);
        System.out.printf("%s --- %s %n", data2.toString(), gson.toJson(data2));

        data.setField("<h1>Hallo</h1>"); // JSON предусматривает экранирования символов, тегов

        Gson gson2 = new GsonBuilder()  // Builder - создает стерилизатор с дополнительными настройками
                .serializeNulls()       // значение null будет додаваться к результату
                .disableHtmlEscaping() // отключение экранирования HTML тегов
                .setPrettyPrinting()   // форматирование вывода - отступи и перевод строк
                .create();


        System.out.printf("%s --- %s %n", data2, gson2.toJson(data2));
        System.out.printf("%s --- %s %n", data, gson.toJson(data));
        System.out.printf("%s --- %s %n", data, gson2.toJson(data));

    }
 public  void run2(){
     System.out.println("Gson demo");
     Gson gson = new Gson();
     Book book =  new Book("Art of Programming", "D. Knut");
     System.out.println(gson.toJson(book));
     String json ="{\"author\":\"D. Knut\",\"title\":\"Art of Programming\"}";

     Book book1 = gson.fromJson(json,Book.class);
     System.out.println(book1.getCard());

     try(FileWriter writer = new FileWriter("book.txt")){
        writer.write(json);
     }catch (IOException e) {
       System.err.println(e.getMessage());
     }
     try(FileReader reader = new FileReader("book.txt")){
         Book boo3 = gson.fromJson(reader,book.getClass());
         System.out.println(boo3.getCard());
     }catch (IOException e) {
         System.err.println(e.getMessage());
     }

 }
}
/*Зависимости maven на примере GSON
Maven - имеет свой репозиторий - коллекцию дополнительных пакетов классов - https://mvnrepository.com/
На этом сайте находим искомый пакет выберем версию. Копируем инструкцию и пакет

Вставляем в файл pom.xml в секции dependencies
После чего нужно обновить загрузить зависимости
- появляется кнопка M при изменениях
 ли выберем инструмент maven и нажимаем Reload

 Сама функциональность - это библиотека клаcсов (.JAR) - аналог DLL
 Она загружается в локальный проект и включается к компиляции
 также она должна быть в Production коде (deploy)
* */