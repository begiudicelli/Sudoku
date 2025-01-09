package com.sudoku.sudoku_solver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.JOptionPane;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIService {
    private static final String API_URL = "https://sudoku-api.vercel.app/api/dosuku";
        
    public static String getApiUrl() {
        return API_URL;
    }

    public static Tabuleiro obterTabuleiro() {
        Tabuleiro tabuleiro = new Tabuleiro();

        try {
            // Conectar-se à API para obter o Sudoku
            @SuppressWarnings("deprecation")
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            // Ler a resposta da API
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Converter a resposta JSON em um objeto JSONObject
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONObject newboard = jsonResponse.getJSONObject("newboard");
            JSONArray grids = newboard.getJSONArray("grids");
            JSONObject grid = grids.getJSONObject(0);
            JSONArray board = grid.getJSONArray("value");
            JSONArray solution = grid.getJSONArray("solution");

            // Preencher o Tabuleiro com valores da API
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    int valor = board.getJSONArray(i).getInt(j);
                    boolean editavel = valor == 0;  // Se for 0, a célula é vazia e editável
                    tabuleiro.preencherCelula(i, j, valor, editavel);
                }
            }
            
            // Preencher a solução
            int[][] solucao = new int[9][9];
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    solucao[i][j] = solution.getJSONArray(i).getInt(j);
                }
            }
            tabuleiro.setSolucoes(solucao);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Erro ao carregar o Sudoku", "Erro", JOptionPane.ERROR_MESSAGE);
        }

        return tabuleiro;
    }
    
    
    
}
