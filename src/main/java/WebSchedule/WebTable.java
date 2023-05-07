package WebSchedule;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
public class WebTable extends JFrame {

   private JPanel[] telas;
   private int semanaAtual = 0;
   
   // Array com os dias da semana correspondentes ao mês de maio
   private String[] diasSemana = {"", "1/05", "2/05", "3/05", "4/05", "5/05", "6/05", "7/05"};

   public WebTable() {
      setTitle("Horário de Estudante");
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(800, 600);

      // Cria os painéis para as telas do calendário
      telas = new JPanel[4];
      for (int i = 0; i < telas.length; i++) {
         telas[i] = new JPanel(new BorderLayout());
         
         // Cria o cabeçalho da tabela
         JPanel cabecalho = new JPanel(new GridLayout(2, 8));
         cabecalho.add(new JLabel(""));
         for (int j = 1; j <= 7; j++) {
            cabecalho.add(new JLabel(diasSemana[j]));
         }
         telas[i].add(cabecalho, BorderLayout.NORTH);
         
         // Adicione aqui os componentes desejados para exibir as informações do calendário
         DefaultTableModel modelo = new DefaultTableModel();
         JTable tabela = new JTable(modelo);
         // Adicione as colunas para as horas do dia
         modelo.addColumn("");
         for (int j = 0; j < 7; j++) {
            modelo.addColumn("");
         }
         // Adicione as linhas para cada hora do dia
         for (int j = 0; j < 24; j++) {
            Object[] linha = new Object[8];
            linha[0] = String.format("%02d:00", j);
            modelo.addRow(linha);
         }
         telas[i].add(new JScrollPane(tabela), BorderLayout.CENTER);
      }

      // Adiciona a primeira tela do calendário ao JFrame
      getContentPane().add(telas[semanaAtual]);

      // Cria o botão para navegar entre as semanas
      JButton btnNavegar = new JButton("Próxima Semana");
      btnNavegar.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            // Remove a tela atual do JFrame
            getContentPane().remove(telas[semanaAtual]);
            // Atualiza a semana atual
            semanaAtual = (semanaAtual + 1) % telas.length;
            // Adiciona a nova tela do calendário ao JFrame
            getContentPane().add(telas[semanaAtual]);
            // Redesenha a janela
            revalidate();
            repaint();
         }
      });

      // Adiciona o botão de navegação abaixo da tela do calendário
      getContentPane().add(btnNavegar, BorderLayout.SOUTH);
   }

   public static void main(String[] args) {
	   WebTable horarioEstudante = new WebTable();
      horarioEstudante.setVisible(true);
   }
}
