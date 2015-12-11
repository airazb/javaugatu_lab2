/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author AAzbuhanov
 */
public class OktmoAnalyzer {
    
    public static void findMostPopularPlaceName(int regionCode,OktmoData data){
        try {
            int cnt = 1;
            Map<String,Integer> m = new TreeMap<String,Integer>();
            
            for (Place p : data.getSortedPlaces()){
                if (m.get(p.getName()) == null){
                    m.put(p.getName(), cnt);
                } else {
                    m.replace(p.getName(), m.get(p.getName()), m.get(p.getName())+1);
                }
            }
            
            System.out.println(m.entrySet().stream().max(Map.Entry.comparingByValue(Integer::compareTo)).get());
        
        } catch (Exception ex) {
            //Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public static void findMaxCountPlaceInDistrict(OktmoData data){
        try {
            Map<String,Integer> m = new TreeMap<String,Integer>();
            Map <Long,District> listDistrict = null;
            listDistrict = data.getListDistrict();
            
            for (Entry<Long,District> e : listDistrict.entrySet()){
                m.put(e.getValue().getName(), data.getCountPlaceByDistrictCode(e.getKey()));
            }           
            //System.out.println("////-"+m);          
            System.out.println(m.entrySet().stream().max(Map.Entry.comparingByValue(Integer::compareTo)).get());
        
        } catch (Exception ex) {
            Logger.getLogger(OktmoAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
    public static void printCountPlaceByStatusInDistrict(OktmoData data){
        try {
            Map<String,Integer> m = null;
            Map <Long,District> listDistrict = null;
            List<Place> pList = null;
            int cnt = 1;
            Map <Long,Region> listRegion = null;
            
            listRegion = data.getListRegion();
            
            for (Region r : listRegion.values()){
                pList = data.getAllPlaceByRegion(r.getCode());
                m = new TreeMap<String,Integer>();
                for (Place p : pList){
                    if (m.get(p.getStatus()) == null){
                        m.put(p.getStatus(), cnt);
                    } else {
                        m.replace(p.getStatus(), m.get(p.getStatus()), m.get(p.getStatus())+1);
                    }
                }
                System.out.println("--------------------------------------------------");
                System.out.format("%10s%-60s","Наименование региона: ", r.getName());
                System.out.println();
                
                Iterator<Entry<String, Integer>> iterator = m.entrySet().stream().sorted(Map.Entry.comparingByValue(Integer::compareTo)).iterator();
                while (iterator.hasNext()){
                    Entry<String,Integer> e = iterator.next();
                    System.out.format("%20s%10s",e.getKey(), e.getValue());
                    System.out.println();
                }
                /*for (Entry<String,Integer> e : m.entrySet()){
                    System.out.format("%20s%10s",e.getKey(), e.getValue());
                    System.out.println();
                }*/
            }
            
           
            //System.out.println(m.entrySet().stream().max(Map.Entry.comparingByValue(Integer::compareTo)).get());
        
        } catch (Exception ex) {
            Logger.getLogger(OktmoAnalyzer.class.getName()).log(Level.SEVERE, null, ex);
        }   
    }
}
