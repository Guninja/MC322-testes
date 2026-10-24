import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Classe agrupadora que representa uma linha de produção na fábrica de lataria.
 * É responsável por organizar e orquestrar um conjunto de máquinas em sequência
 * e propagando operações de produção e auditoria.
 * 
 * @author Luis Gustavo
 */
public class LinhaDeProducao implements Auditavel {
    private String nome;
    private List<Maquina> maquinas;

    /**
     * Construtor que inicializa a linha de produção com um nome identificador e uma lista vazia de máquinas.
     * 
     * @param nome Nome descritivo da linha de produção.
     * @throws IllegalArgumentException Se o nome fornecido for nulo ou vazio.
     */
    public LinhaDeProducao(String nome){
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome da linha de produção não pode ser nulo ou vazio.");
        }
        this.nome=nome;
        this.maquinas=new ArrayList<>();
    }

    /**
     * Adiciona uma máquina à linha de produção.
     * 
     * @param maquina O equipamento industrial a ser integrado na linha.
     * @throws IllegalArgumentException Se a máquina informada for nula.
     */
    public void adicionarMaquina(Maquina maquina){
        if (maquina == null) {
            throw new IllegalArgumentException("A máquina adicionada na linha de produção não pode ser nula.");
        }
        this.maquinas.add(maquina);
    }

    /**
     * Calcula a soma dos custos operacionais de todas as máquinas alocadas nesta linha.
     * 
     * @return Valor inteiro representando o custo total de operação de um ciclo da linha.
     */
    public int calcularCustoOperacaoTotal(){
        int custo=0;
        for (Maquina maquinario:maquinas){
            custo+=maquinario.getCustoOperacao();
        } return custo;
    }

    /**
     * Executa um ciclo completo de fabricação passando o produto sequencialmente por todas as máquinas da linha.
     * 
     * @param produto A peça de lataria que será processada pelas máquinas.
     * @param materiaPrima O estoque de matéria-prima que será consumido durante a operação.
     * @return true se o produto passou por todas as máquinas com sucesso, false se ocorreu falha em alguma etapa.
     * @throws IllegalArgumentException Se o produto ou a matéria-prima forem nulos.
     * @throws IllegalStateException Se a linha de produção não possuir nenhuma máquina cadastrada.
     */
    public boolean executarCiclo(Produto produto, MateriaPrima materiaPrima){
        if (produto == null || materiaPrima == null) {
            throw new IllegalArgumentException("Produto e matéria-prima não podem ser nulos no ciclo.");
        }
        if (maquinas.isEmpty()) {
            throw new IllegalStateException("Erro: A linha de produção deve possuir ao menos uma máquina para operar.");
        }
        for (Maquina m:maquinas){
            m.ligar();
            if(!m.processar(produto, materiaPrima)){
                System.out.println("Falha ao processar produto ("+produto.getNome()+") na máquina ("+m.getNome()+")");
                return false;
            }
        } return true;
    }

    /**
     * Realiza a manutenção completa, restaurando a saúde de todas as máquinas pertencentes à linha.
     */
    public void repararTodasMaquinas(){
        for (Maquina m:maquinas){
            m.reparar();
        }
    }

    /**
     * Obtém uma visão somente leitura da lista de máquinas da linha, protegendo a coleção contra modificações externas.
     * 
     * @return Uma lista imutável contendo as máquinas da linha de produção.
     */
    public List<Maquina> getMaquinas(){
        return Collections.unmodifiableList(maquinas);
    }
    
    /**
     * Obtém o número total de máquinas atualmente alocadas nesta linha de produção.
     * 
     * @return Quantidade de máquinas na linha.
     */
    public int getQuantidadeMaquinas(){
        return maquinas.size();
    }

    /**
     * Recupera o nome identificador da linha de produção.
     * 
     * @return Uma String com o nome da linha.
     */
    public String getNome(){
        return nome;
    }

    /**
     * Verifica se alguma máquina da linha de produção atingiu estado crítico e precisa de manutenção.
     * 
     * @return true se ao menos uma máquina precisar de manutenção, false caso todas estejam operacionais.
     */
    @Override
    public boolean precisaManutencao(){
        for (Maquina m:maquinas){
            if (m.precisaManutencao()==true){
                return true;
            }
        } return false;
    }

    /**
     * Gera o relatório consolidado de diagnóstico da linha de produção e de todas as suas máquinas em cascata.
     * 
     * @return Uma String formatada com os dados de auditoria da linha e de seus equipamentos.
     */
    @Override
    public String gerarRelatorioDiagnostico(){
        String texto="\n====  Linha de Produção  ====\nnome:"+nome+"\nMáquinas:";
        for (Maquina m:maquinas){
            texto+=m.gerarRelatorioDiagnostico();
        } return texto+"\n";
    }
}