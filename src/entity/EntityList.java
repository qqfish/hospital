/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.util.List;

/**
 *
 * @author fish
 */
public class EntityList extends EntityTable{
    List<String> list;
    public EntityList(List<String> list){
	setType("EntityList");
	this.list = list;
    }


    
    public List<String> getList(){
	return list;
    }
}
