package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.persistence.PersistenciaPlanta;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.strategy.EstrategiaMaiorDemanda;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para validar a persistência e recuperação em XML da planta industrial,
 * incluindo a preservação de estado, relacionamentos de composição e tratamento de arquivos inválidos.
 * 
 * @author Luis Gustavo
 */
class PersistenciaPlantaTest {

    @TempDir
    Path pastaTemporaria;

    @Test
    void salvarECarregarPreservaEstadoCompletoDaPlanta() {
        File arquivoXml = pastaTemporaria.resolve("planta_teste.xml").toFile();
        PersistenciaPlanta persistencia = new PersistenciaPlanta();

        // 1. Prepara o estado original completo da fábrica
        MateriaPrima mpOriginal = new MateriaPrima("MP-01", "Aco Carbono", "m^2", 85, 5);
        LinhaDeProducao linhaOriginal = new LinhaDeProducao("Linha Automotiva 1");

        MaquinaPrensaEstampagem prensa = new MaquinaPrensaEstampagem("Prensa Hidraulica", 20, 0.05f, 2, 1);
        prensa.restaurarEstado(64.5f, true);
        MaquinaCorteLaser laser = new MaquinaCorteLaser("Laser Fibra", 15, 0.10f, 3, 2);

        linhaOriginal.adicionarMaquina(prensa);
        linhaOriginal.adicionarMaquina(laser);

        GerenciadorProducao gerenteOriginal = new GerenciadorProducao(4200, mpOriginal, linhaOriginal);
        gerenteOriginal.setEstrategia(new EstrategiaMaiorDemanda());

        Demanda d1 = new Demanda("ProdutoBlindado", 5);
        gerenteOriginal.registrarDemanda(d1);

        Demanda d2 = new Demanda("ProdutoChapaBruta", 12);
        gerenteOriginal.registrarDemanda(d2);
        d2.ProducaoIniciada();

        ProdutoGalvanizado prodFabricado = new ProdutoGalvanizado("PROD1", "ProdutoGalvanizado");
        prodFabricado.processar();
        prodFabricado.aumentarProbabilidadeFalha(0.20f);
        gerenteOriginal.adicionarProdutoFabricado(prodFabricado);

        // 2. Salva e carrega em um novo GerenciadorProducao
        persistencia.salvar(gerenteOriginal, arquivoXml);
        assertTrue(arquivoXml.exists());

        GerenciadorProducao gerenteRecuperado = persistencia.carregar(arquivoXml);

        // 3. Verifica se todos os dados e relacionamentos foram preservados
        assertEquals(4200, gerenteRecuperado.getBudget());
        assertEquals("Vamos produzir ao Máximo!!!", gerenteRecuperado.getEstrategiaAtual().getNomeEstrategia());

        // Verifica Matéria-Prima
        MateriaPrima mpRec = gerenteRecuperado.getMateriaPrima();
        assertEquals("MP-01", mpRec.getId());
        assertEquals("Aco Carbono", mpRec.getNome());
        assertEquals(85, mpRec.getQuantidade());
        assertEquals("m^2", mpRec.getUnidade());
        assertEquals(5, mpRec.getCustoPorUnidade());

        // Verifica Linha de Produção, Máquinas e Composição (Motor, Controlador, Sensores)
        LinhaDeProducao linhaRec = gerenteRecuperado.getLinhaProducao();
        assertEquals("Linha Automotiva 1", linhaRec.getNome());
        assertEquals(2, linhaRec.getQuantidadeMaquinas());

        Maquina maq1 = linhaRec.getMaquinas().get(0);
        assertEquals("Prensa Hidraulica", maq1.getNome());
        assertEquals(64.5f, maq1.getSaude(), 0.001f);
        assertTrue(maq1.estaLigada());
        assertTrue(maq1.getMotor().isEmFuncionamento());
        assertEquals(150.0, maq1.getMotor().getPotencia(), 0.001);
        assertEquals("v2.4", maq1.getControlador().getVersaoFirmware());
        assertEquals(2, maq1.getSensores().size());

        // Verifica Demandas e seus StatusDemanda
        assertEquals(2, gerenteRecuperado.getDemandas().size());
        assertEquals(StatusDemanda.PENDENTE, gerenteRecuperado.getDemandas().get(0).getStatus());
        assertEquals(5, gerenteRecuperado.getDemandas().get(0).getQuantidadeProdutos());
        assertEquals(StatusDemanda.EM_PRODUCAO, gerenteRecuperado.getDemandas().get(1).getStatus());
        assertEquals(12, gerenteRecuperado.getDemandas().get(1).getQuantidadeProdutos());

        // Verifica Produtos no Armazém
        assertEquals(1, gerenteRecuperado.getProdutosFabricados().size());
        Produto prodRec = gerenteRecuperado.getProdutosFabricados().get(0);
        assertEquals("PROD1", prodRec.getId());
        assertEquals(StatusProduto.PRODUZIDO, prodRec.getStatus());
        assertTrue(prodRec.precisaManutencao());
    }

