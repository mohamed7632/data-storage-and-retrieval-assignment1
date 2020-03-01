/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package files;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author mohamed sherif
 */
public class Files {

    
 
    public static void main(String[] args) throws FileNotFoundException, IOException {
       kwaymergesort obj=new kwaymergesort();
       String[] runs = new String[16];
     
        //for( int i=0;i<4;i++){
            //for(int j=0;j<16;j++){
             runs=obj.DivideInputFileIntoRuns("Index.bin", 16);
               
            for(int j=0;j<4;j++){
                System.out.print(runs[j]+" ");
               }
            int i=1;
             RandomAccessFile filestore=new RandomAccessFile("run1.bin","rw");
             while(filestore.getFilePointer()!=-1){
             System.out.println(filestore.readInt());
             }
//          String name="run"+i+".bin";
//   obj.printruns(name);
//            
            
            
    }
}
