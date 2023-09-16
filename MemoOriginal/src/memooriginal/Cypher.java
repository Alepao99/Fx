/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package memooriginal;
import java.io.UnsupportedEncodingException;
/**
 * Implementazione dell'algoritmo di crittografia RC4.
 * Attenzione! RC4 è un algoritmo molto vulnerabile. Non usare
 * per qualsiasi applicazione seria!
 */
public class Cypher {
    private final int schedule[];
    private byte[] bkey;
    private int ii, jj;
    
    public Cypher(String key) {
        try {
            bkey=key.getBytes("UTF-8");
        } catch (UnsupportedEncodingException exc) {
            throw new RuntimeException(exc);
        }
        schedule=new int[256];
    }
        
    private void initSchedule() {
        for(int i=0; i<256; i++)
            schedule[i]=i;
        int j=0;
        for(int i=0; i<256; i++) {
            j=(j+ schedule[i] + bkey[i % bkey.length]) & 255;
            int tmp=schedule[i];
            schedule[i]=schedule[j];
            schedule[j]=tmp;
        }
        ii=0;
        jj=0;
    }
    
    private int next() {
        ii=(ii+1) & 255;
        jj=(jj+schedule[ii]) & 255;
        int tmp=schedule[ii];
        schedule[ii]=schedule[jj];
        schedule[jj]=tmp;
        return schedule[(schedule[ii]+schedule[jj])&255];
    }
    
    /**
     * Codifica o decodifica una stringa.
     */
    public String transform(String s) {
        StringBuffer buffer=new StringBuffer();
        int n=s.length();
        initSchedule();
        for(int i=0; i<n; i++) {
            char c=s.charAt(i);
            int c1=(c & 255) ^ next();
            int c2=((c>>8) & 255) ^ next();
            buffer.append((char)(c1 | (c2<<8)));
        }
        return buffer.toString();
    }
     
}
