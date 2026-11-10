package br.unicamp.ic.mc322;

import br.unicamp.ic.mc322.model.*;
import br.unicamp.ic.mc322.exception.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class MaquinaTeste {

    @Test
    @DisplayName("Deve instanciar Motor e Controlador com valores válidos")
    void testeCriacaoComponentes() {
        Motor motor = new Motor(150.0);
        Controlador ctrl = new Controlador("v1.0");

        assertNotNull(motor);
        assertNotNull(ctrl);
        assertEquals(150.0, motor.getPotencia(), 0.01);
        assertEquals("v1.0", ctrl.getVersaoFirmware());
    }

    @Test
    @DisplayName("Deve validar a criação e tipo do Sensor")
    void testeComposicaoSensores() {
        Sensor sensorTemp = new Sensor("Temperatura");

        assertNotNull(sensorTemp);
        assertEquals("Temperatura", sensorTemp.getTipo());
    }

    @Test
    @DisplayName("Deve lançar exceção ao criar Sensor com tipo nulo ou vazio")
    void testeSensorTipoInvalido() {
        assertThrows(IllegalArgumentException.class, () -> new Sensor(""));
        assertThrows(IllegalArgumentException.class, () -> new Sensor(null));
    }

    @Test
    @DisplayName("Deve lançar exceção ao tentar criar Motor com potência inválida")
    void testeMotorPotenciaInvalida() {
        assertThrows(IllegalArgumentException.class, () -> new Motor(-50.0));
    }
}