/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Execucao;

import java.io.File;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 * Classe não utilizada
 */
public class Deletor {
    
    public void deletarSD (String caminho){ 
        
        String tarefa= "",imagem="";
        StringBuilder sb = new StringBuilder();  
        File raiz = new File(caminho); 
        int imagensDeletadas = 0;
        for(File f: raiz.listFiles()) {  
            if(f.isFile()) {  
                System.out.println(f.getName());  
                tarefa = "adb shell rm /sdcard/screenshots/"+f.getName();
                imagensDeletadas+=execRM(tarefa);
                sb.append(f.getName());  
                sb.append("\n");  
            }  
        }
        JOptionPane.showMessageDialog(null, imagensDeletadas+" arquivos deletados do SD Card!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
       
    }
    
    public int execRM(String tarefa) {
        Terminal ter = new Terminal();
        try {
            Runtime rt = Runtime.getRuntime();
            Process exec = rt.exec(tarefa);
            
            RecuperaBuffer errorGobbler = new RecuperaBuffer(exec.getErrorStream(), "erro");

            RecuperaBuffer outputGobbler = new RecuperaBuffer(exec.getInputStream(), "saida");
            
            errorGobbler.start();
            outputGobbler.start();
          
            int saida = exec.waitFor();
            System.out.println("SAIDA2: "+saida);
            if (saida == 0) {
                if (ter.testeExecucao(exec) == false) {
                    exec.destroy();
                    return 0;
                } else {
                    exec.destroy();
                    return 1;
                }
            } else {
                exec.destroy();
                System.out.println("ERRO");
            }
        } catch (IOException | InterruptedException e) {

            System.out.println(e);
        }

        return 0;
    }
}
