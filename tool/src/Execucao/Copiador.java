package Execucao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class Copiador {

    public boolean callExec(String caminho) {
        String funcao = "adb pull /sdcard/screenshots/ " + caminho;
        String funcao2 = "adb pull /sdcard/LogFile.txt " + caminho;
        int teste = execPull(funcao, "Screenshots");
        int teste2 = execPull(funcao2, "Logs");

        if (teste == 1) {
            return true;
        }
        return false;
    }

    public int execPull(String tarefa, String tipo) {

        Terminal ter = new Terminal();
        int saida;

        try {
            Runtime rt = Runtime.getRuntime();
            Process exec = rt.exec(tarefa);
            RecuperaBuffer err = new RecuperaBuffer(exec.getErrorStream(), "erro");
            RecuperaBuffer out = new RecuperaBuffer(exec.getInputStream(), "saida");
            err.start();
            out.start();
            saida = exec.waitFor();
            exec.destroy();
            System.out.println("SAIDA: " + saida);
            if (saida == 0) {
                if (!copiouArquivos(err)) {
                    JOptionPane.showMessageDialog(null, tipo + " não encontrados no SD Card!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                    return 0;
                } else {

                    JOptionPane.showMessageDialog(null, tipo + " copiados do SD Card!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                    return 1;
                }
            } else {

                JOptionPane.showMessageDialog(null, "Dispositivo não Encontrado!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
                return 2;
            }
        } catch (IOException e) {
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
        return -1;
    }

    public int contem(String verifica) {
        if (verifica.contains("pull")) {
            return 1;
        }
        return 0;

    }

    public boolean copiouArquivos(RecuperaBuffer rb) {

        try {
            InputStreamReader inputStreamReader = new InputStreamReader(rb.inputStream);
            BufferedReader buff = new BufferedReader(inputStreamReader);
            String linha = "";
            while ((linha = buff.readLine()) != null) {
                if (linha.contains("0 files pulled. 0 files skipped.")) {
                    System.out.println("erro de copia de arquivo");
                    return false;
                }
            }
            return true;
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }

    }

}

class RecuperaBuffer extends Thread {

    public InputStream inputStream;
    public String tipo;

    public RecuperaBuffer(){
    }
    
    RecuperaBuffer(InputStream inputStream, String tipo) {
        this.inputStream = inputStream;
        this.tipo = tipo;
    }

    @Override
    public void run() {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader buffReader = new BufferedReader(inputStreamReader);
            String linha = "";
            while ((linha = buffReader.readLine()) != null) {
                System.out.println(tipo + "-" + linha);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
