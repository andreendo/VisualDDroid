/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visualizador;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Bruno
 */
public class Visualizador extends javax.swing.JFrame {

    /**
     * Creates new form Visualizador
     */
    public String cesAtual = "", eventAtual = "", caminho = "";
    public int posEventAtual = 0, labelClicada = 0;
    //public String caminhos[];
    //public String nomes[];
    public ArrayList<ImageIcon> imagem;
    public ArrayList<String> titulos, nomesCes, caminhos, nomes;
    public ArrayList<String> logs;
    public ArrayList<Integer> originalPosition;
    public ArrayList<Integer> numero;
    public ArrayList<Boolean> isLast;
    private int index;
    private int total;
    public LerArquivo arq = new LerArquivo();
    public boolean erro = false;
    public ArrayList<JLabel> labels;
    public JLabel jLabel;
    public JPanel jPanel;

    public Visualizador() {
        initComponents();
        //Dimension d = new Dimension(600,600);
        this.setLocationRelativeTo(null);
        this.setTitle("Imagens");
        //this.setPreferredSize(d);
        imagem = new ArrayList<>();
        titulos = new ArrayList<>();
        originalPosition = new ArrayList<>();
        isLast = new ArrayList<>();
        caminhos = new ArrayList<>();
        nomes = new ArrayList<>();
        index = 0;
        total = 0;
        labels = new ArrayList<>();
        jPanel = new JPanel();
        nomesCes = new ArrayList<>();

    }

    public void preencheLogs(String caminho) {
        this.caminho = caminho;
        logs = arq.retornaArquivo(caminho);
    }

