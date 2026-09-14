import java.util.ArrayList;

public class GerenciadorProducao {
    private ArrayList<Demanda> demandas;
    private ArrayList<Produto> produtosFabricados;
    private ArrayList<Maquina> maquinas;
    private MateriaPrima materiaPrima;
    private float budget;

    public GerenciadorProducao(float dinheiro, MateriaPrima materiaprima){
        demandas = new ArrayList<>();
        produtosFabricados = new ArrayList<>();
        maquinas = new ArrayList<>();
        materiaPrima = materiaprima;
        budget = dinheiro;
    }


    public void registrarDemanda(Demanda novaDemanda){
        demandas.add(novaDemanda);
    }

    public void atualizarDemanda(){
        if(!demandas.isEmpty()){
            demandas.remove(0);
            System.out.println("Demanda atualizada");
        } else{
            System.out.println("Sem demanda :(");
        }
    }

    public void fabricarDemanda(){
        Demanda demandaAtual = demandas.get(0);
        Produto produto = new ProdutoGalvanizado("PROD" + (produtosFabricados.size() + 1), demandaAtual.getTipoProduto());

        for (Maquina m : maquinas) {
            m.ligar();
            this.budget -= m.getcustOperacao();
            if (!m.processar(produto, materiaPrima)) {
                System.out.println("Falha na máquina " + m.getNome());
                return;
            }
        }
        produtosFabricados.add(produto);
        demandaAtual.atualizarDemanda(-1);
        if (d.getQuantidadeProdutos() <= 0) {
            demandas.remove(0);
        }
    }

    public void comprarMateriaPrima(int quantidade, float precoUnitario){
        float custoTotal = quantidade * precoUnitario;
        if(budget >= custoTotal){
            budget -= custoTotal;
            materiaPrima.adicionarEstoque(quantidade);
        } else{
            System.out.println("Sem dinheiro para a compra");
        }

    }

    public String exibirBudget(){
        System.out.println("Budget atual:" + budget);
    }

    public String exibirArmazem(){
        String relatorio = "=== ESTOQUE DO ARMAZÉM ===\n";
        relatorio += "Matéria-Prima (" + materiaPrima.getNome() + "): " + materiaPrima.getQuantidade() + "\n";
        relatorio += "Produtos Fabricados: " + produtosFabricados.size() + " unidade(s)\n";

        for (Produto p : produtosFabricados) {
            relatorio += "- " + p.getNome() + " | Status: " + p.getStatus() + "\n";
        }
        return relatorio;
    }

    private int calcularCustoProducao(){
        float custoTotal = 0;
        for (Maquina m : maquinas) {
            custoTotal += m.getcustOperacao();
        }
        return custoTotal;
    }



}
