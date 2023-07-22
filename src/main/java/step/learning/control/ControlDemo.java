package step.learning.control;

public class ControlDemo {
    public void run(){
        System.out.println("ControlDemo Run");
        // Тыпи данных : reference-type , но есть primitives

        short   xs;
        byte    xb;
        int     xi;
        long    xl;
        float   xf;
        double  xd;
        char    ch;
        boolean b;
        // с примитивными типами могут возникать ограничения, например при огганизации колекций
        // или других generic<T>
        // для етих типов существуют reference - анолог
        Byte yb;
        Short ys;
        Integer yi;
        Long yl;
        Character c='a';

        //Масывы
        int[] arr = {1,2,3,4,5};
        int[] arr2 = new int[]{1,2,3,4,5};
        int[] arr3 = new int[5];

        // loop

        for (int i = 0; i < arr2.length; i++) {
               if(i == arr2.length-1){
                   System.out.println(arr2[i]+".");
               }else
                    System.out.print(arr2[i]+", ");
        }
        //foreach
        System.out.println("foreach");
        for(int x: arr){
            System.out.print(" "+ x);
        }
        System.out.println();
        System.out.println("----- Таблица Пифогора -----");

        int pt[][]=  new PifagorTable().table();
        new PifagorTable().print();





    }
}
