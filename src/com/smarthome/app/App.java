package com.smarthome.app;

import com.smarthome.model.BombillaInteligente;
import com.smarthome.model.CamaraSeguridad;
import com.smarthome.model.Dispositivo;
import com.smarthome.services.Escena;
import com.smarthome.services.SmartHomeService;

/**
 * SOLUCIÓN DE REFERENCIA  - SmartHome
 * 
 * Resumen de reglas:
 * 1) Estado inicial: DESCONECTADO y APAGADO.
 * 2) "No se puede": encender/apagar desconectado -> false.
 * 3) Escena "Cine": conectar -> encender -> ajustar brillo a BRILLO_CINE (20) -> imprimir.
 * 4) Revisión de cámaras: imprime "Movimiento detectado..." cuando procede.
 */
public class App {
// Comentario añadido por DanielS para prueba
    private static final String ID_BOMB_1 = "id-bomb1";
    private static final String ID_BOMB_2 = "id-bomb2";
    private static final String ID_CAM_1 = "id-camera1";

    public static void main(String[] args) {
        // 1) Crear el servicio integrador
        SmartHomeService service = new SmartHomeService();

        // 2) Crear dispositivos (2 bombillas + 1 cámara) y registrarlos en el servicio
        BombillaInteligente bombilla1 = new BombillaInteligente(ID_BOMB_1, "bombilla1");
        BombillaInteligente bombilla2 = new BombillaInteligente(ID_BOMB_2, "bombilla2");
        CamaraSeguridad camara1 = new CamaraSeguridad(ID_CAM_1, "Camara1", CamaraSeguridad.SENSIBILIDAD_MINIMA);

        service.registrarDispositivo(bombilla1);
        service.registrarDispositivo(bombilla2);
        service.registrarDispositivo(camara1);

        // 3) Caso “no se puede” exigido:
        //    Intentar encender estando DESCONECTADO debe devolver false y no cambiar el estado.
        System.out.println(bombilla1.encender());

        // 4) Conectar/encender/ajustar niveles fuera de escena (para demostrar funcionamiento)
        System.out.println(bombilla1.conectar());          // true (cambio DESCONECTADO -> CONECTADO)
        bombilla1.encender();                              // true (apagado -> encendido)
        bombilla1.ajustarNivel(BombillaInteligente.BRILLO_MAXIMO); // permitido aunque estuviera apagada, mientras esté conectada

        // 5) Crear escena "Cine" y añadir al menos una bombilla y una cámara
        Escena cine = new Escena("Cine");
        cine.addDispositivo(bombilla1);
        cine.addDispositivo(bombilla2);
        cine.addDispositivo(camara1);

        // Registrar la escena en el servicio
        service.registrarEscena(cine);

        // 6) Ejecutar escena por nombre (enunciado: Iterator obligatorio en el servicio)
        boolean ejecutada = service.ejecutarEscenaPorNombre("Cine");
        System.out.println("\nEscena Cine ejecutada: " + ejecutada + "\n");

        // 7) Forzar detección de movimiento: conectar, encender y subir sensibilidad al umbral
        camara1.conectar();
        camara1.encender();
        camara1.setSensibilidad(CamaraSeguridad.UMBRAL_MOVIMIENTO);

        // 8) Ejecutar revisión (imprime detección si procede)
        service.revisionCamaras();

        // 9) Imprimir estado final (toString() informativo exigido por el enunciado)
        System.out.println("\nEstado final de los dispositivos: ");
        for (Dispositivo d : service.getDispositivos()) {
            System.out.println(d);
        }
    }
}
