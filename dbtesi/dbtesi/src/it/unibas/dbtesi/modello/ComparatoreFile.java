/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.unibas.dbtesi.modello;

import java.io.File;
import java.util.Comparator;

/**
 *
 * @author Francesco
 */
public class ComparatoreFile implements Comparator<File> {

    private int extractNumber(String name) {
        int i = 0;
        try {
            int s = name.indexOf('L') + 1;
            int e = name.lastIndexOf('.');
            String number = name.substring(s, e);
            i = Integer.parseInt(number);
        } catch (Exception e) {
            i = 0; // if filename does not match the format
            // then default to 0
        }
        return i;
    }

    @Override
    public int compare(File o1, File o2) {
        int n1 = extractNumber(o1.getName());
        int n2 = extractNumber(o2.getName());
        return n1 - n2;
    }
}
