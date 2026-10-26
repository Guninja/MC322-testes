/**
 * Componente interno que representa o motor de uma máquina na planta industrial
 * 
 * @author Júlio Marcos
 */
public class Motor {
    private double potencia;
    private boolean emFuncionamento;

    /**
     * Construtor para inicializar o motor com uma potência específica
     * 
     * @param potencia Potência do motor em CV
     * @throws IllegalArgumentException Se a potência for menor ou igual a zero
     */
    public Motor(double potencia) {
        if (potencia <= 0) {
            throw new IllegalArgumentException("A potência do motor deve ser maior que zero.");
        }
        this.potencia = potencia;
        this.emFuncionamento = false;
    }

    /**
     * Liga o motor
     */
    public void ligar() {
        this.emFuncionamento = true;
    }

    /**
     * Desliga o motor
     */
    public void desligar() {
        this.emFuncionamento = false;
    }

    /**
     * Verifica se o motor está em operação
     * 
     * @return true se o motor estiver ligado, false caso contrário
     */
    public boolean isEmFuncionamento() {
        return emFuncionamento;
    }

    /**
     * Obtém a potência do motor
     * 
     * @return Valor da potência em CV
     */
    public double getPotencia() {
        return potencia;
    }

    /**
     * Gera o relatório de diagnóstico do motor considerando o nível de saúde da máquina
     * 
     * @param saudeMaquina Percentual de saúde da máquina (0 a 100)
     * @return String formatada contendo o status do motor
     */
    public String gerarRelatorioDiagnostico(float saudeMaquina) {
        String desempenho;
        if (!emFuncionamento) {
            desempenho = "Desligado";
        } else if (saudeMaquina < 30.0f) {
            desempenho = "Rendimento Reduzido (Alerta de Superaquecimento)";
        } else {
            desempenho = "Operação Normal";
        }
        return "[Motor] Potência: " + potencia + " CV | Status: " + desempenho;
    }
}