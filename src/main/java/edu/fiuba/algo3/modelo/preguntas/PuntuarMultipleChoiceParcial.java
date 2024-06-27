package edu.fiuba.algo3.modelo.preguntas;

import edu.fiuba.algo3.modelo.Anulador.*;
import edu.fiuba.algo3.modelo.GestorPuntaje;
import edu.fiuba.algo3.modelo.Penalidad.Penalidad.PenalidadClasica;
import edu.fiuba.algo3.modelo.Penalidad.Penalidad.PenalidadConPenalidad;
import edu.fiuba.algo3.modelo.Penalidad.Penalidad.TipoDePenalidad;
import edu.fiuba.algo3.modelo.Respuestas.*;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.exceptions.ExclusividadInvalida;

import java.util.ArrayList;

public class PuntuarMultipleChoiceParcial extends PuntuarConPenalidad{


    private TipoDePenalidad penalidad = new PenalidadConPenalidad();


    private void verificarActivacionAnulador(){
        if (!jugadoresQueUsaronAnulador.isEmpty()){
            this.anulador.activar();
        }
    }

    @Override
    public int puntuar(ArrayList<Respuesta> respuestas,Jugador unjugador) {
        this.verificarActivacionAnulador();
        int PuntajeObtenido = GestorPuntaje.PuntajeError();
        for (Respuesta respuestaDelJugador : respuestas) {
            PuntajeObtenido += respuestaDelJugador.actualizarPuntaje(GestorPuntaje.PuntajeAcierto(), penalidad, respuestaDelJugador);
        }
        for (Respuesta respuestaDelJugador : respuestas) {
            if (!respuestaDelJugador.EsCorrecta(respuestaDelJugador)){
                PuntajeObtenido = GestorPuntaje.PuntajeError();
            }
        }
        PuntajeObtenido = this.anulador.puntosLuegoDeEvaluacion(PuntajeObtenido,this.jugadoresQueUsaronAnulador,unjugador);

        return PuntajeObtenido;
    }
}

