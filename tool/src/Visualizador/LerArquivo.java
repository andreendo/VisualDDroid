/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Bruno
 */
public class LerArquivo {

    public ArrayList<String> linhas = new ArrayList<>();

    public ArrayList<String> retornaArquivo(String caminho) {

        try {
            FileReader arquivo = new FileReader(caminho + "\\LogFile.txt");
            BufferedReader lerArquivo = new BufferedReader(arquivo);
            String linha = lerArquivo.readLine();
            while (linha != null) {
                linhas.add(linha);
                System.out.println(linha);
                linha = lerArquivo.readLine();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao ler arquivo de texto!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }
        return linhas;
    }

    public ArrayList<String> logCompleto(ArrayList<String> linhas, String cesAtual, String eventAtual, int posAtual, boolean lastEvent) {
        ArrayList<String> line = new ArrayList<>();
        boolean falha = false, stop = false, read = false;

        for (String linha : linhas) {
            if (posAtual == 0 && lastEvent) {
                if (linha.contains(cesAtual + "#inicio")) //primeiro evento da CES e último evento da ces, le do começo ao fim a ces
                {
                    read = true;
                }
                if (read) {
                    line.add(linha);
                }

                if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                    falha = true;
                }
                if (read && falha) {
                    if (linha.contains("finished: ")) {
                        read = false;
                    }
                }
                if (read && !falha) {
                    if (linha.contains(cesAtual + "#fim")) {
                        read = false;
                    }
                }

            }
            if (posAtual == 0 && !lastEvent) { //primeiro elemento da CES e não ultimo, le ate o fim do evento ou ate a falha
                if (linha.contains(cesAtual + "#inicio")) {
                    read = true;
                }
                if (read) {
                    line.add(linha);
                }

                if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                    falha = true;
                }
                if (read && falha) {
                    if (linha.contains("finished: ")) {
                        read = false;
                    }
                }
                if (read && !falha) {
                    if (linha.contains("fim#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        read = false;
                    }
                }

            }
            if (posAtual != 0 && lastEvent) {// não primeiro elemento da ces porém é o ultimo, le desde o começo do evento ate o finish da ces
                if (linha.contains("inicio#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                    read = true;
                }
                if (read) {
                    line.add(linha);
                }

                if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                    falha = true;
                }
                if (read && falha) {
                    if (linha.contains("finished: ")) {
                        read = false;
                    }
                }
                if (read && !falha) {
                    if (linha.contains("finished: ")) {
                        read = false;
                    }
                }

            }
            if (posAtual != 0 && !lastEvent) {// nao primeiro e nao ultimo, le do começo do evento ate o fim do evento
                if (linha.contains("inicio#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                    read = true;
                }

                if (read) {
                    line.add(linha);
                }

                if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                    falha = true;
                }
                if (read && falha) {
                    if (linha.contains("finished: ")) {
                        read = false;
                    }
                }
                if (read && !falha) {
                    if (linha.contains("fim#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        read = false;
                    }
                }

            }
        }
        return line;

    }

    public ArrayList<String> logEvento(ArrayList<String> linhas, String cesAtual, String eventAtual, int posAtual, boolean lastEvent) {
        ArrayList<String> line = new ArrayList<>();
        boolean falha = false, stop = false, read = false;

        for (String linha : linhas) {
            if (linha.contains("EventRunner") || linha.contains("TestRunner") || linha.contains("CesTest")) {
                if (posAtual == 0 && lastEvent) {//primeiro evento da CES e último evento da ces, le do começo ao fim a ces
                    if (linha.contains(cesAtual + "#inicio")) {
                        read = true;
                    }
                    if (read) {
                        line.add(linha);
                    }

                    if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        falha = true;
                    }
                    if (read && falha) {
                        if (linha.contains("finished: ")) {
                            read = false;
                        }
                    }
                    if (read && !falha) {
                        if (linha.contains(cesAtual + "#fim")) {
                            read = false;
                        }
                    }

                }
                if (posAtual == 0 && !lastEvent) { //primeiro elemento da CES e não ultimo, le ate o fim do evento ou ate a falha
                    if (linha.contains(cesAtual + "#inicio")) {
                        read = true;
                    }
                    if (read) {
                        line.add(linha);
                    }

                    if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        falha = true;
                    }
                    if (read && falha) {
                        if (linha.contains("finished: ")) {
                            read = false;
                        }
                    }
                    if (read && !falha) {
                        if (linha.contains("fim#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                            read = false;
                        }
                    }

                }
                if (posAtual != 0 && lastEvent) {// não primeiro elemento da ces porém é o ultimo, le desde o começo do evento ate o finish da ces
                    if (linha.contains("inicio#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        read = true;
                    }
                    if (read) {
                        line.add(linha);
                    }

                    if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        falha = true;
                    }
                    if (read && falha) {
                        if (linha.contains("finished: ")) {
                            read = false;
                        }
                    }
                    if (read && !falha) {
                        if (linha.contains("finished: ")) {
                            read = false;
                        }
                    }

                }
                if (posAtual != 0 && !lastEvent) {// nao primeiro e nao ultimo, le do começo do evento ate o fim do evento
                    if (linha.contains("inicio#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        read = true;
                    }

                    if (read) {
                        line.add(linha);
                    }

                    if (linha.contains("Faulty#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                        falha = true;
                    }
                    if (read && falha) {
                        if (linha.contains("finished: ")) {
                            read = false;
                        }
                    }
                    if (read && !falha) {
                        if (linha.contains("fim#" + cesAtual + "#" + posAtual + "#" + eventAtual)) {
                            read = false;
                        }
                    }

                }

            }

        }
        return line;
    }
    
    public boolean verificaExcecao(ArrayList<String> linhas, String cesAtual, String eventAtual, int posAtual){
        for(String linha: linhas){
            if(linha.contains("Faulty#" + cesAtual + "#")){
                return true;
            }
        }
        return false;
    }
    
}
