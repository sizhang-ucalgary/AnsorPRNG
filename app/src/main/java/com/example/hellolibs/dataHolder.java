package com.example.hellolibs;
import java.util.ArrayList;
public class dataHolder {
    public ArrayList<byte []> dataByteArrayList;

    public dataHolder(){
        dataByteArrayList = new ArrayList<byte []>();
    }

    public boolean addBytes(byte [] arr){
        try{
            dataByteArrayList.add(arr);
            return true;
        }catch(Exception E){
            return false;
        }
    }

    public void clearData(){
        dataByteArrayList.clear();
    }
}
