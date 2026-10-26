import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe abstrata que serve de base para os diferentes tipos de maquinas da planta industrial.
 * Define o comportamento padrao de funcionamento, desgaste, falhas e manutencao.
 * 
 * @author Júlio Marcos
 */
public abstract class Maquina implements Auditavel {
    private String nome;
    private boolean ligada = false; // status da maquina (on/off)
    private int capacidadeMaxima; // capacidade de guardar materia prima usada na producao atual
    private float probabilidadeFalha;
    private int custoOperacao;
    private float saude; // porcentagem de saude da maquina (100.0 a 0.0)
    private int fatorDesgaste;

    // Componentes Internos (Composição Estrita)
    private Motor motor;               // Relação 1:1
    private Controlador controlador;   // Relação 1:1
    private List<Sensor> sensores;     // Relação 1:N

    /**
     * Construtor para inicializar os atributos da maquina e validar os parametros fornecidos.
     * Instancia tambem os componentes internos no momento da construcao.
     * 
     * @param nomeado Nome identificador da maquina.
     * @param capacidade Capacidade maxima de armazenamento.
     * @param probabilidadeDefalha Probabilidade base de falha (deve estar entre 0.0 e 1.0).
     * @param custoOperacao Custo para operar a maquina.
     * @param fatorDesgaste Fator que influencia no desgaste da saude.
     * @throws IllegalArgumentException Se algum dos parametros for invalido.
     */
    public Maquina(String nomeado, int capacidade, float probabilidadeDefalha, int custoOperacao, int fatorDesgaste) {
        if (nomeado == null || nomeado.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da maquina nao pode ser nulo ou vazio.");
        }
        if (capacidade <= 0) {
            throw new IllegalArgumentException("A capacidade maxima deve ser maior que zero.");
        }
        if (probabilidadeDefalha < 0.0f || probabilidadeDefalha > 1.0f) {
            throw new IllegalArgumentException("A probabilidade de falha deve estar entre 0.0 e 1.0.");
        }
        if (custoOperacao < 0) {
            throw new IllegalArgumentException("O custo de operacao nao pode ser negativo.");
        }
        if (fatorDesgaste < 0) {
            throw new IllegalArgumentException("O fator de desgaste nao pode ser negativo.");
        }

        this.nome = nomeado;
        this.capacidadeMaxima = capacidade;
        this.probabilidadeFalha = probabilidadeDefalha;
        this.custoOperacao = custoOperacao;
        this.saude = 100.0f;
        this.fatorDesgaste = fatorDesgaste;

        // Composição estrita: instanciação dos componentes internos no construtor
        this.motor = new Motor(150.0);
        this.controlador = new Controlador("v2.4");
        this.sensores = new ArrayList<>();
        this.sensores.add(new Sensor("Temperatura"));
        this.sensores.add(new Sensor("Pressão"));
    }

    /**
     * Processa um produto utilizando a materia prima fornecida.
     * 
     * @param obraPrima Produto a ser processado.
     * @param material Materia prima a ser utilizada.
     * @return true se o processamento ocorreu com sucesso, false caso contrario.
     */
    public abstract boolean processar(Produto obraPrima, MateriaPrima material);

    /**
     * Retorna o tipo especifico da maquina.
     * 
     * @return Nome do tipo da maquina.
     */
    public abstract String getTipo();

    /**
     * Define se a maquina precisa ou nao de manutencao preventiva.
     * 
     * @return true se a saude for menor que 20%, false caso contrario.
     */
    public boolean precisaManutencao() {
        return saude < 20.0f;
    }

    /**
     * Liga a maquina e aciona o motor. Se a saude estiver zerada, realiza o conserto automatico antes de ligar.
     */
    public void ligar() {
        if (saude <= 0) {
            System.out.println("maquina quebrada");
            System.out.println("Consertanto...");
            reparar();
        } else {
            ligada = true;
            motor.ligar();
            System.out.println("Máquina Ligada...");
        }
    }

    /**
     * Desliga a maquina e interrompe o motor.
     */
    public void desligar() {
        ligada = false;
        motor.desligar();
        System.out.println("Máquina desligada...");
    }

    /**
     * Aplica um desgaste aleatorio na saude da maquina com base no fator de desgaste.
     */
    protected void desgate() {
        float reducao = (float) (fatorDesgaste * Math.random());
        saude = saude - reducao;
        if (saude < 0) {
            saude = 0;
        }
    }

    /**
     * Verifica se a maquina falhou com base na probabilidade e na saude atual.
     * 
     * @return true se a maquina falhar, false caso contrario.
     */
    protected boolean verificarFalha() {
        if (saude <= 0) return true;
        float chanceFalhar = probabilidadeFalha + (1.0f - probabilidadeFalha) * ((100.0f - saude) / 100.0f);
        return Math.random() < chanceFalhar;
    }

    /**
     * Repara a maquina, restaurando sua saude para 100% e resetando o sistema do controlador.
     */
    public void reparar() {
        saude = 100.0f;
        controlador.resetarSistema();
        System.out.println("Máquina consertada!");
    }

    /**
     * Obtem a lista nao modificavel dos sensores da maquina.
     * Protege a colecao contra modificacoes externas indesejadas.
     * 
     * @return Lista imutavel de sensores.
     */
    public List<Sensor> getSensores() {
        return Collections.unmodifiableList(sensores);
    }

    /**
     * Obtem o nome da maquina.
     * 
     * @return Nome da maquina em texto.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Verifica se a maquina esta ligada.
     * 
     * @return true se ligada, false se desligada.
     */
    public boolean estaLigada() {
        return ligada;
    }

    /**
     * Obtem o custo de operacao da maquina.
     * 
     * @return Custo de operacao.
     */
    public int getCustoOperacao() {
        return custoOperacao;
    }

    /**
     * Obtem a capacidade maxima da maquina.
     * 
     * @return Capacidade maxima.
     */
    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    /**
     * Obtem o valor percentual da saude atual da maquina.
     * 
     * @return Saude atual em float.
     */
    public float getSaude() {
        return saude;
    }

    /**
     * Gera uma String com o relatorio de diagnostico da maquina e delega a geração para seus componentes.
     * 
     * @return Texto com o status, saude, risco de falha e relatorio dos componentes internos.
     */
    public String gerarRelatorioDiagnostico() {
    String textoDiagnostico = precisaManutencao() ? "Máquina com defeito: precisa de manutenção" : "Manutenção em dia :)";
    
    String relatorio = "\n | Máquina: " + nome + " | Saúde: " + saude + " | Risco de Falha: " + probabilidadeFalha + " | Status: " + textoDiagnostico + "\n";
    relatorio += "   -> " + motor.gerarRelatorioDiagnostico(saude) + "\n";
    relatorio += "   -> " + controlador.gerarRelatorioDiagnostico(saude) + "\n";
    
    for (Sensor s : sensores) {
        relatorio += "   -> " + s.gerarRelatorioDiagnostico(saude) + "\n";
    }

    return relatorio;
    }
}