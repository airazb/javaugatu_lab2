/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oktmo;

import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author AAzbuhanov
 */
public class FileToLoad {
        
    public static File[] getAllFiles(File curDir) {
        return curDir.listFiles();        
        }

    } 
    
