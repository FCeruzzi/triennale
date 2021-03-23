/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.modello;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Francesco
 */
public class Modello {
    private Map<String,Object> mappaBean = new HashMap<String, Object>();
    
    public void putBean(String key, Object o){
        this.mappaBean.put(key, o);
    }
    
    public Object getBean (String key){
        return mappaBean.get(key);
    }
}

