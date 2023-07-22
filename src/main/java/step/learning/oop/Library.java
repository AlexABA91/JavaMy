package step.learning.oop;


import step.learning.files.JsonFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Library {
    private List<Literature> funds;

    public Library() {
        funds = new ArrayList<>();
        funds.add(new Book("Art of Programming", "D. Knut"));
        funds.add(new Journal("Nature",123));
        funds.add(new Newspaper("Daily Bugle",new Date(2020-1900,11-1,20)));
        funds.add(new Booklet("Go to Step","Step academy"));

        funds.add(new Hologram("Mona Lisa" , "picture"));
        funds.add(new Hologram("Terracotta Army" , "archaeological exhibit"));
        funds.add(new Hologram("Venus of Willebrord" , "archaeological exhibit"));
        funds.add(new Poster("Welcome to aur library" , "library"));


    }
    public void save(){
        new JsonFile().save(funds);
    }
    public void Load(){
        funds = new JsonFile().load();
        showCatalog();
        System.out.println("------File load Success");
    }
    public  void showCatalog()
    {
        System.out.println( "Catalog" );
        for(Literature lit : funds){
            System.out.println(lit.getCard());
        }
        System.out.println("--------------------------------Copiable---------------------");
        this.showCopyable();
        System.out.println("--------------------------------Non-Copiable---------------------");
        this.showNoneCopiable();
        System.out.println("-----------------------------Periodic----------------------------");
        this.showPeriodic();
        System.out.println("------------------------Book and Author ----------------------------");
        this.showBooks();
        System.out.println("------------------------Expo ----------------------------");
        this.showExpo();

    }
    public  void showCopyable(){
        for (Literature lit : funds){
            if(lit instanceof Copiable){
                System.out.println( lit.getCard());
            }
        }
    }
    public  void showNoneCopiable(){
        for (Literature lit : funds){
            if( !(lit instanceof Copiable) )
            {
                System.out.println( lit.getCard());
            }
        }
    }
    public void  showPeriodic(){
        for (Literature lit :funds){
           if(lit instanceof Periodic){
               System.out.print(lit.getCard());
               System.out.printf(" Periodic with period: %s\n",
                       ((Periodic) lit).gryPeriod() );

           }

        }
    }
    public void showBooks(){
        for (Literature lit :funds){
            if(lit instanceof Book){
                Book book = (Book) lit;
                System.out.printf(" Book name: %s, Author: %s \n",
                        book.getTitle(), book.getAuthor() );
            }
        }
    }
    public void showExpo(){
        for (Literature lit :funds){
            if(lit instanceof Expo){
                if(lit instanceof Hologram) {
                    Hologram hologram = (Hologram) lit;
                    System.out.print(lit.getCard()+"\n");
                }
                if(lit instanceof Poster){
                    Poster poster = (Poster) lit;
                    System.out.printf(" poster : %s, Type: %s \n",
                            poster.getTitle(), poster.getCustomer());
                }
            }
        }
    }

}
/*
* Проект "Библиотека"
* Библиотека - хранилище литературы разного типа: газеты, журналы, книги,
* По кожному виду литературы может быть - карта каталог с названием и другими данными.
* Роз ширение:
*  услуга копирования - может ли издание быть скопировано
* Книги, журналы, - копированы , Газеты - нет.
 */