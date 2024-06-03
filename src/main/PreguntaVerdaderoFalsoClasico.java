package main;


import java.util.ArrayList;
import java.util.List;

//Asigna un punto a cada jugador que responda con la opción correcta
public class PreguntaVerdaderoFalsoClasico implements Pregunta {

    private String enunciado;

    private ArrayList<Respuesta> respuestas;



    public PreguntaVerdaderoFalsoClasico(String pregunta, ArrayList<Respuesta> respuestas) {
        enunciado = pregunta;
        this.respuestas = respuestas;

    }


    //respuestasDeVerdaderoFalsoClasico deberia poder responder lo mismo que Respuesta, porque hereda de ella
    @Override
    public void puntuar(ArrayList<Respuesta> respuestas) {
        for (Respuesta respuesta: respuestas) {
                respuesta.actualizarPuntaje(1);
        }
    }

    @Override
    public ArrayList<Respuesta> respuestasPosibles(){
        return respuestas;
    }


    public String getEnunciado() {
        return enunciado;
    }

}
