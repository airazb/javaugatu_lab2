/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author AAzbuhanov
 */
public class OktmoData {
    
    class SortedByName implements Comparator<Place> {
      
       @Override
       public int compare(Place obj1, Place obj2) {
            
             String str1 = obj1.getName();
             String str2 = obj2.getName();
            
             return str1.compareTo(str2);
       }
}
    private ArrayList <Place> listPlace = new ArrayList<Place>();
    private TreeMap <Long,Region> listRegion = new TreeMap<Long,Region>();
    private TreeMap <Long,District> listDistrict = new TreeMap<Long,District>();    
    private TreeMap <Long,Settlement> listSettlement = new TreeMap<Long,Settlement>();
    private TreeSet <String> allStatuses = new TreeSet<String>();
    private List <Place> sortedPlaces = new ArrayList <Place>();
    
    public boolean addListPlace(Place place) {
        //System.out.println(place.toString());
        /*if !(place.getStatus().equals("")) */ allStatuses.add(place.getStatus());
        return this.listPlace.add(place);
    }

    public TreeMap<Long,Region> getListRegion() {
        return listRegion;
    }

    public TreeMap<Long,District> getListDistrict() {
        return listDistrict;
    }

    public TreeMap<Long,Settlement> getListSettlement() {
        return listSettlement;
    }
    
    public Region addListRegion(Region region){
        return this.listRegion.put(region.getCode(), region);
    }
    
    public District addListDistrict(District district){
        return this.listDistrict.put(district.getCode(),district);
    }
    
    public Settlement addListSettlement(Settlement settlement){
        return this.listSettlement.put(settlement.getCode(),settlement);
    }

    public TreeSet<String> getAllStatuses() {
        return allStatuses;
    }

    public ArrayList<Place> getListPlace() {
        return listPlace;
    }

    public List<Place> getSortedPlaces() {
        sortedPlaces.addAll(listPlace);
        Collections.sort(sortedPlaces, new SortedByName());
        
        return sortedPlaces;
    }
    
    public Region getRegionByCode(long code){
        return listRegion.get(code);
    }
    
    public District getDistrictByCode(long code){
        return listDistrict.get(code);
    }
    
    public Settlement getSettlementByCode(long code){
        return listSettlement.get(code);
    }
    
    public Place getPlaceByCode(long code){
        for (Place s : listPlace) {
                if (code == s.getCode()){
                    return s;
                }
            }  
        return null;
    }
    
    public void associatePlaces() {
        //for (Settlement s : listSettlement.values()) {
        int cnt=0;
            for (Place p : listPlace){
                /*if (p.getCode()/1000 == s.getCode()) {
                    s.add(p.getCode(), p);
                }*/
                Settlement s = listSettlement.get(p.getCode()/1000);
                if (s != null){
                    s.add(p.getCode(), p);
                } else { cnt +=1;
                    District d = listDistrict.get(p.getCode()/1000);
                    s = new Settlement(d.getCode(), d.getName()); 
                    s.add(p.getCode(), p);
                    this.addListSettlement(s);                    
                    //System.out.println(p);
                }                
            }
        //}
            System.out.println("колво неопределенных"+cnt);
    }
    public Region findRegion(Place p){
           return this.getRegionByCode(p.getCode()/(long)Math.pow(10, 9)*(long)Math.pow(10, 6));
    }
    public District findDistrict(Place p){
           return this.getDistrictByCode(p.getCode()/(long)Math.pow(10, 6)*(long)Math.pow(10, 3));
    }
    public Settlement findSettlement(Place p){
           return this.getSettlementByCode(p.getCode()/(long)Math.pow(10, 3));
    }
    
    public int getCountPlaceByDistrictCode(long districtCode){
        int cnt=0;
        for (Section d : listDistrict.get(districtCode).getListItem().values()){
            //System.out.println(d.getListItem().values()); 
            cnt += d.getListItem().values().size();
        }
        return cnt;
    }
    
    public List<Place> getAllPlaceByRegion (long regionCode){
        List<Place> list = new ArrayList<>();
        listRegion.get(regionCode).getListItem().values().stream().forEach((d) -> {
            d.getListItem().values().stream().forEach((stl) -> {
                stl.getListItem().values().stream().forEach((p) -> {
                    list.add((Place) p);
                });
            });
        });
        return list;
    }
    
}
