package br.unicamp.ic.mc322.persistence;

import br.unicamp.ic.mc322.exception.DemandaInvalidaException;
import br.unicamp.ic.mc322.exception.PersistenciaException;
import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.service.GerenciadorProducao;
import br.unicamp.ic.mc322.strategy.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * Classe responsável por persistir e recuperar o estado completo da planta industrial em arquivos XML.
 * Garante a separação entre as regras de negócio do domínio e a manipulação de arquivos,
 * validando a integridade estrutural e semântica dos dados carregados.
 * 
 * @author Luis Gustavo
 */
public class PersistenciaPlanta {

    private static final String CAMINHO_PADRAO = "estado_planta.xml";

    /**
     * Construtor padrão da classe de persistência.
     */
    public PersistenciaPlanta() {
    }

    /**
     * Salva o estado atual do gerenciador da planta no arquivo XML padrão.
     * 
     * @param gerente O GerenciadorProducao contendo todo o estado atual da fábrica.
     * @throws PersistenciaException Se ocorrer falha na geração ou escrita do arquivo XML.
     */
    public void salvar(GerenciadorProducao gerente) {
        salvar(gerente, new File(CAMINHO_PADRAO));
    }

    /**
     * Salva o estado atual do gerenciador da planta em um arquivo XML específico.
     * 
     * @param gerente O GerenciadorProducao contendo todo o estado atual da fábrica.
     * @param arquivo O arquivo de destino onde o XML será gravado.
     * @throws PersistenciaException Se o gerenciador/arquivo for nulo ou se houver erro de escrita.
     */
    public void salvar(GerenciadorProducao gerente, File arquivo) {
        if (gerente == null) {
            throw new PersistenciaException("Erro ao salvar: o gerenciador da planta não pode ser nulo.");
        }
        if (arquivo == null) {
            throw new PersistenciaException("Erro ao salvar: o arquivo de destino não pode ser nulo.");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            // Elemento raiz <planta>
            Element raiz = doc.createElement("planta");
            raiz.setAttribute("budget", String.valueOf(gerente.getBudget()));
            String nomeClasseEstrategia = gerente.getEstrategiaAtual() != null
                    ? gerente.getEstrategiaAtual().getClass().getSimpleName()
                    : "EstrategiaOrdemChegada";
            raiz.setAttribute("estrategia", nomeClasseEstrategia);
            doc.appendChild(raiz);

            // 1. Matéria-Prima
            MateriaPrima mp = gerente.getMateriaPrima();
            if (mp != null) {
                Element mpElem = doc.createElement("materiaPrima");
                mpElem.setAttribute("id", mp.getId());
                adicionarElementoTexto(doc, mpElem, "nome", mp.getNome());
                adicionarElementoTexto(doc, mpElem, "quantidade", String.valueOf(mp.getQuantidade()));
                adicionarElementoTexto(doc, mpElem, "unidade", mp.getUnidade());
                adicionarElementoTexto(doc, mpElem, "custoPorUnidade", String.valueOf(mp.getCustoPorUnidade()));
                raiz.appendChild(mpElem);
            }

            // 2. Linha de Produção, Máquinas e Subcomponentes (Composição)
            LinhaDeProducao linha = gerente.getLinhaProducao();
            Element linhaElem = doc.createElement("linhaDeProducao");
            linhaElem.setAttribute("nome", linha.getNome());

            Element maquinasElem = doc.createElement("maquinas");
            for (Maquina m : linha.getMaquinas()) {
                Element maqElem = doc.createElement("maquina");
                maqElem.setAttribute("classe", m.getClass().getSimpleName());
                maqElem.setAttribute("nome", m.getNome());
                adicionarElementoTexto(doc, maqElem, "capacidadeMaxima", String.valueOf(m.getCapacidadeMaxima()));
                adicionarElementoTexto(doc, maqElem, "probabilidadeFalha", String.valueOf(m.getProbabilidadeFalha()));
                adicionarElementoTexto(doc, maqElem, "custoOperacao", String.valueOf(m.getCustoOperacao()));
                adicionarElementoTexto(doc, maqElem, "fatorDesgaste", String.valueOf(m.getFatorDesgaste()));
                adicionarElementoTexto(doc, maqElem, "saude", String.valueOf(m.getSaude()));
                adicionarElementoTexto(doc, maqElem, "ligada", String.valueOf(m.estaLigada()));

                // Subcomponente 1:1 - Motor
                Motor motor = m.getMotor();
                Element motorElem = doc.createElement("motor");
                motorElem.setAttribute("potencia", String.valueOf(motor.getPotencia()));
                motorElem.setAttribute("emFuncionamento", String.valueOf(motor.isEmFuncionamento()));
                maqElem.appendChild(motorElem);

                // Subcomponente 1:1 - Controlador
                Controlador ctrl = m.getControlador();
                Element ctrlElem = doc.createElement("controlador");
                ctrlElem.setAttribute("firmware", ctrl.getVersaoFirmware());
                ctrlElem.setAttribute("sistemaOk", String.valueOf(ctrl.isSistemaOk()));
                maqElem.appendChild(ctrlElem);

                // Subcomponente 1:N - Sensores
                Element sensoresElem = doc.createElement("sensores");
                for (Sensor s : m.getSensores()) {
                    Element sensorElem = doc.createElement("sensor");
                    sensorElem.setAttribute("tipo", s.getTipo());
                    sensorElem.setAttribute("operacional", String.valueOf(s.isOperacional()));
                    sensoresElem.appendChild(sensorElem);
                }
                maqElem.appendChild(sensoresElem);

                maquinasElem.appendChild(maqElem);
            }
            linhaElem.appendChild(maquinasElem);
            raiz.appendChild(linhaElem);

            // 3. Demandas e StatusDemanda
            Element demandasElem = doc.createElement("demandas");
            for (Demanda d : gerente.getDemandas()) {
                Element demElem = doc.createElement("demanda");
                adicionarElementoTexto(doc, demElem, "tipoProduto", d.getTipoProduto());
                adicionarElementoTexto(doc, demElem, "quantidadeProdutos", String.valueOf(d.getQuantidadeProdutos()));
                adicionarElementoTexto(doc, demElem, "status", d.getStatus().name());
                adicionarElementoTexto(doc, demElem, "custoProducaoUnitario", String.valueOf(d.getCustoProducaoUnitario()));
                demandasElem.appendChild(demElem);
            }
            raiz.appendChild(demandasElem);

            // 4. Produtos Fabricados no Armazém
            Element produtosElem = doc.createElement("produtosFabricados");
            for (Produto p : gerente.getProdutosFabricados()) {
                Element prodElem = doc.createElement("produto");
                prodElem.setAttribute("classe", p.getClass().getSimpleName());
                prodElem.setAttribute("id", p.getId());
                adicionarElementoTexto(doc, prodElem, "nome", p.getNome());
                adicionarElementoTexto(doc, prodElem, "status", p.getStatus().name());
                adicionarElementoTexto(doc, prodElem, "probabilidadeFalhaAcumulada", String.valueOf(p.getProbabilidadeFalha()));
                produtosElem.appendChild(prodElem);
            }
            raiz.appendChild(produtosElem);

            // Gravação formatada em arquivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(arquivo);
            transformer.transform(source, result);

        } catch (ParserConfigurationException | TransformerException | RuntimeException e) {
            throw new PersistenciaException("Falha ao escrever o arquivo XML da planta: " + e.getMessage(), e);
        }
    }

