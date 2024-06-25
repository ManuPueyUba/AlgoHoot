package edu.fiuba.algo3.Vista;

import edu.fiuba.algo3.Controlador.ControladorMostrarPregunta;
import edu.fiuba.algo3.modelo.Jugador;
import edu.fiuba.algo3.modelo.Modelo;
import edu.fiuba.algo3.modelo.Respuestas.Respuesta;
import edu.fiuba.algo3.modelo.Respuestas.RespuestaAVerificar;
import edu.fiuba.algo3.modelo.exceptions.AnuladorSeUsaMasDeUnaVez;
import edu.fiuba.algo3.modelo.exceptions.ModificadorSeUsaMasDeUnaVezException;
import edu.fiuba.algo3.modelo.preguntas.Pregunta;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import static edu.fiuba.algo3.modelo.Juego.ANCHO_PANTALLA;
import static edu.fiuba.algo3.modelo.Juego.LARGO_PANTALLA;

public class VentanaPregunta implements Ventana, Observer {
    private final Scene escenaPregunta;
    private final Button mostrarLeaderBoardBoton;
    private  Label labelEnunciado;
    ArrayList<RadioButton> BotonesDeRespuestas = new ArrayList<>();
    private Pregunta preguntaActual;
    private Label playerLabel;


    public VentanaPregunta(Modelo modelo){


        mostrarLeaderBoardBoton = new Button("Avanzar");
        mostrarLeaderBoardBoton.setStyle("-fx-background-color: #f16363; -fx-background-radius: 5px; -fx-padding: 8px;");

        playerLabel = new Label("Jugador Actual: " + modelo.conseguirJugador().obtenerNombre());
        Button botonConfirmar = new Button("Confirmar");
        HBox boxJugador =  new HBox(playerLabel, botonConfirmar);


        VBox cajaDeRespuestas = this.ArmarBoxRespuestas(boxJugador, modelo);
        VBox cajaDeBonificadores = this.ArmarBoxModificadores(modelo);

        preguntaActual = modelo.ConseguirPregunta();

        labelEnunciado = new Label();
        labelEnunciado.setText(preguntaActual.getEnunciado());

        Label labelTipoDePregunta = new Label("[Tipo de Pregunta]");

        ControladorMostrarPregunta controlador = new ControladorMostrarPregunta(modelo, this, botonConfirmar, mostrarLeaderBoardBoton);
        controlador.initialize();

        VBox cajaPregunta = new VBox(10, labelEnunciado, labelTipoDePregunta, cajaDeRespuestas);


        BorderPane mainPane = new BorderPane();
        mainPane.setCenter(cajaPregunta);
        mainPane.setRight(cajaDeBonificadores);
        mainPane.setBottom(mostrarLeaderBoardBoton);

        escenaPregunta = new Scene(mainPane, ANCHO_PANTALLA, LARGO_PANTALLA);
    }


    public void inicializarVentana(Stage stage){
        stage.setScene(escenaPregunta);
    }


    private VBox ArmarBoxModificadores(Modelo modelo){
        Button botonMultiplicadorPorDos = new Button("Multiplicador X2");
        botonMultiplicadorPorDos.setOnAction(event -> {
            Jugador unJugador = modelo.conseguirJugador();
            try {
                unJugador.activarDuplicadorDePuntaje();
            } catch (ModificadorSeUsaMasDeUnaVezException ex) {
                throw new RuntimeException(ex);
            }

        });
        Button botonMultiplicadorPorTres = new Button("Multiplicador X3");
        botonMultiplicadorPorTres.setOnAction(event -> {
            Jugador unJugador = modelo.conseguirJugador();
            try {
                unJugador.activarDuplicadorDePuntaje();
            } catch (ModificadorSeUsaMasDeUnaVezException ex) {
                throw new RuntimeException(ex);
            }

        });
        Button botonExclusividad = new Button("Exclusividad");

        Button botonAnulador = new Button("Anulador");
        botonAnulador.setOnAction(event -> {
            Jugador unJugador = modelo.conseguirJugador();
            try {
                unJugador.activarAnuladorDePuntaje(modelo.ConseguirPregunta());
            } catch (AnuladorSeUsaMasDeUnaVez ex) {
                throw new RuntimeException(ex);
            }

        });
        botonMultiplicadorPorDos.setDisable(true); //Para tener despues, cuando haya que implementarlo

        VBox cajaDeBonificadores = new VBox(10, botonMultiplicadorPorDos, botonMultiplicadorPorTres, botonExclusividad, botonAnulador);
        cajaDeBonificadores.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(10), Insets.EMPTY)));
        return cajaDeBonificadores;
    }

    private VBox ArmarBoxRespuestas(HBox boxJugadores, Modelo modelo){

        VBox cajaDeRespuestas = new VBox(20, boxJugadores);

        Pregunta preguntaAMostrar = modelo.ConseguirPregunta();

        ArrayList<Respuesta> respuestasPosibles = preguntaAMostrar.respuestasPosibles();

        Jugador jugadorActual = modelo.conseguirJugador();

        ToggleGroup grupoRespuestas = new ToggleGroup();



        for (Respuesta respuesta : respuestasPosibles) {
            RadioButton opcion = new RadioButton(respuesta.getRespuesta());

            opcion.setOnAction(g -> {jugadorActual.responder(preguntaAMostrar, new RespuestaAVerificar(respuesta.getRespuesta(),
                    jugadorActual.obtenerOrdenParcial()));
                opcion.setDisable(true);
            });
            opcion.setToggleGroup(grupoRespuestas);
            BotonesDeRespuestas.add(opcion);
            cajaDeRespuestas.getChildren().add(opcion);

        }
        cajaDeRespuestas.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, new CornerRadii(10), Insets.EMPTY)));
        return cajaDeRespuestas;
    }


    @Override
    public void AlCambiarVentana(Runnable funcion) {
        funcion.run();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (o instanceof Modelo) {
            Modelo modelo = (Modelo) o;

            Jugador currentPlayer = modelo.conseguirJugador();
            playerLabel.setText("Jugador Actual: " + currentPlayer.obtenerNombre());

            preguntaActual = modelo.ConseguirPregunta();
            labelEnunciado.setText(preguntaActual.getEnunciado());

            for (RadioButton button : BotonesDeRespuestas) {
                button.setDisable(false);
            }
        }
    }
}
