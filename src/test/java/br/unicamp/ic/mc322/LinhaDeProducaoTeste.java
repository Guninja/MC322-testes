package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class LinhaDeProducaoTeste {

    @Test
    @DisplayName("Deve criar linha de produção com nome válido")
    void testeCriacaoLinha() {
        LinhaDeProducao linha = new LinhaDeProducao("Linha Principal");

        assertNotNull(linha);
        assertEquals("Linha Principal", linha.getNome());
        assertNotNull(linha.getMaquinas());
    }

    @Test
    @DisplayName("Deve garantir cópia defensiva da lista de máquinas")
    void testeCopiaDefensivaMaquinas() {
        LinhaDeProducao linha = new LinhaDeProducao("Linha Protegida");

        assertThrows(UnsupportedOperationException.class, () -> {
            linha.getMaquinas().clear();
        });
    }
}