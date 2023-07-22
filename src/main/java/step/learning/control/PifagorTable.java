package step.learning.control;

public class PifagorTable {
           public int[][] table(){
                int[][] arr = new int[9][9];
               for (int i = 0; i <9 ;++i) {
                   for (int j = 0; j <9 ; ++j) {
                       arr[i][j] = (i+1)*(j+1);
                   }
               }
                return arr;
            };

           public void print(){
               int[][] arr = table();
               for (int i = 0; i <9; i++) {
                   for (int j = 0; j < 9; j++) {
                       if(i == 0 && j == 0){
                           System.out.print("   ");
                       } else if (j == 8) {
                           System.out.println(arr[i][j]);
                       } else{
                           if(arr[i][j]>9)
                               System.out.print(arr[i][j]+" ");
                           else
                               System.out.print(" "+arr[i][j]+" ");
                       }


                   }
               }
           }
        }
