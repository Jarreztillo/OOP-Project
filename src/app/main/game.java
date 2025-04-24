package app.main;

import app.main.Roaster.Roaster;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
// Cosas de JavaFX.



public class game extends Application {
    public static Stage window;
    public static AudioClip audio;
    private Scene mainScene;
    private Scene gameModeScene;
    // Escena, graficos, raiz y lienzo activados para su uso en toda la aplicacion.

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        game.window = window;
        /* Game.window es la variable publica estatica que usaran
        todas las clases para instanciar la ventana que necesiten.
         */
        windowDesign();
    }

    private void windowDesign() {
        Image fondoPrincipal= new Image("fondoPrincipal.png");
        ImageView vista = new ImageView(fondoPrincipal);
        Button play = new Button("Jugar.");
        play.getStyleClass().add("menu-button");
        Button options = new Button("Opciones. ");
        options.getStyleClass().add("menu-button");
        Button quit = new Button("Salir. ");
        quit.getStyleClass().add("menu-button");
        Label titulo = new Label("Cronicas de Valthar: El torneo de las eras.");
        // Botones y la marca del titulo.

        VBox mainMenu = new VBox(20);
        mainMenu.getChildren().addAll(play, options,quit);
        mainMenu.setAlignment(Pos.CENTER);

        vista.setPreserveRatio(false);
        vista.setFitWidth(850);
        vista.setFitHeight(832);

        StackPane root = new StackPane();
        root.getChildren().addAll(vista, mainMenu);
        mainScene = new Scene(root,832, 850);
        vista.fitWidthProperty().bind(mainScene.widthProperty());
        vista.fitHeightProperty().bind(mainScene.heightProperty());

        play.translateXProperty().bind(mainScene.widthProperty().multiply(-0.30)); options.translateXProperty()
                .bind(mainScene.widthProperty().multiply(-0.30)); quit.translateXProperty()
                .bind(mainScene.widthProperty().multiply(-0.30));


        mainScene.getStylesheets().add("buttons.css");

        play.setOnAction(_ -> gameModes());
        // El boton lleva a los modos de juego.


        quit.setOnAction(_ -> window.close());
        // El boton cierra el programa.

        window.setScene(mainScene);
        window.setTitle("Cronicas de Valthar: El torneo de las eras");
        window.show();
    }



    public void gameModes() {
        Button campaig = new Button("Campaña.");
        campaig.setStyle(
                "-fx-background-color: blue; "+
                "-fx-text-fill: white;"+
                "-fx-border: false; "
        );
        //Ceseando.
        Button pvp = new Button("PvP.");
        Button tournament = new Button("Torneo.");
        // Botones para inicializar modos de juego.

        VBox gameModeMenu = new VBox(30);
        gameModeMenu.getChildren().addAll(campaig, pvp, tournament);
        gameModeScene = new Scene(gameModeMenu, 832, 850);
        // Se añaden los botones: primero a su contenedor y luego a la pantalla.

        window.setScene(gameModeScene);
        // Se activa la escena donde se muestran todos los objetos.

        campaig.setOnAction(_ -> Roaster.initialize());
        // Cada boton lleva al modo de juego correspondiente.
    }



}

