package com.example.junlinliu.calorietracker.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.example.junlinliu.calorietracker.data.UserData;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class InternalStorageService {

    public static void writeToStorage(Context context, UserData userData) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput("userdata", Context.MODE_PRIVATE);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(userData);
            objectOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static UserData loadFromStorage(Context context) {

        File file = context.getFileStreamPath("userdata");
        if(file == null || !file.exists()) {
            return new UserData();
        }
        FileInputStream fileInputStream;
        UserData userData = null;
        try {
            fileInputStream = context.openFileInput("userdata");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            userData = (UserData) objectInputStream.readObject();
            objectInputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userData;
    }
}
