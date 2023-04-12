package GameUtilities;

import java.io.*;

public class Input_Output {
//    static Game_database database;
//
//    public Input_Output(Game_database g){
//        database = g;
//    }

    public static void serialize(Game_database database) throws IOException {
        ObjectOutputStream out = null;
        try{
            out = new ObjectOutputStream(new FileOutputStream("output.ser"));
            out.writeObject(database);
            System.out.println("Written to output.txt");
        }
        finally {
            out.close();

        }
    }

    public static Game_database deserialize() throws IOException, ClassNotFoundException{
        ObjectInputStream in = null;
        Game_database out_database = null;
        try{
            in = new ObjectInputStream(new FileInputStream("output.ser"));
            out_database = (Game_database) in.readObject();
        }
        finally {
            in.close();
        }

        return out_database;
    }
}
