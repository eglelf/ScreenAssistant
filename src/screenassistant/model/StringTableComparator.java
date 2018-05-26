/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package screenassistant.model;

import java.util.Comparator;

/**
 *
 * @author egle_ferreira
 */
public class StringTableComparator implements Comparator<StringTable> {

    @Override
    public int compare(StringTable o1, StringTable o2) {
        return o1.getKey().compareTo(o2.getKey());
    }

}
