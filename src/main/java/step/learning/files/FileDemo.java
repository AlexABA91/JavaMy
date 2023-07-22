package step.learning.files;

import javax.swing.plaf.IconUIResource;
import java.awt.datatransfer.FlavorEvent;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class FileDemo {
    public void run(){
        //System.out.println("Files");
        //dirDemo();
        //ioDemo();
        System.out.println("Home Work Files");
        dirHomeWork();
    }

    private void dirHomeWork() {
        String fileName = "Home-Work-File.txt";
        System.out.println("Введите путь к папке");
        Scanner dirPath = new Scanner(System.in);

           String directoryPath = dirPath.nextLine();
           File currentDir = new File(directoryPath);
           if (!currentDir.exists()) {
               System.out.println("Не корректный путь");
               return;
           }

           if (!currentDir.isDirectory())
               System.out.println("Введите путь к папке!");
            StringBuilder FileStr = new StringBuilder();
           for (File file : currentDir.listFiles()) {
               String Date = new SimpleDateFormat("yyyy-MM-dd hh:mm").format(new Date(file.lastModified()));

               if (file.isFile()) {
                   String size = Math.round((double)file.length() / 1024) + "kb";
                   System.out.printf("%s\t\t%s \t%s%n",Date,size,file.getName());
                   String writeStr  = Date +"\t\t" + size+ " \t" + file.getName();
                   FileStr.append(writeStr).append("\n");
               } else {
                   System.out.printf("%s <DIR>\t\t\t%s%n", Date, file.getName());
                   String writeStr  = Date +" <DIR>\t\t\t" + file.getName();
                   FileStr.append(writeStr).append("\n");
               }
               try (FileWriter fw = new FileWriter(fileName)){
                   fw.write(FileStr.toString());
               } catch (IOException e) {
                   System.err.println(e.getMessage());
               }
           }
    };

    /**
     * Демонстрация работы с файловой системой
     */
    private void dirDemo(){             //File (java.io) - основный класс
        File currentDir =               // для работы как файлами так и с папками
                new File(               // !! создание new File не
                        "./pom.xml");   //  влияет на oc только создает объект

        System.out.printf("File '%s'",currentDir.getName());
        if( currentDir.exists()){
           System.out.printf("exist %n" );       // %n -> std::end, \n -> один символ
       }
       else {
            System.out.println("Does not exist %n");;
       }
       if(currentDir.isDirectory()){
           System.out.println("is directory");
           for (File file: currentDir.listFiles())
           {
               System.out.printf("%s \t%s %n",
                       file.getName(),
                       file.isDirectory() ? "<DIR>": "file");
           }
       }else {
           System.out.printf("Is File: %s%s%s %n",
                        currentDir.canRead() ? "r": "R",   //
                        currentDir.canWrite() ? "w": "W",  //
                        currentDir.canExecute() ? "x": "X" //
           );
       }
    }
    /**
     * Демонстрация ввода/вывода файла
     */
    private void ioDemo(){
         String fileContent = "This is content of a file\n" +
                 "This is new line";
         String fileName = "Test-file.txt";
         try( FileWriter writer = new FileWriter(fileName)){
            writer.write(fileContent) ;
             System.out.println("Write success");

         } catch (IOException ex) {
             System.err.println(ex.getMessage());
         }
        System.out.println("Reading....");
         try(FileReader reader  =  new FileReader(fileName);
             Scanner scanner = new Scanner(reader)
         ) {
             while (scanner.hasNext()){
                 System.out.println(scanner.nextLine());}
         }catch (IOException ex) {
             System.err.println(ex.getMessage());
         }
    }
};
/*Работа с файлом
* делится на 2 групы
* 1. работа с файловой системой - копирование выделе6ние создание файлов
* 2. Использование е для сохранения файлов
*  1 - смт. DirDemo()
*  2 - особенности java - наличие большего количества средств работы с патоками с (stream)
*  -- InputStream - абстракция что объединение все filestream
 *  - FileReader - потоковое чтение по символу
*   - FileStream - то же самое, но по bite
*   - BufferReader - оболочка, которая создает промежуточный буфер который уменьшает количество операций с потоками
*   - Scanner - оболочка для чтения разных типов данных
*
* -- OutputStream
*   - FileWriter
*   - FileOutputStream
*   - BufferedWriter
*   - PrintWriter - оболочка, которая дает средства для форматированной
*    "печати"(переводит разные типы в символы для потока)
*
*   try-with-resource
*   try( Resource res  = new Resource ){ -- вместо using (c#) -- autoClosable
 *
*   }catch(){
*
*   }
* */


