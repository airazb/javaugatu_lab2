/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.*;

/**
 *
 * @author AAzbuhanov
 */
public class OktmoReader {
    
    public static void readPlaces(String fileName, OktmoData data){
        long start = System.nanoTime();
        BufferedReader br = null;
        String [] aStr;        
        long code;
        String name;
        boolean isUpperCase;
        String status;  
        int lineCount=0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String s;
            while ((s=br.readLine()) !=null ) {
                lineCount++;
                aStr = s.split(";");
                if (aStr.length ==0 || aStr[0].equals("") ) continue;
                if (aStr[2].startsWith("Населенные пункты") == false){
                    code = Long.parseLong(aStr[0].replace(" ", ""));
                    isUpperCase = aStr[2].substring(0,1).equals(aStr[2].substring(0,1).toUpperCase());
                    //System.out.println(s +" $"+ isUpperCase+" #" + lineCount);                    
                    name = (isUpperCase == true) ? aStr[2].trim() : aStr[2].substring(aStr[2].indexOf(" ")+1).trim();
                    status = (isUpperCase == true) ? "" : aStr[2].substring(0, aStr[2].indexOf(" "));
                    if (!aStr[0].equals("") && !aStr[2].equals("") ){
                    data.addListPlace(new Place(
                                            code, //code
                                            name, //name
                                            status //status
                                            ));                  
                    }
                }            
            }
        } 
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException ex) {
                System.out.println("Can not close");
            }
            aStr = null;
            br = null;
            long time = System.nanoTime() - start;
            System.out.printf("readPlaces: "+fileName+" took an average of %.1f us%n", time / 1000000.0);
     
        }
    }
    
    public static void readPaternPlaces(String fileName, OktmoData data){
        long start = System.nanoTime();
        BufferedReader br = null;
        String code;
        String name;
        String status;  
        int lineCount=0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String s;
            Pattern splitPatern = Pattern.compile(
                    "^(\\d+)\\s(\\d+)\\s(\\d+)\\s(\\d+)\\;(\\d+)\\;((?!Населенные\\sпункты|[А-Я]|[A-Z])\\S+)*\\s*((?!Населенные\\sпункты).*)$");
            Matcher m;
            while ((s=br.readLine()) !=null ) {
                lineCount++;
                m = splitPatern.matcher(s);
                if (m.matches()){
                    //System.out.println(s + " #" + lineCount);
                    code = m.group(1) + m.group(2) + m.group(3) + m.group(4);
                    name = m.group(7).toString();
                    status = (m.group(6) != null) ? m.group(6) : "";
                    data.addListPlace(new Place(
                                                Long.parseLong(code), //code
                                                name, //name
                                                status) //status
                                      );                
                } else {
                    //System.out.println(s + " #" + lineCount);
                }
            }
        } 
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException ex) {
                System.out.println("Can not close");
            }
            long time = System.nanoTime() - start;
            System.out.printf("readPaternPlaces: "+fileName+" took an average of %.1f us%n", time / 1000000.0);
     
        }
    }
    
    public static void readData(String fileName, OktmoData data){
        long start = System.nanoTime();
        BufferedReader br = null;
        String code;
        String name; 
        int lineCount=0;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8"));
            String s;
            Pattern splitPaternRegion = Pattern.compile(
                    "^\\D*(\\d+)\\s+(000)\\s+(000)\\s*\\;(\\d+)\\;(\\-s*)*(.*)\\;.*$");
            Matcher mRegion;
            Pattern splitPaternDistrict = Pattern.compile(
                    "^\\D*(\\d+)\\s+((?!000)\\d+)\\s+(000)\\s*\\;(\\d+)\\;(-*|)\\s*(.*)\\;(.*)$");
            Matcher mDistrict;
            Pattern splitPaternSettlement = Pattern.compile(
                    "^\\D*(\\d+)\\s+((?!000)\\d+)\\s+((?!000)\\d+)\\s*\\;(\\d+)\\;(-*|)\\s*(.*)\\;(.*)$");
            Matcher mSettlement;
            
            Region r = null;
            District d = null;
            Settlement st = null;
            
            while ((s=br.readLine()) !=null ) {
                lineCount++;
                boolean bRegion = false;
                boolean bDistrict = false;
                boolean bSettlement = false;
                
                mRegion = splitPaternRegion.matcher(s);
                mDistrict = splitPaternDistrict.matcher(s);
                mSettlement = splitPaternSettlement.matcher(s);
                           
                if (mRegion.matches()){
                    //System.out.println("###" +s + " #" + lineCount);
                    bRegion = true;
                    code = mRegion.group(1) + mRegion.group(2) + mRegion.group(3) ;
                    name = mRegion.group(6);
                    r = new Region(Long.parseLong(code), //code
                                   name); //name
                    data.addListRegion(r);                
                } else {
                    //System.out.println(s + " #" + lineCount);
                }
                if (mDistrict.matches()){
                    //System.out.println(" #" +s + " #" + lineCount);
                    bDistrict = true;
                    code = mDistrict.group(1) + mDistrict.group(2) + mDistrict.group(3) ;
                    name = mDistrict.group(6);
                    d = new District( Long.parseLong(code), //code
                                      name); //name
                    data.addListDistrict(d);    
                    r.add( (Long.parseLong(code)) , d);
                } else {
                    //System.out.println(s + " #" + lineCount);
                }
                if (mSettlement.matches()){
                    //System.out.println(s + " #" + lineCount);
                    bSettlement = true;
                    code = mSettlement.group(1) + mSettlement.group(2) + mSettlement.group(3) ;
                    name = mSettlement.group(6);
                    st = new Settlement( Long.parseLong(code), //code
                                         name);//name
                    data.addListSettlement( st );  
                    d.add( (Long.parseLong(code)), st);
                } else {
                    //System.out.println(s + " #" + lineCount);
                }
                if (!(bSettlement) && !(bDistrict) && !(bRegion)){
                    System.out.println("err:"+s + " #" + lineCount);
                }
                if (lineCount > 500) {
                    //break;
                }
            }
        } 
        catch (IOException ex) {
            System.out.println("Reading error in line "+lineCount);
            ex.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException ex) {
                System.out.println("Can not close");
            }
            long time = System.nanoTime() - start;
            System.out.printf("readPaternPlaces: "+fileName+" took an average of %.1f us%n", time / 1000000.0);
     
        } 
        
    }
    
}