    public void atualizaLogs() {
        jTextArea1.setText("");
        ArrayList<String> conteudoLog;

        if (jComboBox1.getSelectedIndex() == 0) {
            conteudoLog = arq.logCompleto(logs, cesAtual, eventAtual, originalPosition.get(index), isLast.get(index));
            erro = arq.verificaExcecao(conteudoLog, cesAtual, eventAtual, originalPosition.get(index));
            for (String c : conteudoLog) {
                jTextArea1.append(c + "\n");
            }
            if (erro) {
                jLabel1.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            } else {
                jLabel1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            }
        }
        if (jComboBox1.getSelectedIndex() == 1) {
            conteudoLog = arq.logEvento(logs, cesAtual, eventAtual, originalPosition.get(index), isLast.get(index));
            erro = arq.verificaExcecao(conteudoLog, cesAtual, eventAtual, originalPosition.get(index));
            for (String c : conteudoLog) {
                jTextArea1.append(c + "\n");
            }
            if (erro) {
                jLabel1.setBorder(BorderFactory.createLineBorder(Color.RED, 5));
            } else {
                jLabel1.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));
            }
        }
        if (jComboBox1.getSelectedIndex() == 2) {
            jLabel1.setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
        }
    }

    public void exibeImagens(ArrayList<JCheckBox> listCheckBox) {
        int x, y, i = 0;
        for (x = 0; x < listCheckBox.size(); x++) {
            if (listCheckBox.get(x).isSelected()) {
                for (y = 0; y < nomes.size(); y++) {
                    if (nomes.get(y).contains(listCheckBox.get(x).getText())) {
                        preencheImagens(caminhos.get(y));
                        titulos.add(listCheckBox.get(x).getText());
                        String txt[] = listCheckBox.get(x).getText().split("-");
                        originalPosition.add(Integer.parseInt(txt[1]));
                        if (x < (listCheckBox.size() - 1)) {
                            txt = listCheckBox.get(x + 1).getText().split("-");
                            int k = Integer.parseInt(txt[1]);
                            if (k == 0) {
                                isLast.add(true);
                            } else {
                                isLast.add(false);
                            }
                        }
                        if (x == (listCheckBox.size() - 1)) {
                            isLast.add(true);
                        }
                        i++;
                    }
                }
            }
        }
        Collections.sort(titulos);
        iniciarImagens();
    }

    public void preencheImagens(String caminho) {
        //System.out.println(caminho);
        total++;
        jButton1.setEnabled(false);
        ImageIcon im = new ImageIcon(caminho);
        im.setImage(im.getImage().getScaledInstance(jLabel1.getWidth(), jLabel1.getHeight(), 100));
        imagem.add(im);
        System.out.println(total);
    }

    public void iniciarImagens() {
        jLabel1.setIcon(imagem.get(0));
        this.setTitle(titulos.get(0));
        testeButton2();
        String ces[] = titulos.get(index).split("-");
        cesAtual = ces[0];
        posEventAtual = Integer.parseInt(ces[1]);

        String events[] = ces[2].split(".png");
        eventAtual = events[0];
        atualizaLogs();
        addLabels();
        preencheComboCes();
        addLabelsPainel();
        vaiParaImagem();
    }

    public void atualizaPainelMais() {

        Collections.sort(titulos);

        String cesPresente[] = titulos.get(index).split("-");
        String getCesPresente[] = cesPresente[0].split("S");
        int valorPresente = Integer.parseInt(getCesPresente[1], 10);

        String cesPassado[] = titulos.get(index - 1).split("-");
        String getCesPassado[] = cesPassado[0].split("S");
        int valorPassado = Integer.parseInt(getCesPassado[1], 10);

        if (valorPresente == valorPassado) {
            for (JLabel l : labels) {
                if (l.getText().equals(titulos.get(index))) {
                    if (erro) {
                        l.setOpaque(true);
                        l.setForeground(Color.red);
                        l.setBackground(Color.black);
                    } else {
                        l.setOpaque(true);
                        l.setForeground(Color.green);
                        l.setBackground(Color.black);
                    }

                } else {
                    l.setOpaque(false);
                    l.setForeground(Color.black);
                }
            }
        } else {
            int prox = jComboBox2.getSelectedIndex();
            prox++;
            jComboBox2.setSelectedIndex(prox);
        }
    }

    public void atualizaPainelMenos() {

        Collections.sort(titulos);
        String cesPresente[] = titulos.get(index).split("-");
        String getCesPresente[] = cesPresente[0].split("S");
        int valorPresente = Integer.parseInt(getCesPresente[1], 10);

        String cesFuturo[] = titulos.get(index + 1).split("-");
        String getCesFuturo[] = cesFuturo[0].split("S");
        int valorFuturo = Integer.parseInt(getCesFuturo[1], 10);

        if (valorPresente == valorFuturo) {
            for (JLabel l : labels) {
                if (l.getText().equals(titulos.get(index))) {
                    if (erro) {
                        l.setOpaque(true);
                        l.setForeground(Color.red);
                        l.setBackground(Color.black);
                    } else {
                        l.setOpaque(true);
                        l.setForeground(Color.green);
                        l.setBackground(Color.black);
                    }

                } else {
                    l.setOpaque(false);
                    l.setForeground(Color.black);
                }
            }
        } else {
            int ant = jComboBox2.getSelectedIndex();
            ant--;
            jComboBox2.setSelectedIndex(ant--);
        }
    }

    public void vaiParaImagem() {
        int cont = 0;
        int first = 0;
        String cesTitulo = jComboBox2.getSelectedItem().toString();
        for (JLabel l : labels) {
            if (l.getText().contains(cesTitulo) && first == 0) {
                index = cont;
                first = 1;

                jLabel1.setIcon(imagem.get(index));
                this.setTitle(titulos.get(index));
                testeButton2();
                String ces[] = titulos.get(index).split("-");
                cesAtual = ces[0];
                posEventAtual = Integer.parseInt(ces[1]);
                String events[] = ces[2].split(".png");
                eventAtual = events[0];
                testeButtons();
                atualizaLogs();
                if (erro) {
                    l.setOpaque(true);
                    l.setForeground(Color.red);
                    l.setBackground(Color.black);
                } else {
                    l.setOpaque(true);
                    l.setForeground(Color.green);
                    l.setBackground(Color.black);
                }

            } else {
                l.setOpaque(false);
                l.setForeground(Color.black);
            }
            cont++;
        }

    }

    public void vaiParaImagem(MouseEvent ev) {
        jLabel = (JLabel) ev.getSource();

        int cont = 0;
        for (JLabel l : labels) {
            if (l.equals(jLabel)) {
                index = cont;
                jLabel1.setIcon(imagem.get(index));
                this.setTitle(titulos.get(index));
                testeButton2();
                String ces[] = titulos.get(index).split("-");
                cesAtual = ces[0];
                posEventAtual = Integer.parseInt(ces[1]);
                String events[] = ces[2].split(".png");
                eventAtual = events[0];
                testeButtons();
                atualizaLogs();
                if (erro) {
                    l.setOpaque(true);
                    l.setForeground(Color.red);
                    l.setBackground(Color.black);
                } else {
                    l.setOpaque(true);
                    l.setForeground(Color.green);
                    l.setBackground(Color.black);
                }

            } else {
                l.setOpaque(false);
                l.setForeground(Color.black);
            }
            cont++;
        }

    }

    public void addLabels() {
        labels.clear();
        Collections.sort(titulos);
        for (String label : titulos) {
            jLabel = new JLabel();
            jLabel.setText(label);
            labels.add(jLabel);
            jLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent ev) {
                    vaiParaImagem(ev);

                }

            });
        }

    }

    public void addLabelsPainel() {
        String testeAtual = jComboBox2.getSelectedItem().toString();
        jPanel.removeAll();
        jPanel.validate();
        ArrayList<String> ordenados = new ArrayList<String>();
        int cont = 0;
        for (String c : titulos) {
            if (c.contains(testeAtual)) {
                //ordenados.add(c);
                jPanel.add(labels.get(cont));
            }
            cont++;
        }
        /*cont = 0;
        int x = 0, y = 0;
        for (String c : ordenados) {
            String pos[] = c.split("-");
            x = Integer.parseInt(pos[1]);
            for (String k : ordenados) {
                String pos2[] = k.split("-");
                y = Integer.parseInt(pos2[1]);
                if (x > y) {
                    Collections.swap(ordenados, x, y);
                }
            }
        }
        ArrayList<JLabel> lablesOrdenadas = new ArrayList<JLabel>();
        for(String c: ordenados){
            JLabel l = new JLabel();
        }*/


        jPanel.setLayout(new javax.swing.BoxLayout(jPanel, javax.swing.BoxLayout.Y_AXIS));
        jScrollPane2.setViewportView(jPanel);
    }

    public void preencheComboCes() {
        int cont = 0;
        for (String c : titulos) {
            String ces[] = c.split("-");
            String nomeCes = ces[0];
            if (nomesCes.isEmpty()) {
                nomesCes.add(nomeCes);
                jComboBox2.addItem(nomeCes);
            } else {
                if (!nomesCes.contains(nomeCes)) {
                    nomesCes.add(nomeCes);
                    jComboBox2.addItem(nomeCes);
                }
            }
        }

    }

    public int retornaPosicao(String posicao) {
        int pos = 0;
        pos = Integer.parseInt(posicao);
        return pos;
    }

    public boolean carregarImagens(String caminho) {
        String imagem = "";
        int i = 0;
        String posicao[];
        StringBuilder sb = new StringBuilder();
        File raiz = new File(caminho);
        for (File f : raiz.listFiles()) {
            if (f.isFile()) {
                System.out.println(f.getName());
                if (f.getName().contains(".png")) {
                    i++;
                }
                sb.append(f.getName());
                sb.append("\n");
            }
        }
        if (i == 0) {
            JOptionPane.showMessageDialog(null, "Arquivos não encontrada na Pasta!", "Aviso!", JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        int cont = 0;
        int posAnterior = 0;
        int aux = 0;
        boolean ok = false;
        System.out.println("Tamanho: " + caminhos.size() + " caminho: " + caminho);
        for (File f : raiz.listFiles()) {
            if (f.isFile()) {
                System.out.println(f.getName());
                if (f.getName().contains(".png")) {
                    posicao = f.getName().split("#");
                    int c = retornaPosicao(posicao[0]);
                    System.out.println("retornaPosicao: " + c + " cont: " + cont);
                    caminhos.add(caminho + "\\" + f.getName());
                    nomes.add(f.getName());

                }

                posAnterior = cont;
                cont++;
            }
            sb.append(f.getName());
            sb.append("\n");
        }


        /*for (cont = 0; cont < i; cont++) {
         System.out.println(caminhos[cont]);
         //preencheImagens(caminhos[cont],i);
         }*/
        //iniciarImagens();
        Collections.sort(caminhos);
        Collections.sort(nomes);
        return true;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jComboBox2 = new javax.swing.JComboBox();
        jScrollPane2 = new javax.swing.JScrollPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jButton1.setText("Anterior");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Próxima");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 3, true));

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Log Completo", "Log da Aplicação", "Sem Log" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jComboBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox2ActionPerformed(evt);
            }
        });

        jScrollPane2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jScrollPane2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 72, Short.MAX_VALUE))
                    .addComponent(jScrollPane2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 569, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 360, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                            .addComponent(jScrollPane2))))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public String splitNome(String nome) {
        String nomes[];
        nomes = nome.split("#");
        return nomes[1];
    }

    private void testeButton2() {
        if (total == 1) {
            jButton2.setEnabled(false);
        }
    }

    private void testeButtons() {
        if (index == (total - 1)) {
            jButton2.setEnabled(false);
            jButton1.setEnabled(true);
        } else {
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);

        }

        if (index == 0) {
            jButton2.setEnabled(true);
            jButton1.setEnabled(false);
        } else {
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
        }
    }


    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        index--;
        if (index == 0) {
            jButton2.setEnabled(true);
            jButton1.setEnabled(false);
        } else {
            jButton1.setEnabled(true);
            jButton2.setEnabled(true);
        }
        jLabel1.setIcon(imagem.get(index));
        this.setTitle(titulos.get(index));
        String ces[] = titulos.get(index).split("-");
        cesAtual = ces[0];
        posEventAtual = Integer.parseInt(ces[1]);
        String events[] = ces[2].split(".png");
        eventAtual = events[0];
        atualizaLogs();
        atualizaPainelMenos();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        index++;
        //System.out.println("INDEX: "+index+" TOTAL: "+total);
        if (index == (total - 1)) {
            jButton2.setEnabled(false);
            jButton1.setEnabled(true);
        } else {
            jButton2.setEnabled(true);
            jButton1.setEnabled(true);

        }
        jButton1.setEnabled(true);
        jLabel1.setIcon(imagem.get(index));
        this.setTitle(titulos.get(index));
        String ces[] = titulos.get(index).split("-");
        cesAtual = ces[0];
        posEventAtual = Integer.parseInt(ces[1]);

        String events[] = ces[2].split(".png");
        eventAtual = events[0];
        atualizaLogs();
        atualizaPainelMais();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        if (jComboBox1.getSelectedIndex() == 0) {
            atualizaLogs();
        }
        if (jComboBox1.getSelectedIndex() == 1) {
            atualizaLogs();
        }
        if (jComboBox1.getSelectedIndex() == 2) {
            atualizaLogs();
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        // TODO add your handling code here:
        addLabelsPainel();
        vaiParaImagem();
    }//GEN-LAST:event_jComboBox2ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Visualizador.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Visualizador().setVisible(true);
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
}
