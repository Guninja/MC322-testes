/**
 * Componente interno responsável pelo controle e firmware da máquina.
 */
public class Controlador {
    private String versaoFirmware;
    private boolean sistemaOk;

    /**
     * Construtor para inicializar o controlador da máquina.
     * 
     * @param versaoFirmware Versão do sistema de controle.
     * @throws IllegalArgumentException Se a versão for nula ou vazia.
     */
    public Controlador(String versaoFirmware) {
        if (versaoFirmware == null || versaoFirmware.trim().isEmpty()) {
            throw new IllegalArgumentException("A versão do firmware não pode ser vazia.");
        }
        this.versaoFirmware = versaoFirmware;
        this.sistemaOk = true;
    }

    /**
     * Verifica se o sistema do controlador está operando sem falhas.
     * 
     * @return true se o sistema estiver OK, false em caso de falha.
     */
    public boolean isSistemaOk() {
        return sistemaOk;
    }

    /**
     * Reseta o sistema do controlador, reestabelecendo o status operacional.
     */
    public void resetarSistema() {
        this.sistemaOk = true;
    }

    /**
     * Gera o relatório de diagnóstico do controlador com base na saúde da máquina.
     * 
     * @param saudeMaquina Percentual de saúde da máquina (0 a 100).
     * @return String formatada com as informações do controlador.
     */
    public String gerarRelatorioDiagnostico(float saudeMaquina) {
        String status = (saudeMaquina > 0 && sistemaOk) ? "Sincronizado" : "Falha de Comunicação";
        return "[Controlador] Firmware: " + versaoFirmware + " | Status: " + status;
    }
}

```