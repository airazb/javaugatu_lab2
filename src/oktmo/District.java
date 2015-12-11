/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

/**
 *
 * @author AAzbuhanov
 */
public class District extends Section{

    public District(long code, String name) {
        super(code, name);
    }
    
    @Override
    public <U extends Section> boolean add(long code, U section) {
        if (section instanceof Settlement){
            return super.add(code, section); //To change body of generated methods, choose Tools | Templates.
        } else {
            return false;
        }
        
    }  

  
}
