/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Execucao;

/**
 *
 * @author Bruno
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Terminal {

    
    public boolean testeExecucao(Process proc) throws IOException,  InterruptedException{
        InputStream stdin = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdin);
        BufferedReader br = new BufferedReader(isr);
        String linhas = null;
        while ((linhas = br.readLine()) != null){
            System.out.println(linhas);
             if(linhas.contains("rm failed")){
                 System.out.println("FALSE1");
                 return false;
             }
        }
        return true;
    }
    public boolean testeErro (Process proc) throws IOException,  InterruptedException{
        InputStream stderr = proc.getErrorStream();
        InputStreamReader isr = new InputStreamReader(stderr);
        BufferedReader br = new BufferedReader(isr);
        String linhas = null;
        while ((linhas = br.readLine()) != null){
            System.out.println(linhas);
             if(linhas.equals("0 files pulled. 0 files skipped.")){
                 System.out.println("FALSE2");
                 return false;
             }
        }
        return true;
    }
}