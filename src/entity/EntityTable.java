/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;

/**
 *
 * @author fish
 */
public class EntityTable implements Serializable{

    private String type;

    public EntityTable() {
    }

    public String getType() {
	return type;
    }
    
    public void setType(String type){
	this.type = type;
    }
}
