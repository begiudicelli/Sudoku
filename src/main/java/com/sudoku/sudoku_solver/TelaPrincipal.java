package com.sudoku.sudoku_solver;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class TelaPrincipal {

	private JFrame frame;
	private JPanel boardPanel;
	private JButton btnVerificar;

	private Tabuleiro tabuleiro;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				TelaPrincipal window = new TelaPrincipal();
				window.frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TelaPrincipal() {
		initialize();
	}

	private void initialize() {
	    frame = new JFrame();
	    frame.setBounds(100, 100, 801, 600);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.getContentPane().setLayout(new BorderLayout());

	    // Painel para o tabuleiro do Sudoku
	    boardPanel = new JPanel();
	    boardPanel.setLayout(new GridLayout(9, 9)); // Cria uma grade 9x9

	    // Adiciona células
	    for (int i = 0; i < 81; i++) {
	        JTextField celula = new JTextField("");
	        celula.setHorizontalAlignment(SwingConstants.CENTER);
	        celula.setFont(new Font("Arial", Font.PLAIN, 20));
	        celula.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

	        // Adiciona DocumentListener para detectar mudanças no conteúdo
	        celula.getDocument().addDocumentListener(new DocumentListener() {
	            @Override
	            public void insertUpdate(DocumentEvent e) {
	                mudarCorTexto(celula);
	            }

	            @Override
	            public void removeUpdate(DocumentEvent e) {
	                mudarCorTexto(celula);
	            }

	            @Override
	            public void changedUpdate(DocumentEvent e) {
	                mudarCorTexto(celula);
	            }
	        });

	        boardPanel.add(celula);
	    }

	    // Adiciona painel do tabuleiro
	    frame.getContentPane().add(boardPanel, BorderLayout.CENTER);

	    // Painel para os botões de controle
	    JPanel controlPanel = new JPanel();
	    controlPanel.setLayout(new FlowLayout());

	    // ComboBox para selecionar a dificuldade
	    String[] dificuldades = {"Easy", "Medium", "Hard"};
	    JComboBox<String> comboDificuldade = new JComboBox<>(dificuldades);
	    controlPanel.add(comboDificuldade);

	    // Botão de Verificar
	    btnVerificar = new JButton("Verificar");
	    btnVerificar.addActionListener(e -> verificarSolução());
	    controlPanel.add(btnVerificar);

	    // Adiciona o painel de controle (com a ComboBox e o botão) ao frame
	    frame.getContentPane().add(controlPanel, BorderLayout.SOUTH);

	    // Botão para obter o tabuleiro da API
	    JButton btnObterTabuleiro = new JButton("Obter Tabuleiro");
	    btnObterTabuleiro.addActionListener(e -> {
	        // Aqui você pode acessar a dificuldade selecionada
	        String dificuldadeSelecionada = (String) comboDificuldade.getSelectedItem();
	        System.out.println("Dificuldade selecionada: " + dificuldadeSelecionada);

	        // Passar a dificuldade para o método da API (modifique isso conforme a necessidade)
	        tabuleiro = APIService.obterTabuleiro(); // Usando o método da APIService
	        atualizarTabuleiro();
	    });

	    // Colocando o botão "Obter Tabuleiro" no topo
	    JPanel obterTabuleiroPanel = new JPanel();
	    obterTabuleiroPanel.add(btnObterTabuleiro);
	    frame.getContentPane().add(obterTabuleiroPanel, BorderLayout.NORTH);
	}


	// Método para mudar a cor do texto para azul
	private void mudarCorTexto(JTextField celula) {
		if (!celula.getText().isEmpty()) {
			celula.setForeground(Color.BLUE); // Quando o usuário digitar algo, ficará azul
		} else {
			celula.setForeground(Color.BLACK); // Caso a célula esteja vazia, o texto será preto
		}
	}

	private void atualizarTabuleiro() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JTextField cell = (JTextField) boardPanel.getComponent(i * 9 + j);
				int valor = tabuleiro.getValor(i, j);
				boolean editavel = tabuleiro.isEditavel(i, j);

				Color corCelula;
				// Alterando a cor do fundo das células 3x3
				if ((i / 3 + j / 3) % 2 == 0) {
					corCelula = new Color(240, 240, 240); // Cor clara
				} else {
					corCelula = new Color(220, 220, 220); // Cor mais escura
				}
				cell.setBackground(corCelula);

				if (valor != 0) {
					cell.setText(String.valueOf(valor));
					cell.setEditable(false);
					cell.setForeground(Color.BLACK);  // Cor preta para valores da API
				} else {
					cell.setText("");
					cell.setEditable(editavel);
					cell.setForeground(Color.BLACK);  // Cor preta para células vazias
				}
			}
		}
	}

	private void verificarSolução() {
		int[][] solucao = tabuleiro.getSolucoes();
		boolean solucaoCorreta = true;
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				JTextField cell = (JTextField) boardPanel.getComponent(i * 9 + j);
				String textoCelula = cell.getText();
				if (!textoCelula.isEmpty()) {
					int valorUsuario = Integer.parseInt(textoCelula);
					int valorSolucao = solucao[i][j];

					if (valorUsuario != valorSolucao) {
						solucaoCorreta = false;
						break;
					}
				}
			}
		}
		if (solucaoCorreta) {
			JOptionPane.showMessageDialog(null, "A solução está correta!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null, "A solução está incorreta. Tente novamente!", "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
