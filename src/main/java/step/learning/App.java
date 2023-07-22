package step.learning;
import step.learning.control.ControlDemo;
import step.learning.db.DbDemo;
import step.learning.files.FileDemo;
import step.learning.files.GsonDemo;
import step.learning.files.JsonFile;
import step.learning.oop.Library;


/**
 * Hello world!
 */
public class App 
{
    public static void main( String[] args )
    {

        // new ControlDemo().run();

        //new FileDemo().run();
        //new GsonDemo().run();
        // new DbDemo().run();

        new Library().save();
        new Library().Load();

    }
}
/*
Java Вступ

Java - ООП мова програмування, на сьогодні курується Oracle
Мова типу "транслятор" - компілюється у байт-код (проміжний
код), який виконується спеціальною платформою (JRE -
Java Runtime Environment) або JVM (Virtual Machine)
Ця платформа встановлюється як окреме ПЗ. Для перевірки
можна виконати у терміналі команду
java -version

У Java гарна "зворотна" сумісність - старші платформи нормально
виконують код, створений ранніми платформами
Є визначна версія - Java8 (1.8), яка оновлюється, але не
модифікується. На ній працює більшість програмних комплексів
типу ЕЕ

Java SE (Standard Edition) - базовий набір
Java EE (Enterprise Edition) - базовий набір + розширені засоби

Для створення програм необхідний додатковий пакет - JDK (Java
Development Kit)

Після встановлення JRE, JDK встановлюємо IDE (Intellij Idea)
Створюємо новий проєкт.
Як правило, проєкти базуються на шаблонах, орієнтованих на
простоту збирання проєкту - підключення додаткових модулів,
формування команд компілятору та виконавцю, тощо
Поширені системи - Maven, Gradle, Ant, Idea
При створенні нового проєкту - вибираємо Maven Archetype
тип - org.apache.maven.archetypes:maven-archetype-quickstart

после создания проэкта конфигурируем запуск( в начале настроен запуск current file) -
Edit Configuration  - create new -- вводим название  и выбераем главный класс.

 */

/*
* Java - интерпритируемый язык коорый с файла .java (выходной кол )
* компелирует в файлы .class (срединый код ) который выполняется jvm командой java.exe step.learning.App
*
* в отличии от Studio отдельное окно консоли не создается виведение проводится через IDE, окно Run
*
* В Java строгая привязка к структуре файлов и папок
* - папка - это package (пкет, анолог NameSpase)
*  название папки один к одному с  именем пакета. Принято Нижний регистр для названия пакетов.
* - файл - это класс. Ограничение  - один файл - один public класс
* замечание: -   в водном файле может быть нескольео класов но только один ви демый.
* а ткже есть внутрение клвссы (Nested)класы - в класе
* Название класса также должны сбегатся с названием файла
* Для имен класов принято CapitalCamelCase
*
* Control flow insruction - инструкции управления упрравлением оператор условного и цикличного выполнения, а также
* переходов между инструкциями
 */

    // String
//    String str1 = "Hello";  // String pool - одне значення стає
//    String str2 = "Hello";  // одним об'єктом
//    String str3 = new String("Hello");
//    String str4 = new String("Hello");
//if( str1 == str2 ) {  // через пул рядків це насправді один і той самий об'єкт
//        System.out.println("str1 == str2");
//        }
//        else {
//        System.out.println("str1 != str2");
//        }
//        if( str3 == str4 ) {  // об'єкти рівні тільки якщо це один об'єкт (за посиланням)
//        System.out.println("str3 == str4");
//        }
//        else {
//        System.out.println("str3 != str4");
//        }
//        if( str3.equals( str4 ) ) {  // порівняння за контентом
//        System.out.println("str3 equals str4");
//        }
//        else {
//        System.out.println("str3 !equals str4");
//        }