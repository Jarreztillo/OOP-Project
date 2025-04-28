package app.main;

import app.main.Roaster.Roaster;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.scene.control.Slider;
// Cosas de JavaFX.


public class Game extends Application {
    public static Stage window;
    public static AudioClip audio;
    private Scene mainScene;
    private VBox mainMenu;
    // Escena, graficos, raiz y lienzo activados para su uso en toda la aplicacion.


    private Button play;
    private Button options;
    private Button quit;
    private Button atras;
    private Button campaign = new Button();
    private Button pvp = new Button();
    private Button tournament = new Button();
    private Button Audio;
    private Button video;
    private Button controles;


    private Slider volumen;
    private HBox sliderContainer;;



    // Botones y Labels.

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        Game.window = window;
        /* Game.window es la variable publica estatica que usaran
        todas las clases para instanciar la ventana que necesiten.
         */
        AudioPlayer.init();
        windowDesign();
    }

    private void windowDesign() {
        AudioPlayer.playMainMenu();

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
        Audio=new Button("Audio");
        Audio.getStyleClass().add("menu-button");
        atras=new Button("Atras");
        atras.getStyleClass().add("menu-button");
        video = new Button("Video");
        video.getStyleClass().add("menu-button");
        controles = new Button("Controls");
        controles.getStyleClass().add("menu-button");

        // Botones.


        volumen=new Slider(0,1,0.4);
        volumen.setShowTickLabels(true);

        sliderContainer=new HBox();
        sliderContainer.setAlignment(Pos.CENTER);
        sliderContainer.getChildren().add(volumen);
        sliderContainer.setPrefSize(200,20);
        volumen.setPrefSize(200,20);
        volumen.valueProperty().addListener((observable, oldValue, newValue) -> {
            double newVolumen= newValue.doubleValue();
            AudioPlayer.setVolumeForAllSounds(newVolumen);
        });


        mainMenu = new VBox(10);
        mainMenu.getChildren().addAll(play, options,quit,campaign, pvp,tournament,Audio,atras,video,controles,sliderContainer);
        mainMenu.setAlignment(Pos.CENTER);

        disableGameModesButtons();
        disableVolumenSlider();
        disableOptionsButton();

        vista.setPreserveRatio(false);
        vista.setFitWidth(850);
        vista.setFitHeight(832);

        StackPane root = new StackPane();
        root.getChildren().addAll(vista, mainMenu);
        mainScene = new Scene(root,832, 850);
        vista.fitWidthProperty().bind(mainScene.widthProperty());
        vista.fitHeightProperty().bind(mainScene.heightProperty());

        //Traslacion de los botones en el eje de las X
        play.translateXProperty().bind(mainScene.widthProperty().multiply(-0.30));
        options.translateXProperty().bind(mainScene.widthProperty().multiply(-0.30));
        quit.translateXProperty().bind(mainScene.widthProperty().multiply(-0.30));

        campaign.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));
        pvp.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));
        tournament.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));

        controles.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));
        video.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));
        Audio.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));
        atras.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));
        sliderContainer.translateXProperty().bind(mainScene.widthProperty().multiply(0.30));


        //Traslacion de los botones en el eje de las Y
        play.translateYProperty().bind(mainScene.widthProperty().multiply(0.24));
        options.translateYProperty().bind(mainScene.widthProperty().multiply(0.24));
        quit.translateYProperty().bind(mainScene.widthProperty().multiply(0.24));

        campaign.translateYProperty().bind(mainScene.widthProperty().multiply(0.03));
        pvp.translateYProperty().bind(mainScene.widthProperty().multiply(0.03));
        tournament.translateYProperty().bind(mainScene.widthProperty().multiply(0.03));

        controles.translateYProperty().bind(mainScene.widthProperty().multiply(-0.45));
        video.translateYProperty().bind(mainScene.widthProperty().multiply(-0.31));
        Audio.translateYProperty().bind(mainScene.widthProperty().multiply(-0.105));
        atras.translateYProperty().bind(mainScene.widthProperty().multiply(-0.105));
        sliderContainer.translateYProperty().bind(mainScene.widthProperty().multiply(-0.35));

        mainScene.getStylesheets().add("buttons.css");

        play.setOnAction(_ -> enableGameModesButtons());
        // El boton lleva a los modos de juego.

        quit.setOnAction(_ -> window.close());
        // El boton cierra el programa.

        campaign.setOnAction(_ -> initializeRoaster());
        // El boton inicia el modo campaña.

        options.setOnAction(_ -> enableOptionsButton());
        //El boton lleva al interior del menu de opciones.

        atras.setOnAction(_ -> {
                    if (sliderContainer.isVisible()) {
                        disableVolumenSlider();
                        enableOptionsButton();
                    } else if (controles.isVisible() || video.isVisible() || Audio.isVisible()) {
                        disableOptionsButtonByBack();

                    }
                });
        //El boton cierra el menu de opciones.

        Audio.setOnAction(_ -> enableVolumenSlider());
        //EL boton abre la barra de volumen.


        window.setScene(mainScene);
        window.setTitle("Cronicas de Valthar: El torneo de las eras");
        window.show();
    }

    private void initializeRoaster(){
        AudioPlayer.playButtonSound();
        AudioPlayer.stopMainMenu();
        Roaster.initialize();
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
        AudioPlayer.playButtonSound();
        campaign.setDisable(false);
        campaign.setVisible(true);
        pvp.setDisable(false);
        pvp.setVisible(true);
        tournament.setDisable(false);
        tournament.setVisible(true);
        Audio.setVisible(false);
        atras.setVisible(false);
        controles.setVisible(false);
        video.setVisible(false);
    }

    private void enableOptionsButton(){
        AudioPlayer.playButtonSound();
        campaign.setVisible(false);
        pvp.setVisible(false);
        tournament.setVisible(false);
        controles.setVisible(true);
        video.setVisible(true);
        Audio.setVisible(true);
        atras.setVisible(true);

    }

    private void disableOptionsButtonByBack(){
        AudioPlayer.playButtonSound();
        controles.setVisible(false);
        video.setVisible(false);
        Audio.setVisible(false);
        atras.setVisible(false);
    }
    private void disableOptionsButton(){
        controles.setVisible(false);
        video.setVisible(false);
        Audio.setVisible(false);
        atras.setVisible(false);
    }
    private void enableVolumenSlider(){
        AudioPlayer.playButtonSound();
        controles.setVisible(false);
        video.setVisible(false);
        Audio.setVisible(false);
        atras.setVisible(true);
        sliderContainer.setVisible(true);
    }
    private void disableVolumenSlider(){
        sliderContainer.setVisible(false);

    }

}

