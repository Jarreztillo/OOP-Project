module Game {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.media;

    opens app;
    opens app.gameModes;
    opens app.main;
}