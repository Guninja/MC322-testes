public abstract class Maquina {
    private String nome;
    private boolean ligada=false; //é o status da máquina se on/off
    private int capacidadeMaxima;//capacidade de guardar matéria prima usada na produção atual
    private float probabilidadeFalha;
    private float custOperacao;

    public Maquina(String nomeado, int capacidade, float probabilidadeDefalha, float custoOperacao){
        nome=nomeado;
        capacidadeMaxima=capacidade;
        probabilidadeFalha = probabilidadeDefalha;
        this.custOperacao = custoOperacao;
    }

    public abstract boolean processar(Produto obraPrima, MateriaPrima material);

    public abstract String getTipo();

    public void ligar(){
        ligada=true;
        System.out.println("Máquina Ligada...");
    }

    public void desligar(){
        ligada=false;
        System.out.println("Máquina desligada...");
    }

    protected boolean verificarFalha(){
        return Math.random() < probabilidadeFalha;
    }

    public String getNome(){
        return nome;
    }

    public boolean estaLigada(){
        return ligada;
    }

    public float getcustOperacao(){
        return custOperacao;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }
}