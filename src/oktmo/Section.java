/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

import java.util.HashMap;
import java.util.TreeMap;
//import java.util.Map;

/**
 *
 * @author AAzbuhanov
 */
public class Section {
    private long code;
    private String name;
    private int cntItem = 0;    
    private HashMap<Long, Section> listItem;

    public Section(long code, String name) {
        if (this instanceof Place){
        } else {
            //System.out.println(this.getClass().toString());
            this.listItem = new HashMap<Long, Section>();
        }
        this.code = code;
        this.name = name;
    }    

    public long getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
       
    public <U extends Section> boolean add(long code,U section){
        if (1==1){//this.code == code){
            listItem.put(code, section);
            cntItem +=1;
            return true;
        } else {
            return false;
        }
        
    }

    @Override
    public String toString() {
        return "{" + "name=" + name + ", cntItem=" + cntItem + ", code=" + code + "}";
    }
    
    public void getInfo(){
        System.out.println("name=" + name );
        System.out.println("listItem=" + listItem );
    }

    public <U extends Section> HashMap<Long, U> getListItem() {
        return (HashMap<Long, U>) listItem;
    }
    
    
    
    
    
    
}
