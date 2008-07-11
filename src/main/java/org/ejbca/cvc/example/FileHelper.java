/*************************************************************************
 *                                                                       *
 *  CERT-CVC: EAC 1.11 Card Verifiable Certificate Library               * 
 *                                                                       *
 *  This software is free software; you can redistribute it and/or       *
 *  modify it under the terms of the GNU Lesser General Public           *
 *  License as published by the Free Software Foundation; either         *
 *  version 2.1 of the License, or any later version.                    *
 *                                                                       *
 *  See terms of license at gnu.org.                                     *
 *                                                                       *
 *************************************************************************/
package org.ejbca.cvc.example;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


/**
 * Klass f�r att l�sa eller skriva filer.
 * 
 * @author Keijo Kurkinen, Swedish National Police Board
 * @version $Id$
 */
public class FileHelper {


   /**
    * Laddar en (bin�r) fil fr�n disk
    * @return
    */
   public static byte[] loadFile(String path) throws IOException {
      return loadFile(new File(path));
   }

   public static byte[] loadFile(File file) throws IOException {
      byte[] dataBuffer = null;
      FileInputStream inStream = null;
      try {
         // Simple file loader...
         int length = (int)file.length();
         dataBuffer = new byte[length];
         inStream = new FileInputStream(file);

         int offset = 0;
         int readBytes = 0;
         boolean readMore = true;
         while (readMore) {
            readBytes = inStream.read(dataBuffer, offset, length - offset);
            offset += readBytes;
            readMore = readBytes > 0 && offset != length;
         }
      }
      finally {
         try {
            if (inStream != null)
               inStream.close();
         }
         catch (IOException e1) {
            System.out.println("loadFile - error when closing: " + e1);
         }
      }
      return dataBuffer;
   }

   /**
    * Laddar textfil fr�n disk
    * @param file
    * @return
    * @throws IOException
    */
   public static String loadTextFile(File file) throws IOException {
      BufferedReader input = null;
      StringBuffer sb = new StringBuffer();
      try {
         int lines = 0;
         input = new BufferedReader(new FileReader(file));
         String line = null;
         String newLine = System.getProperty("line.separator");
         while( (line=input.readLine())!=null ){
            lines++;
            sb.append(line);
            sb.append(newLine);
         }
         System.out.println("Read " + file.getName() +", " + lines + " lines");
      }
      finally {
         try {
            if (input!= null)
               input.close();
         }
         catch (IOException e1) {
            System.out.println("loadFile - error when closing: " + e1);
         }
      }
      return sb.toString();
   }


   /**
    * Skriver data till en fil
    * @param file
    * @param data
    * @throws IOException
    */
   public static void writeFile(File file, byte[] data) throws IOException {
      FileOutputStream outStream = null;
      BufferedOutputStream bout = null;
      try {
         outStream = new FileOutputStream(file);
         bout = new BufferedOutputStream(outStream, 1000);
         bout.write(data);
      }
      finally {
         if( bout!=null )
            bout.close();
      }
   }

}