    @Test
    void atualizarArquivoXmlRefleteAlteracoesDeEstado() {
        File arquivoXml = pastaTemporaria.resolve("atualizacao.xml").toFile();
        PersistenciaPlanta persistencia = new PersistenciaPlanta();

        MateriaPrima mp = new MateriaPrima("MP-01", "Metal", "m^2", 10, 5);
        LinhaDeProducao linha = new LinhaDeProducao("Linha 1");
        linha.adicionarMaquina(new MaquinaPrensaEstampagem("Prensa", 20, 0.05f, 1, 1));
        GerenciadorProducao gerente = new GerenciadorProducao(1000, mp, linha);

        persistencia.salvar(gerente, arquivoXml);

        // Realiza alterações relevantes e salva novamente
        gerente.comprarMateriaPrima(20, 5);
        persistencia.salvar(gerente, arquivoXml);

        GerenciadorProducao recarregado = persistencia.carregar(arquivoXml);
        assertEquals(900, recarregado.getBudget());
        assertEquals(30, recarregado.getMateriaPrima().getQuantidade());
    }

    @Test
    void carregarArquivoInexistenteLancaPersistenciaException() {
        File inexistente = pastaTemporaria.resolve("nao_existe.xml").toFile();
        PersistenciaPlanta persistencia = new PersistenciaPlanta();

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(inexistente));
    }

    @Test
    void carregarArquivoVazioLancaPersistenciaException() throws IOException {
        File vazio = pastaTemporaria.resolve("vazio.xml").toFile();
        assertTrue(vazio.createNewFile());
        PersistenciaPlanta persistencia = new PersistenciaPlanta();

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(vazio));
    }

    @Test
    void carregarArquivoCorrompidoLancaPersistenciaException() throws IOException {
        File corrompido = pastaTemporaria.resolve("corrompido.xml").toFile();
        try (FileWriter writer = new FileWriter(corrompido)) {
            writer.write("<?xml version=\"1.0\"?><planta><materiaPrima><tagSemFechar></planta>");
        }
        PersistenciaPlanta persistencia = new PersistenciaPlanta();

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(corrompido));
    }

    @Test
    void carregarDadosIncompativeisComDominioLancaPersistenciaException() throws IOException {
        File invalido = pastaTemporaria.resolve("invalido.xml").toFile();
        try (FileWriter writer = new FileWriter(invalido)) {
            // Budget negativo (-500) viola regra de domínio
            writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
                    + "<planta budget=\"-500\" estrategia=\"EstrategiaOrdemChegada\">"
                    + "<materiaPrima id=\"MP1\"><nome>Metal</nome><quantidade>10</quantidade>"
                    + "<unidade>m^2</unidade><custoPorUnidade>5</custoPorUnidade></materiaPrima>"
                    + "<linhaDeProducao nome=\"Linha\"><maquinas></maquinas></linhaDeProducao>"
                    + "</planta>");
        }
        PersistenciaPlanta persistencia = new PersistenciaPlanta();

        assertThrows(PersistenciaException.class, () -> persistencia.carregar(invalido));
    }
}