package com.mnk.Utils;

import java.io.*;

public class SaveRestoreObjectFromFile {

    public static void saveToFile(String outputFile,Object object){
        try {
            FileOutputStream fout=new FileOutputStream(outputFile);
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(fout);
            objectOutputStream.writeObject(object);
            objectOutputStream.flush();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Object restoreFromFile(String inputFile){
        try {
            FileInputStream fileInputStream=new FileInputStream(inputFile);
            ObjectInputStream objectInputStream=new ObjectInputStream(fileInputStream);
            Object object=objectInputStream.readObject();
            objectInputStream.close();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
