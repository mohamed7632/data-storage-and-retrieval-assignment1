/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

/**
 *
 * @author mohamed sherif
 */
public class kwaymergesort {
     public  String[] DivideInputFileIntoRuns(String filename,int runsize) throws FileNotFoundException, IOException{
         //numberof runs
         int numOfRuns=64/runsize;
         //reading the runs
           RandomAccessFile filestore=new RandomAccessFile(filename,"rw");
           String[] runs=new String[numOfRuns];
           
           filestore.seek(0);
           //while(filestore.readInt()!=EOF){
               for(int i=0;i<numOfRuns;i++){
                  //int[] arr=new int[runsize];
                   RandomAccessFile runfile=new RandomAccessFile("run"+i+".bin","rw");
                   runs[i]="run"+i+".bin";
                   for(int j=0;j<runsize;j++){
                       int x=filestore.readInt();
                       int y=filestore.readInt();
                        runfile.writeInt(x);
                         runfile.writeInt(y);

                        
                    
                   }      
                 
                  
                         
               }
               return runs;
               
     }
     public void printruns(String filename) throws FileNotFoundException, IOException{
         RandomAccessFile filestore=new RandomAccessFile(filename,"rw");
       
         //int size=(int) filestore.length();
        System.out.println(filestore.length()); 
     }
 

}