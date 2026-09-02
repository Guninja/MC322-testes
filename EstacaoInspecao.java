public class EstacaoInspecao {
    private boolean ativa;
    private int produtosInspecionados=0;

    public EstacaoInspecao(boolean ligado){
        ativa=ligado;
    }

    public void ativar(){
        ativa=true;
        System.out.println("Inspeção ativa...");
    }

    public void desativar(){
        ativa=false;
        System.out.println("Inspeção desativada...");
    }

    public boolean inspecionar(Produto item){
        if(!ativa){
            System.out.println("Falha na inspeção, estação está desativada!\nSe você quer fabricar, ligue a fabrica :)");
            return false;
        }else{
            item.avaliar();
            produtosInspecionados++;
            System.out.println("Produto inspecionado com sucesso ;) ...");
            return true;
        }
    }

    public int getTotalInspecionados(){
        return produtosInspecionados;
    }
}
