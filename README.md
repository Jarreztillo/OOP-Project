# Documento de visión inicial (Readme).


## Equipo 1:
## 1- Harry Pérez Castillo.
## 2- Lázaro Luis Ayala Oquendo.
## 3- Arturo Manuel Álvarez González.
## 4- Xavier Alejandro Alfonso Borges.
## 5- Eduardo Cortés Odio.

### Tema 2: 
### Crónicas de Valthar: El torneo de las Eras.

---

# En las siguientes líneas del documento de visión inicial del Trabajo de Curso para DPOO, se hablará de:
## 1- Describir lo que se hará en cuanto a desarrollo.
## 2- Tareas propuestas a cada miembro.
## 3- Tecnologías usadas en el desarrollo.
## 4- Librerías y su explicación.
## 5- Función del juego.
## 6- Descripción del sistema de carpetas del software.




# R1/ 
En cuanto a desarrollo, tenemos planeado un modelo de proceso de software incremental. Entiéndase que va por fases, cambia dependiendo de los avances que tengamos del programa y se puede volver a iterar en caso de que cambien los requisitos del proyecto. 

A continuación hay una figura de su representación general y luego otra imagen sobre cómo se adaptó a nuestro caso junto a una explicación:

![.](https://github.com/user-attachments/assets/53b1a035-4903-43d2-8e75-123009925540)

![.](https://github.com/user-attachments/assets/68cc24f3-a0fe-4c06-adf8-a83b2eebc41b)


Estas etapas NO influyen en las entregas semanales (por el contrario, las entregas semanales influyen en las etapas). Se dividen en tres por el amplio código que se necesita para que no solamente el juego funcione bien, sino que se sienta o fluya bien para el jugador.

Al finalizar la Etapa 1, estarán todas las mecánicas del juego ya hechas. Eso sí, sin haberse hecho control de errores aún.

Al finalizar la Etapa 2, se habrán perfeccionado las mecánicas y se habrán añadido algunas otras, además de haber arreglado gran parte de los errores.

Al finalizar la Etapa 3, el juego pasaría de tener una interfaz visual pobre y solamente para pruebas, a ser una interfaz de calidad y c
on efectos de sonido y música.




# R2/ 

Para cada miembro en este equipo, se han dividido las tareas según ciertas funcionalidades del juego y la afinidad de estos integrantes a esas tareas. Muestro entonces la repartición inicial que hemos hecho con tal de crear el esqueleto de buena parte de las funciones del juego:

Harry Pérez Castillo: controles e inteligencia artificial de los enemigos.
Lázaro Luis Ayala Oquendo: sistema de combate y de consumibles.
Arturo Manuel Álvarez González: mapa y menús.
Xavier Alejandro Alfonso Borges: roaster e implementación del equipo de personajes en el juego.
Eduardo Cortés Odio: modo campaña y sus escenas (cutscenes).


# R3/ 
El juego constará de varias clases (algunas pueden surgir sin haber sido planeadas, debido al tamaño del proyecto y sus funcionalidades), de las que se enumerarán la carpeta a la que corresponden, la cantidad que tienen y cada una de ellas:

Main:
1- AssetsManager: gestor de recursos del juego.
2- CollisionChecker: comprobador de colisiones.
3- Config: gestión de configuraciones.
4- Cutscene: escenas no jugables dentro del juego.
5- EntityGenerator: generador de entidades.
6- Event Handler: gestor de eventos.
7- Game Panel: marco del juego (o sea, la ventana que se crea y gestionará todo lo gráfico del juego).
8- Main: método principal por el que correrá todo lo demás.
9- Sound: gestor de los efectos de sonido y música del juego.
10- UI: interfaz de usuario.


Entities:
1- Entity: entidad general, de la que heredan Characters y Enemies.
2- Characters: clase personajes.
3- Enemies: clase enemigos.

AI:
1- Node: clase nodo (véase Teoría de Grafos).
2- PathFinding: para encontrar el camino y llegar al jugador a través de los nodos.
3- CombatActions: acciones de la Inteligencia Artificial del enemigo en el combate.

HexMap:

1- HexGrid: matriz de hexágonos que se usará como mapa.
2- HexGridManager: gestor de dicha matriz.

HexMap Interactions:

1- EvP Interactions: interacciones Enemigo y Jugador en el Mapa.
2- RvP Interactions: interacciones Relieve y Jugador en el Mapa.
3- RvE Interactions: interacciones Relieve y Enemigo en el Mapa.
R4/ De momento, solamente usaremos la librería JavaFX (ampliamente usada y actualizada para crear interfaces gráficas) y las librerías estándar del vigésimo tercer Kit de Desarrollo de Java. 

# R5/ 

El juego tiene muchas funcionalidades, que aquí dividiremos en 4:

1- Funciones del mapa: tratan el movimiento del personaje (que serán 5, pero en el mapa se verá solamente uno) por el mapa y por puntos de acción, los efectos que tendrán en ellos el relieve, las trampas e incluso algunos enemigos y las colisiones.
2- Funciones del combate: basado en los típicos sistemas por turnos de RPG, este tendrá las funcionalidades de: Ataque, Habilidades, Consumibles y Huir (esta última opción desactivada en ciertas ocasiones); la funcionalidad de este empezará cuando se haga colisión con un enemigo, donde se pasará a una pantalla especial para esta funcionalidad.
3- Funciones de los modos de juego: en el modo campaña, se tendrán diálogos con una interfaz visual mostrando al personaje, su nombre y su diálogo (de sobrar tiempo, se diferenciarán los diálogos del personaje principal en el equipo elegido), habrán ciertos objetivos a cumplir y se añadirá alguna que otra cinemática (cutscene) para mejorar la experiencia. En el modo PvP, se elegirá un escenario de dos y luego a los cinco personajes de cada uno, para posteriormente ganar o acabando con todo el equipo del otro jugador o alcanzando un Núcleo de Era. En el modo Torneo, con unas combinaciones de hasta 26334 (C22 5) equipos posibles, se agarrarán dieciséis equipos que lucharán entre sí, obteniendo niveles y consumibles por el recorrido hasta ser finalistas (única forma de ganar en este modo).
4- Funciones del menú: botón de iniciar el juego (posteriormente llevaría a otro menú con la selección del modo de juego y, posteriormente, de los personajes), botón para explicar los controles y botón de cerrar el juego.


# R6/ 

De momento, el sistema de carpetas del software sería:

1- Src (incluyendo las carpetas de Clases y las carpetas de los Objetos predefinidos).
2- Nbproject (carpetas generadas automáticamente).
3- Test (carpetas generadas automáticamente).
4- Assets (música, efectos de sonido, sprites).

