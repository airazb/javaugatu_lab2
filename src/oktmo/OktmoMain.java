/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

import java.io.File;
import java.util.Iterator;
import java.util.regex.*;
import static oktmo.FileToLoad.*;
import static oktmo.OktmoAnalyzer.*;
import static oktmo.OktmoReader.*;

/**
 *
 * @author AAzbuhanov
 */
public class OktmoMain {
    
     /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        OktmoData oData = new OktmoData();
        OktmoData oPatData = new OktmoData();
        for(File f : getAllFiles(new File("."))){
            if(f.isFile() & f.getName().indexOf("_2.csv")>0){
                System.out.println("-----"+f.getName());
                readPlaces(f.getName(),oData);
                readPaternPlaces(f.getName(),oPatData);
            }
        }
        System.out.println(oData.getAllStatuses().toString());
        System.out.println(oPatData.getAllStatuses().toString());
        Iterator<Place> it = oData.getSortedPlaces().listIterator();           
        Pattern p = Pattern.compile(".{1,3}(ово)$"); 
        while (it.hasNext()) {
            Place place = it.next();
            //Place placeP = itP.next();
            /*if !(place.getName().equals(placeP.getName())){
                System.out.println(place+" # "+placeP);
            }*/ 
            Matcher m = p.matcher(place.getName());  
            
            if(m.matches()){  
                //System.out.println(place.getName());  
            } 
            //System.out.println(place.getName() + " " + place.getStatus());            
        }
        System.out.println("-11-------------");
        //Найдите населённые пункты, с названиями, которые начинаются и заканчиваются га одну и ту же согласную букву 
        Iterator<Place> it11 = oData.getSortedPlaces().listIterator();        
        //p = Pattern.compile("\\b([\\w])[\\w-]+\\1\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE); \
        Pattern p11 = Pattern.compile("(?u)\\b([А-Яа-я])[А-Яа-я]+\\1\\b", Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
        while (it11.hasNext()) {
            Place place11 = it11.next();  
            Matcher m11 = p11.matcher(place11.getName());     
            if(m11.matches()){  
                //System.out.println(place11.getName());  
            }    
        }
        
        System.out.println(oData.getListPlace().size());
        System.out.println(oPatData.getListPlace().size());
        
        System.out.println(oData.getListPlace().equals(oPatData.getListPlace()));
        System.out.println("4.-------------");       
        
        //OktmoData oDataR = new OktmoData();
        for(File f : getAllFiles(new File("."))){
            if(f.isFile() & f.getName().indexOf("_1.csv")>0){
                System.out.println("-----"+f.getName());
                readData(f.getName(),oPatData);
            }
        }
        System.out.println("");    
        System.out.println("associatePlaces-");    
        oPatData.associatePlaces();
        System.out.println(oPatData.getListRegion());
        System.out.println(oPatData.getListDistrict());
        System.out.println(oPatData.getListSettlement());
        System.out.println("4.4------------");     
        System.out.println(oPatData.getListRegion().size());
        System.out.println(oPatData.getListDistrict().size());
        System.out.println(oPatData.getListSettlement().size());
        
        System.out.println(oPatData.getListPlace().get(1001));
        System.out.println(oPatData.findRegion(oPatData.getListPlace().get(1001)));
        System.out.println(oPatData.findDistrict(oPatData.getListPlace().get(1001)));
        System.out.println(oPatData.findSettlement(oPatData.getListPlace().get(1001)));
        
        System.out.println("--test------------");   
        
        oPatData.findSettlement(oPatData.getListPlace().get(1001)).getInfo();
        
        System.out.println(oPatData.findSettlement(oPatData.getListPlace().get(1001)).getListItem().size());
        System.out.println(oPatData.getListPlace().size());
        System.out.println(oPatData.getSettlementByCode(3623425));
        
        System.out.println(oPatData.findSettlement(oPatData.getListPlace().get(1001)));
        System.out.println("-------------");  
        System.out.println(oPatData.getPlaceByCode((Long.parseLong("3623425111"))));
        for (Section tPlace : oPatData.getSettlementByCode(3623425).getListItem().values()){
            System.out.println(tPlace.toString());
        }
        System.out.println("-------------"); 
        System.out.println("5------------"); 
        
        findMostPopularPlaceName(63000000,oPatData);
        
        System.out.println("***********------------"); 
        
        System.out.println(oPatData.getCountPlaceByDistrictCode(3623000));
        
        findMaxCountPlaceInDistrict(oPatData);
        System.out.println(""); 
        System.out.println("------------"); 
        printCountPlaceByStatusInDistrict(oPatData);
        
    }
    
    
}
