/**
 * Componente interno que representa um sensor individual da máquina.
 * Atua como uma Parte na relação de composição (1:N) com a classe Maquina.
 */
public class Sensor {
    private String tipo;
    private boolean operacional;

    /**
     * Construtor para inicializar um sensor com seu tipo correspondente.
     * 
     * @param tipo Tipo ou finalidade do sensor (ex: Temperatura, Pressão).
     * @throws IllegalArgumentException Se o tipo for nulo ou vazio.
     */
    public Sensor(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("O tipo do sensor não pode ser vazio.");
        }
        this.tipo = tipo;
        this.operacional = true;
    }

    /**
     * Obtém o tipo ou funcionalidade do sensor.
     * 
     * @return O tipo do sensor.
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Verifica se o sensor está operando normalmente.
     * 
     * @return true se o sensor estiver operacional, false caso contrário.
     */
    public boolean isOperacional() {
        return operacional;
    }

    /**
     * Gera o relatório de diagnóstico do sensor considerando a saúde da máquina.
     * 
     * @param saudeMaquina Percentual de saúde da máquina (0 a 100).
     * @return String formatada contendo o status do sensor.
     */
    public String gerarRelatorioDiagnostico(float saudeMaquina) {
        String status;
        if (saudeMaquina <= 0) {
            status = "Inoperante (Desligamento de Emergência)";
        } else if (saudeMaquina < 20.0f) {
            status = "Alerta de Leitura Inconstante";
        } else {
            status = "Operacional";
        }
        return "[Sensor - " + tipo + "] Status: " + status;
    }
}