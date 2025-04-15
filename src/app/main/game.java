package app.main;

import app.Roaster;
import app.cutscenes.yosbelFirstApparition;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
// Cosas de JavaFX.



public class game extends Application {


    public static Stage window;
    private Scene mainScene;
    private Scene gameModeScene;
    // Escena, graficos, raiz y lienzo activados para su uso en toda la aplicacion.

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage window) throws Exception {
        game.window = window;
        /* Game.window es la variable publica estatica que usaran
        todas las clases para instanciar la ventana que necesiten.
         */
        windowDesign();
    }
    private void windowDesign() {
        Button play = new Button("Jugar.");
        Button options = new Button("Opciones. ");
        Button quit = new Button("Salir. ");
        Label titulo = new Label("Cronicas de Valthar: El torneo de las eras.");
        // Botones y la marca del titulo.

        VBox mainMenu = new VBox(20);
        mainMenu.getChildren().addAll(titulo, play, options, quit);
        mainScene = new Scene(mainMenu, 832, 850);
        // Añadiendo todos estos botones al menu.

        play.setOnAction(e -> gameModes());
        // El boton lleva a los modos de juego.

        quit.setOnAction(e -> window.close());
        // El boton cierra el programa.

        window.setScene(mainScene);
        window.setTitle("Cronicas de Valthar: El torneo de las eras");
        window.show();



    }



    public void gameModes() {
        Button campaig = new Button("Campaña.");
        Button pvp = new Button("PvP.");
        Button tournament = new Button("Torneo.");
        // Botones para inicializar modos de juego.




        VBox gameModeMenu = new VBox(30);
        gameModeMenu.getChildren().addAll(campaig, pvp, tournament);
        gameModeScene = new Scene(gameModeMenu, 832, 850);
        // Se añaden los botones: primero a su contenedor y luego a la pantalla.

        window.setScene(gameModeScene);
        // Se activa la escena donde se muestran todos los objetos.

        Roaster roaster = new Roaster();
        yosbelFirstApparition yosbel = new yosbelFirstApparition();

        campaig.setOnAction(e -> roaster.initialize());
        // Cada boton lleva al modo de juego correspondiente.
    }



}

