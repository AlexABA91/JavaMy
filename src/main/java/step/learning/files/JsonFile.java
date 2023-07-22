package step.learning.files;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
import step.learning.oop.Literature;
import step.learning.oop.Book;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class JsonFile {
    Gson gson;
   final String fileName = "HomeWorkJson.txt";
public  JsonFile(){
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
    }

    public  void save(List<Literature> collection) {

       List<CoverForSerialize> coverSer = new ArrayList<>();

        for (Literature literature : collection) {
            CoverForSerialize cov = new CoverForSerialize()
                    .setType(literature.getClass().toString())
                    .setData(literature);
            coverSer.add(cov);
        }
        try( FileWriter fileWriter = new FileWriter(fileName)) {
           fileWriter.write(gson.toJson(coverSer));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        System.out.println(gson.toJson(coverSer));

        System.out.println("---------Save To File success-----------");
    }
    public List<Literature> load(){
             System.out.println("--------File Load-------");
             ArrayList<Literature>  literature = new ArrayList<>();
             try (BufferedReader reader = new BufferedReader( new FileReader(fileName))){

                 StringBuilder str = new StringBuilder();
                 int c;
                 while ((c= reader.read()) != -1){
                     str.append((char) c);
                 }

                 JSONArray myJson = new JSONArray(str.toString());
                 for (int i = 0; i < myJson.length(); i++) {
                     JSONObject jo = myJson.getJSONObject(i);
                     String type = jo.getString("Type").replaceAll("class","").trim();
                     Object libObj = gson.fromJson(String.valueOf(jo.getJSONObject("Data")),Class.forName(type) );
                     literature.add((Literature)libObj);
                 }
             } catch (IOException e) {
                 System.err.println(e.getMessage());
             } catch (ClassNotFoundException e) {
                 throw new RuntimeException(e);
             }

             return literature;

    }

}
