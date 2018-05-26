/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenassistant.model;

/**
 *
 * @author egle_ferreira
 */
public class StringTable implements Comparable<String>{
    private String key;
    private String value;

    public StringTable(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public StringTable() {
        this.key = "";
        this.value = "";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(String o) {
        return this.key.compareTo(o);
    }
}
