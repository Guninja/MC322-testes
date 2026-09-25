import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner entrada=new Scanner(System.in);
        String printe="\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\nESCOLHA DE CENÁRIO DA FÁBRICA\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n";
        printe+="\n1 - Ideal (orçamento bom, desgaste padrão)\n2 - Apocalíptico (orçamento baixíssimo, desgastes enormes)";
        System.out.println(printe);
        boolean escolhaAtiva=true;
        Cenario cenarioEscolhido = null;
        String cenarioAtual="";
        while(escolhaAtiva){
            while(!entrada.hasNextInt()){
                System.out.println("Tente numeros apenas!");
                entrada.next();
            }
            int opcaoCenario=entrada.nextInt();
            switch (opcaoCenario) {
                case 1:
                    cenarioEscolhido=Cenario.IDEAL;
                    cenarioAtual="Ideal";
                    escolhaAtiva=false;
                    break;
                case 2:
                    cenarioEscolhido=Cenario.APOCALIPTICO;
                    cenarioAtual="Apocalíptico";
                    escolhaAtiva=false;
                    break;
                default:
                    System.out.println("Tente apenas 1 ou 2...");
                    break;
            }
        }
        int budgetInicial = cenarioEscolhido.getBudgetInicial();
        int fatorDesgaste = cenarioEscolhido.getFatorDesgaste();
        float fatorFalha = cenarioEscolhido.getFatorFalha();
        MateriaPrima metal=new MateriaPrima("MeBA", "Metal", "m^2", 0, 5);//será que é aço ou európio??? nunca saberemos... mas ambos são caros
        GerenciadorProducao gerente=new GerenciadorProducao(budgetInicial,metal);//esse objeto sabe muito...
        MaquinaPrensaEstampagem prensa=new MaquinaPrensaEstampagem("Prensa de Estampagem", 20, fatorFalha, 1);
        MaquinaCorteLaser cnc=new MaquinaCorteLaser("CNC Corte Laser em metal", 10, fatorFalha, 2);//cnc laser é cara pra funcionar
        MaquinaVerificacao inspecao=new MaquinaVerificacao("Inspeção", 30, fatorFalha, 1);
        gerente.adicionarMaquina(prensa);
        gerente.adicionarMaquina(cnc);
        gerente.adicionarMaquina(inspecao);
        String lataB="ProdutoBlindado";
        String lataG="ProdutoGalvanizado";
        String lataCB="ProdutoChapaBruta";
        String[] listaProdutos={lataB, lataG, lataCB};
        EstrategiaProducao estrategiaPadrao = new EstrategiaOrdemChegada();
        gerente.setEstrategia(estrategiaPadrao);
        String menu;
        String erroNaoInt="\nErro! Digite apenas números inteiros";
        boolean menuAtivo=true;//esse é o estagiario, o famoso aux  :o
        while(menuAtivo){
            menu="\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\nFÁBRICA DE LATARIA DE CARRO\n+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+\n";
            menu+="\nMATÉRIA-PRIMA(MP): "+metal.getNome()+" ("+metal.getUnidade()+")\n";
            menu+="\nLATARIAS POSSÍVEIS: Blindada, Galvanizada e Chapa bruta";
            menu+="\nESTRATEGIA ATUAL: "+estrategiaPadrao.getNomeEstrategia();
            menu+="\nCENARIO ATIVO: "+cenarioAtual;
            menu+="\nSALDO ATUAL: "+gerente.exibirBudget();
            menu+="\n\n\n__________________________\nMENU PRINCIPAL\n__________________________";
            menu+="\n\n1 -> Comprar matéria-prima";
            menu+="\n\n2 -> Cadastrar demanda";
            menu+="\n\n3 -> Fabricar demanda";
            menu+="\n\n4 -> Ver estoque ou armazém";
            menu+="\n\n5 -> Alterar estratégia de produção";
            menu+="\n\n6 -> Relatório de auditoria";
            menu+="\n\n0 -> SAIR";
            System.out.print(menu + "\nESCOLHA: ");
            if(entrada.hasNextInt()){//filtro para aceitar apenas int na escolha do menu
                int opcao=entrada.nextInt();
                switch(opcao){//primeiro switch, escolha do menu  principal
                    case 1:
                        System.out.print("Digite quantas unidades (" + metal.getUnidade() + ") de "+metal.getNome()+" deseja comprar: ");
                        if(entrada.hasNextInt()){
                            int quantidadeComprando=entrada.nextInt();
                            gerente.comprarMateriaPrima(quantidadeComprando,metal.getCustoPorUnidade());
                        }else{
                            System.out.println("Compra não realizada,"+erroNaoInt);
                            entrada.next();
                        }break;
                    case 2:
                        menu="\n--- ATUALIZAÇÃO DE DEMANDA ---\n1 -> Lataria Blindada\n2 -> Lataria Galvanizada\n3 -> Lataria Chapa Bruta";
                        menu+="\n4 -> Ver fila de demandas\n0 -> Voltar\nESCOLHA: ";
                        System.out.println(menu);
                        if(!entrada.hasNextInt()){
                            System.out.println("Falha ao atualizar demanda,"+erroNaoInt);
                            break;
                        }
                        opcao=entrada.nextInt();
                        switch (opcao) {
                            case 1://Lataria Blindada selecionada
                                System.out.print("\n-CRIANDO DEMANDA DE BLINDADO-\nQuanto deseja fabricar: ");
                                if(entrada.hasNextInt()){
                                    int quantidadeDemandada=entrada.nextInt();
                                    Demanda novaDemanda=new Demanda(listaProdutos[0], quantidadeDemandada);
                                    gerente.registrarDemanda(novaDemanda);
                                }else{
                                    System.out.println("Demanda não atualizada,"+erroNaoInt);
                                    entrada.next();
                                }break;
                            case 2://Lataria Galvanizada selecionada
                                System.out.print("\n-CRIANDO DEMANDA DE GALVANIZADO-\nQuanto deseja fabricar: ");
                                if(entrada.hasNextInt()){
                                    int quantidadeDemandada=entrada.nextInt();
                                    Demanda novaDemanda=new Demanda(listaProdutos[1], quantidadeDemandada);
                                    gerente.registrarDemanda(novaDemanda);
                                }else{
                                    System.out.println("Demanda não atualizada,"+erroNaoInt);
                                    entrada.next();
                                }break;
                            case 3://Lataria Chapa bruta selecionada
                                System.out.print("\n-CRIANDO DEMANDA DE CHAPA BRUTA-\nQuanto deseja fabricar: ");
                                if(entrada.hasNextInt()){
                                    int quantidadeDemandada=entrada.nextInt();
                                    Demanda novaDemanda=new Demanda(listaProdutos[2], quantidadeDemandada);
                                    gerente.registrarDemanda(novaDemanda);
                                }else{
                                    System.out.println("Demanda não atualizada,"+erroNaoInt);
                                    entrada.next();
                                }break;
                            case 4:
                                System.out.println(gerente.exibirDemandas());
                                break;
                            case 0:
                                System.out.println("\nVoltando...");
                                break;
                            default:
                                System.out.println("Número inválido!");
                                break;
                        }
                        break;
                    case 3://Produzir segundo demanda atual
                        gerente.executarProximaProducao();
                        break;
                    case 4://escolha de estoque para ver
                        menu="\n--- CONSULTA DE ESTOQUE ---\n1 -> Ver Estoque de matéria-prima\n2 -> Ver armazém de produtos\n0 -> Voltar";
                        menu+="\nESCOLHA: ";
                        System.out.println(menu);
                        if(!entrada.hasNextInt()){
                            System.out.println("Falha ao atualizar demanda,"+erroNaoInt);
                            break;
                        }
                        opcao=entrada.nextInt();
                        switch (opcao) {
                            case 1:
                                System.out.println(gerente.exibirEstoqueMateriaPrima());
                                break;
                            case 2:
                                System.out.println(gerente.exibirArmazem());
                                break;
                            case 0:
                                System.out.println("\nVoltando...");
                                break;
                            default:
                                System.out.println("Número inválido!");
                                break;
                        }
                        break;
                    case 5://Alteração de estratégia
                        menu="\n--- ALTERAR ESTRATÉGIA DE PRODUÇÃO ---\n1 -> Ordem de chegada (FIFO)\n2 -> Maior demanda (decrescente)";
                        menu+="\n3 -> Máximo produtos (menor custo/produção)\n0 -> Voltar\nESCOLHA: ";
                        System.out.println(menu);
                        if(!entrada.hasNextInt()){
                            System.out.println("Falha ao atualizar demanda,"+erroNaoInt);
                            break;
                        }
                        opcao=entrada.nextInt();
                        switch (opcao) {
                            case 1:
                                estrategiaPadrao=new EstrategiaOrdemChegada();
                                gerente.setEstrategia(estrategiaPadrao);
                                System.out.println("Estratégia alterada para ordem de chegada!");
                                break;
                            case 2:
                                estrategiaPadrao=new EstrategiaMaiorDemanda();
                                gerente.setEstrategia(estrategiaPadrao);
                                System.out.println("Estratégia alterada para maior demanda!");
                                break;
                            case 3:
                                estrategiaPadrao=new EstrategiaMaxProdutos();
                                gerente.setEstrategia(estrategiaPadrao);
                                System.out.println("Estratégia alterada para maximo produtos!");
                                break;
                            case 0:
                                System.out.println("Voltando...");
                                break;
                            default:
                                System.out.println("Número inválido!");
                                break;
                        }
                        break;
                    case 6://Relatório de auditoria
                        System.out.println(gerente.gerarRelatorioDiagnostico());
                        break;
                    case 0:
                        System.out.println("\n-SAINDO...\nEspero ter sido útil, até mais!\n   O.O  ");
                        menuAtivo=false;
                        break;
                    default:
                        System.out.println("Tente um número válido!");
                        break;
                }
            }else{
                System.out.println(erroNaoInt);
                entrada.next();
            }
            if (menuAtivo){
                System.out.print("\nDIGITE QUALQUER COISA PARA VOLTAR AO MENU...\n");
                entrada.next();
            }
        }
    }
}