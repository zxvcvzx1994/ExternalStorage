package com.example.kh.externalstorage.Module;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by kh on 4/25/2017.
 */

public class externalHelper {
    private static  externalHelper Helper;
    public static  externalHelper getInstance(){
        if(Helper ==null){
            Helper = new externalHelper();

        }
        return Helper;
    }
    public Boolean isExternalStorage(){
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                || Environment.MEDIA_MOUNTED_READ_ONLY.equals(Environment.getExternalStorageState()))
            return true;
        else
            return false;
    }
    File Dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
   public String a = Dir.toString();
    public void setData(String data, String filename) throws IOException {

        if(isExternalStorage())
        {


            if (!Dir.exists())
            {
                Dir.mkdirs();
            }
            File f = new File(Dir,filename);
            if(!f.exists())
                f.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(f);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();

        }
    }
    public String getData(String filename) throws IOException {
        if (isExternalStorage()){
            File f = new File(Dir,filename);
           if(f.exists()){
               FileInputStream fileInputStream = new FileInputStream(f);
               InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
               BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
               String line;
               StringBuffer stringBuffer = new StringBuffer();
               while ((line = bufferedReader.readLine())!=null){
                    stringBuffer.append(line+"\n");
               }
             //  bufferedReader.close();
             //  fileInputStream.close();
            return stringBuffer.toString();
           }
           else
            return null;
        }
        return null;
    }
    public boolean deleteData(String filename){
    File f = new File(Dir,filename);
      return f.delete();

    }
}
