package edu.fiuba.algo3.modelo;

import com.google.gson.Gson;
import edu.fiuba.algo3.modelo.DTO.PreguntaDTO;
import edu.fiuba.algo3.modelo.DTO.ProcesarDTO;
import edu.fiuba.algo3.modelo.exceptions.YaJugaronTodosLosJugadores;
import edu.fiuba.algo3.modelo.preguntas.Pregunta;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Random;


public class Modelo {
    private ArrayList<Jugador> jugadores;
    private ArrayList<Pregunta> preguntas;
    private Pregunta preguntaActual;
    private Jugada jugadaActual;

    public Modelo() {
        this.jugadores = new ArrayList<>();
        this.procesarPreguntas();
        this.jugadaActual = iniciarJugada();
    }

    public Jugada iniciarJugada(){
        return new Jugada(jugadores, preguntaActual);
    }

    public void agregarJugador(String nombreJugador) {
        jugadores.add(new Jugador(nombreJugador));
    }

    private void procesarPreguntas(){
        preguntas = new ArrayList<>();

        Gson gson = new Gson();


        try (Reader readerPregunta = new InputStreamReader(maain.class.getResourceAsStream("/preguntas.json"))) {

            PreguntaDTO[] listaPreguntas = gson.fromJson(readerPregunta, PreguntaDTO[].class);

            ProcesarDTO procesador = new ProcesarDTO();

            this.preguntas = procesador.procesarPreguntas(listaPreguntas);

            this.BuscarPregunta();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void BuscarPregunta() {
        Random random = new Random();

        int indiceRandom = random.nextInt(preguntas.size());

        preguntaActual = preguntas.get(indiceRandom);
    }

    public Pregunta ConseguirPregunta(){
        Pregunta pregunta1 = preguntaActual;
        this.BuscarPregunta();
        return pregunta1;
    }

    public Jugador conseguirJugador(){
        return jugadaActual.conseguirJugador();
    }

    public ArrayList<String> ConseguirTodosLosJugadores(){
        ArrayList<String> jugadoresADevolver = new ArrayList<>();
        for (Jugador jugador : this.jugadores) {
            jugadoresADevolver.add(jugador.obtenerNombre());
        }
        return jugadoresADevolver;
    }

    public ArrayList<Integer> ConseguirTodosLosPuntajes(){
        ArrayList<Integer> puntajesADevolver = new ArrayList<>();
        for (Jugador jugador : this.jugadores) {
            puntajesADevolver.add(jugador.obtenerPuntos());
        }
        return puntajesADevolver;
    }

    public Jugador SiguienteJugador() throws YaJugaronTodosLosJugadores {
        return jugadaActual.siguienteJugador();
    }
}