    /**
     * Carrega e reconstrói o estado da planta a partir do arquivo XML padrão.
     * 
     * @return Um novo objeto GerenciadorProducao com todo o estado restaurado e validado.
     * @throws PersistenciaException Se o arquivo não existir, estiver corrompido ou contiver dados inválidos.
     */
    public GerenciadorProducao carregar() {
        return carregar(new File(CAMINHO_PADRAO));
    }

    /**
     * Carrega, valida e reconstrói o estado da planta a partir de um arquivo XML específico.
     * 
     * @param arquivo O arquivo XML de origem.
     * @return Um novo objeto GerenciadorProducao com todo o estado restaurado e validado.
     * @throws PersistenciaException Se o arquivo for inexistente, vazio, malformado ou violar regras de domínio.
     */
    public GerenciadorProducao carregar(File arquivo) {
        if (arquivo == null || !arquivo.exists() || !arquivo.isFile()) {
            throw new PersistenciaException("Arquivo de persistência não encontrado: "
                    + (arquivo != null ? arquivo.getPath() : "null"));
        }
        if (arquivo.length() == 0) {
            throw new PersistenciaException("O arquivo de persistência está vazio.");
        }

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(arquivo);
            doc.getDocumentElement().normalize();

            Element raiz = doc.getDocumentElement();
            if (!"planta".equals(raiz.getNodeName())) {
                throw new PersistenciaException("Estrutura XML inválida: o elemento raiz deve ser <planta>.");
            }

            // Valida e lê budget e estratégia
            String budgetStr = raiz.getAttribute("budget");
            if (budgetStr == null || budgetStr.trim().isEmpty()) {
                throw new PersistenciaException("Atributo obrigatório 'budget' ausente em <planta>.");
            }
            int budget = Integer.parseInt(budgetStr.trim());
            if (budget < 0) {
                throw new PersistenciaException("Dados inválidos no XML: o budget não pode ser negativo.");
            }

            String nomeEstrategia = raiz.getAttribute("estrategia");
            EstrategiaProducao estrategia = instanciarEstrategia(nomeEstrategia);

            // 1. Reconstrói MateriaPrima
            NodeList mpNodes = raiz.getElementsByTagName("materiaPrima");
            if (mpNodes.getLength() == 0) {
                throw new PersistenciaException("Estrutura XML inválida: elemento <materiaPrima> ausente.");
            }
            Element mpElem = (Element) mpNodes.item(0);
            String mpId = mpElem.getAttribute("id");
            String mpNome = obterTextoObrigatorio(mpElem, "nome");
            int mpQuantidade = Integer.parseInt(obterTextoObrigatorio(mpElem, "quantidade"));
            String mpUnidade = obterTextoObrigatorio(mpElem, "unidade");
            int mpCusto = Integer.parseInt(obterTextoObrigatorio(mpElem, "custoPorUnidade"));

            if (mpId == null || mpId.trim().isEmpty() || mpQuantidade < 0 || mpCusto <= 0) {
                throw new PersistenciaException("Dados de matéria-prima inválidos no arquivo XML.");
            }
            MateriaPrima materiaPrima = new MateriaPrima(mpId, mpNome, mpUnidade, mpQuantidade, mpCusto);

            // 2. Reconstrói LinhaDeProducao, Máquinas e Subcomponentes
            NodeList linhaNodes = raiz.getElementsByTagName("linhaDeProducao");
            if (linhaNodes.getLength() == 0) {
                throw new PersistenciaException("Estrutura XML inválida: elemento <linhaDeProducao> ausente.");
            }
            Element linhaElem = (Element) linhaNodes.item(0);
            String nomeLinha = linhaElem.getAttribute("nome");
            LinhaDeProducao linha = new LinhaDeProducao(nomeLinha);

            NodeList maqNodes = linhaElem.getElementsByTagName("maquina");
            for (int i = 0; i < maqNodes.getLength(); i++) {
                Element maqElem = (Element) maqNodes.item(i);
                Maquina maquina = reconstruirMaquina(maqElem);
                linha.adicionarMaquina(maquina);
            }

            // Instancia o GerenciadorProducao
            GerenciadorProducao gerente = new GerenciadorProducao(budget, materiaPrima, linha);
            gerente.setEstrategia(estrategia);

            // 3. Reconstrói Demandas e seus StatusDemanda
            NodeList demNodes = raiz.getElementsByTagName("demanda");
            for (int i = 0; i < demNodes.getLength(); i++) {
                Element demElem = (Element) demNodes.item(i);
                String tipoProd = obterTextoObrigatorio(demElem, "tipoProduto");
                int qtdProd = Integer.parseInt(obterTextoObrigatorio(demElem, "quantidadeProdutos"));
                StatusDemanda statusDem = StatusDemanda.valueOf(obterTextoObrigatorio(demElem, "status"));
                int custoUnit = Integer.parseInt(obterTextoObrigatorio(demElem, "custoProducaoUnitario"));

                Demanda demanda = new Demanda(tipoProd, qtdProd);
                gerente.registrarDemanda(demanda);
                demanda.setCustoProducaoUnitario(custoUnit);
                demanda.setStatus(statusDem);
            }

            // 4. Reconstrói Produtos no Armazém
            NodeList prodNodes = raiz.getElementsByTagName("produto");
            for (int i = 0; i < prodNodes.getLength(); i++) {
                Element prodElem = (Element) prodNodes.item(i);
                Produto prod = reconstruirProduto(prodElem);
                gerente.adicionarProdutoFabricado(prod);
            }

            return gerente;

        } catch (PersistenciaException e) {
            throw e;
        } catch (IllegalArgumentException | DemandaInvalidaException e) {
            throw new PersistenciaException("Dados do XML violam regras de domínio da fábrica: " + e.getMessage(), e);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            throw new PersistenciaException("Arquivo XML malformado, corrompido ou ilegível: " + e.getMessage(), e);
        }
    }

    /**
     * Método auxiliar para reconstruir uma máquina e seus componentes internos a partir do elemento XML.
     */
    private Maquina reconstruirMaquina(Element maqElem) {
        String classe = maqElem.getAttribute("classe");
        String nome = maqElem.getAttribute("nome");
        int capMax = Integer.parseInt(obterTextoObrigatorio(maqElem, "capacidadeMaxima"));
        float probFalha = Float.parseFloat(obterTextoObrigatorio(maqElem, "probabilidadeFalha"));
        int custoOp = Integer.parseInt(obterTextoObrigatorio(maqElem, "custoOperacao"));
        int fatorDesgaste = Integer.parseInt(obterTextoObrigatorio(maqElem, "fatorDesgaste"));
        float saude = Float.parseFloat(obterTextoObrigatorio(maqElem, "saude"));
        boolean ligada = Boolean.parseBoolean(obterTextoObrigatorio(maqElem, "ligada"));

        Maquina maquina;
        switch (classe) {
            case "MaquinaPrensaEstampagem":
                maquina = new MaquinaPrensaEstampagem(nome, capMax, probFalha, custoOp, fatorDesgaste);
                break;
            case "MaquinaCorteLaser":
                maquina = new MaquinaCorteLaser(nome, capMax, probFalha, custoOp, fatorDesgaste);
                break;
            case "MaquinaVerificacao":
                maquina = new MaquinaVerificacao(nome, capMax, probFalha, custoOp, fatorDesgaste);
                break;
            default:
                throw new PersistenciaException("Tipo de máquina desconhecido no XML: " + classe);
        }

        // Reconstrói Motor
        NodeList motorNodes = maqElem.getElementsByTagName("motor");
        if (motorNodes.getLength() == 0) {
            throw new PersistenciaException("Componente obrigatório <motor> ausente na máquina: " + nome);
        }
        Element motorElem = (Element) motorNodes.item(0);
        double potencia = Double.parseDouble(motorElem.getAttribute("potencia"));
        boolean emFunc = Boolean.parseBoolean(motorElem.getAttribute("emFuncionamento"));
        Motor motor = new Motor(potencia);
        if (emFunc) {
            motor.ligar();
        }

        // Reconstrói Controlador
        NodeList ctrlNodes = maqElem.getElementsByTagName("controlador");
        if (ctrlNodes.getLength() == 0) {
            throw new PersistenciaException("Componente obrigatório <controlador> ausente na máquina: " + nome);
        }
        Element ctrlElem = (Element) ctrlNodes.item(0);
        String firmware = ctrlElem.getAttribute("firmware");
        Controlador controlador = new Controlador(firmware);

        // Reconstrói Sensores
        NodeList sensorNodes = maqElem.getElementsByTagName("sensor");
        if (sensorNodes.getLength() == 0) {
            throw new PersistenciaException("A máquina deve possuir ao menos um <sensor> no XML: " + nome);
        }
        List<Sensor> sensores = new ArrayList<>();
        for (int i = 0; i < sensorNodes.getLength(); i++) {
            Element sElem = (Element) sensorNodes.item(i);
            String tipoSensor = sElem.getAttribute("tipo");
            sensores.add(new Sensor(tipoSensor));
        }

        maquina.restaurarComponentes(motor, controlador, sensores);
        maquina.restaurarEstado(saude, ligada);
        return maquina;
    }

    /**
     * Método auxiliar para reconstruir um produto fabricado a partir do elemento XML.
     */
    private Produto reconstruirProduto(Element prodElem) {
        String classe = prodElem.getAttribute("classe");
        String id = prodElem.getAttribute("id");
        String nome = obterTextoObrigatorio(prodElem, "nome");
        StatusProduto status = StatusProduto.valueOf(obterTextoObrigatorio(prodElem, "status"));
        float probAcumulada = Float.parseFloat(obterTextoObrigatorio(prodElem, "probabilidadeFalhaAcumulada"));

        if (id == null || id.trim().isEmpty() || nome == null || nome.trim().isEmpty() || probAcumulada < 0.0f) {
            throw new PersistenciaException("Dados de produto inválidos no arquivo XML.");
        }

        Produto produto;
        switch (classe) {
            case "ProdutoBlindado":
                produto = new ProdutoBlindado(id, nome);
                break;
            case "ProdutoGalvanizado":
                produto = new ProdutoGalvanizado(id, nome);
                break;
            case "ProdutoChapaBruta":
                produto = new ProdutoChapaBruta(id, nome);
                break;
            default:
                throw new PersistenciaException("Tipo de produto desconhecido no XML: " + classe);
        }

        produto.setStatus(status);
        float diferencaRisco = probAcumulada - produto.getProbabilidadeFalha();
        if (diferencaRisco > 0.0001f) {
            produto.aumentarProbabilidadeFalha(diferencaRisco);
        }
        return produto;
    }

    /**
     * Instancia a estratégia de produção correspondente ao nome salvo no XML.
     */
    private EstrategiaProducao instanciarEstrategia(String nomeEstrategia) {
        if (nomeEstrategia == null || nomeEstrategia.trim().isEmpty()) {
            return new EstrategiaOrdemChegada();
        }
        switch (nomeEstrategia.trim()) {
            case "EstrategiaOrdemChegada":
                return new EstrategiaOrdemChegada();
            case "EstrategiaMaiorDemanda":
                return new EstrategiaMaiorDemanda();
            case "EstrategiaMaxProdutos":
                return new EstrategiaMaxProdutos();
            default:
                throw new PersistenciaException("Estratégia de produção desconhecida no XML: " + nomeEstrategia);
        }
    }

    /**
     * Cria e anexa uma tag XML filha contendo texto simples.
     */
    private void adicionarElementoTexto(Document doc, Element pai, String nomeTag, String valor) {
        Element elem = doc.createElement(nomeTag);
        elem.setTextContent(valor);
        pai.appendChild(elem);
    }

    /**
     * Extrai o texto de uma tag obrigatória ou lança PersistenciaException caso esteja ausente/vazia.
     */
    private String obterTextoObrigatorio(Element pai, String nomeTag) {
        NodeList nodes = pai.getElementsByTagName(nomeTag);
        if (nodes.getLength() == 0 || nodes.item(0).getTextContent() == null) {
            throw new PersistenciaException("Elemento obrigatório <" + nomeTag + "> ausente no XML.");
        }
        String texto = nodes.item(0).getTextContent().trim();
        if (texto.isEmpty()) {
            throw new PersistenciaException("Elemento <" + nomeTag + "> não pode estar vazio no XML.");
        }
        return texto;
    }
}