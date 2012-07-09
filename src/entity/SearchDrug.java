/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author fish
 */
public class SearchDrug extends EntityTable {

    private String keyword;
    private char searchType;
    // N for normal; D for detail

    public SearchDrug(String keyword, char searchType) {
	setType("SearchDrug");
	this.keyword = keyword;
	this.searchType = searchType;
    }
    
    public char getSearchType(){
	return searchType;
    }

    public String getKeyword() {
	return keyword;
    }
}
