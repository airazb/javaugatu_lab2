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
public class Place extends Section{
    private String status;  

    public Place(long code, String name, String status) {
        super(code, name);
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        final Place other = (Place) obj;
        if((this.getCode() != other.getCode())){    
            return false;
        }
        if((this.getName() == null) ? (other.getName() != null) : !this.getName().equals(other.getName())){
            return false;
        }
        if((this.status == null) ? (other.status != null) : !this.status.equals(other.status)){
            return false;
        }
        return true;
      }

    @Override
    public String toString() {
        return "Place{" + "code=" + this.getCode() + ", name=" + this.getName() + ", status=" + status + '}';
    }

    @Override
    public <U extends Section> boolean add(long code, U section) {
        return false;//super.add(code, section); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
}
