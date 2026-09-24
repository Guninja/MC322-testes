public abstract class Maquina {
    private String nome;
    private boolean ligada=false; //é o status da máquina se on/off
    private int capacidadeMaxima;//capacidade de guardar matéria prima usada na produção atual
    private float probabilidadeFalha;
    private float custOperacao;
    private float saude;//sem health, aqui é Brasil

    public Maquina(String nomeado, int capacidade, float probabilidadeDefalha, float custoOperacao){
        nome=nomeado;
        capacidadeMaxima=capacidade;
        probabilidadeFalha = probabilidadeDefalha;
        this.custOperacao = custoOperacao;
        saude = 100.0f;
    }

    public abstract boolean processar(Produto obraPrima, MateriaPrima material);

    public abstract String getTipo();

    public boolean precisaManutencao(){
        return saude < 20.0f;
    }

    public void ligar(){
        if(saude <= 0){
            System.out.println("maquina quebrada");
        }else{
            ligada=true;
            System.out.println("Máquina Ligada...");
        }
    }

    public void desligar(){
        ligada=false;
        System.out.println("Máquina desligada...");
    }

    protected void desgate(){
        float reducao =(float) (3 * Math.random());
        saude = saude - reducao;
        if (saude < 0){
            saude = 0;
        }
    }

    protected boolean verificarFalha(){
        if (saude <= 0) return true;
        float chanceFalhar = probabilidadeFalha + (1.0f - probabilidadeFalha) * ((100.0f - saude) / 100.0f);
        return Math.random() < chanceFalhar;
    }

    public void reparar(){
        saude = 100.0f;
        System.out.println("Máquina consertada!");
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

    public float getSaude(){
        return saude;
    }



    public String gerarRelatorioDiagnostico(){
        String textoDiagnostico = precisaManutencao() ? "Máquina com defeito: precisa de manutenção" : "Manutenção em dia :)";
        return "\n | Máquina: "+nome+" | Saúde: "+saude+" | Risco de Falha: "+probabilidadeFalha+" | Status: "+textoDiagnostico;
    }  
}  