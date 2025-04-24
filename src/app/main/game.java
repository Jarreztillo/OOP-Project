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


    private Button play;
    private Button options;
    private Button quit;
    private Button campaign = new Button();
    private Button pvp = new Button();
    private Button tournament = new Button();

    // Botones y Labels.

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
        play = new Button("Jugar");
        play.getStyleClass().add("menu-button");
        options = new Button("Opciones");
        options.getStyleClass().add("menu-button");
        quit = new Button("Salir");
        quit.getStyleClass().add("menu-button");
        campaign = new Button("Campaña.");
        campaign.getStyleClass().add("menu-button");
        pvp = new Button("PvP.");
        pvp.getStyleClass().add("menu-button");
        tournament = new Button("Torneo.");
        tournament.getStyleClass().add("menu-button");

        // Botones.

        disableGameModesButtons();
        VBox mainMenu = new VBox(20);
        mainMenu.getChildren().addAll(play, options,quit,campaign, pvp,tournament);
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

        campaign.translateXProperty().bind(mainScene.widthProperty().multiply(0.30)); pvp.translateXProperty()
                .bind(mainScene.widthProperty().multiply(0.30)); tournament.translateXProperty()
                .bind(mainScene.widthProperty().multiply(0.30));

                campaign.setTranslateY(-200);
                pvp.setTranslateY(-190);
                tournament.setTranslateY(-180);

        mainScene.getStylesheets().add("buttons.css");

        play.setOnAction(_ -> enableGameModesButtons());
        // El boton lleva a los modos de juego.

        quit.setOnAction(_ -> window.close());
        // El boton cierra el programa.

        campaign.setOnAction(_ -> Roaster.initialize());
        // El boton inicia el modo campaña.


        window.setScene(mainScene);
        window.setTitle("Cronicas de Valthar: El torneo de las eras");
        window.show();
    }


    private void disableGameModesButtons(){
        campaign.setDisable(true);
        campaign.setVisible(false);
        pvp.setDisable(true);
        pvp.setVisible(false);
        tournament.setDisable(true);
        tournament.setVisible(false);
    }

    private void enableGameModesButtons(){
        campaign.setDisable(false);
        campaign.setVisible(true);
        pvp.setDisable(false);
        pvp.setVisible(true);
        tournament.setDisable(false);
        tournament.setVisible(true);
    }

}

