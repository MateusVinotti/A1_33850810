import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessadorDeAlunos {

    public static void main(String[] args) {
        // Caminho absoluto para o arquivo alunos.csv
        String arquivoCSV = "C:\\Users\\autologon\\Downloads\\provajava\\provajava\\alunos.csv";
        List<Aluno> listaDeAlunos = new ArrayList<>();

        // Leitura do arquivo CSV e processamento dos dados
        try (BufferedReader br = new BufferedReader(new FileReader(arquivoCSV))) {
            String linha;
            boolean primeiraLinha = true; // Flag para verificar a primeira linha (cabeçalho)
            while ((linha = br.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue; // Pular a primeira linha (cabeçalho)
                }
                String[] dados = linha.split(";");
                try {
                    int matricula = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    double nota = Double.parseDouble(dados[2].trim().replace(",", "."));

                    Aluno aluno = new Aluno(matricula, nome, nota);
                    listaDeAlunos.add(aluno);
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter número na linha: " + linha);
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
            e.printStackTrace();
        }

        // Cálculo das estatísticas
        int quantidadeDeAlunos = listaDeAlunos.size();
        int aprovados = 0;
        int reprovados = 0;
        double menorNota = Double.MAX_VALUE;
        double maiorNota = Double.MIN_VALUE;
        double somaDasNotas = 0;

        for (Aluno aluno : listaDeAlunos) {
            double nota = aluno.getNota();
            somaDasNotas += nota;
            if (nota >= 6.0) {
                aprovados++;
            } else {
                reprovados++;
            }
            if (nota < menorNota) {
                menorNota = nota;
            }
            if (nota > maiorNota) {
                maiorNota = nota;
            }
        }

        double mediaGeral = somaDasNotas / quantidadeDeAlunos;

        // Antes de escrever no arquivo resumo.csv
        System.out.println("Menor Nota: " + menorNota);
        System.out.println("Maior Nota: " + maiorNota);
        System.out.println("Média Geral: " + mediaGeral);

        // Escrita dos resultados no arquivo resumo.csv
        String arquivoResumo = "C:\\Users\\autologon\\Downloads\\provajava\\provajava\\resumo.csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(arquivoResumo))) {
            writer.write("Quantidade de Alunos na Turma: " + quantidadeDeAlunos + "\n");
            writer.write("Quantidade de Aprovados: " + aprovados + "\n");
            writer.write("Quantidade de Reprovados: " + reprovados + "\n");
            writer.write("Menor Nota: " + menorNota + "\n");
            writer.write("Maior Nota: " + maiorNota + "\n");
            writer.write("Média Geral: " + mediaGeral + "\n");
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo resumo.csv: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

class Aluno {
    private int matricula;
    private String nome;
    private double nota;

    public Aluno(int matricula, String nome, double nota) {
        this.matricula = matricula;
        this.nome = nome;
        this.nota = nota;
    }

    // Getters e setters para os atributos (matricula, nome, nota)
    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
}
