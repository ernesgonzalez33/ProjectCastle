# Apuntes Wiki

Este fichero tiene como finalidad guardar los apuntes que crea conveniente de la Wiki de LibGDX

* El verdadero código del juego tiene que estar en una clase que implemente el `ApplicationListener`
  * Si no todos los métodos son relevantes, se puede usar la clase `ApplicationAdapter`
* Aquí se encuentra el [*LifeCycle*](https://github.com/libgdx/libgdx/wiki/The-life-cycle), muy importante revisarlo

* Se podría decir que el *Game Loop* es el método `render`

* El módulo de `Networking` puede ser interesante en el futuro

* Puede ser interesante añadir una pantalla de cargado, la información en este [enlace](https://github.com/libgdx/libgdx/wiki/Starter-classes-%26-configuration)

* *Logging*

```java
Gdx.app.log("MyTag", "my informative message");
Gdx.app.error("MyTag", "my error message", exception);
Gdx.app.debug("MyTag", "my debug message");
```

## Simple Game

* Página de un [juego simple](https://github.com/libgdx/libgdx/wiki/A-simple-game) para referencias

* Los *Assets* son cargados en el método `create`del `ApplicationAdapter`

* Usar `Sound` si dura menos de 10 segundos y `Music` si dura más es una buena regla para el *Soundtrack* del juego

```java
Gdx.audio.newSound();
Gdx.audio.newMusic();
```

* Sobre la limpieza de la pantalla

```java
Gdx.gl.glClearColor(1, 0, 0, 1); //RGB alpha
Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); //Limpia la pantalla
```

* Importante llamar el método `dispose` de todas las cosas

## Extending the simple game

* [Enlace](https://github.com/libgdx/libgdx/wiki/Extending-the-simple-game)

* La clase `Screen` tiene los métodos de `ApplicationListener` y además tiene `show` y `hide`

* La clase `Game` también implementa los métodos de `ApplicationListener` además de otros métodos para seleccionar la pantalla que quieras utilizar

* En `Game` dentro del método `create()`, tenemos que llamar a `this.setScreen(new NombreScreen(this))`

* **SUMAMENTE IMPORTANTE** utilizar `super.render();` dentro del método `render` en la clase `Game`

* Las diferentes `Screen` tienen que tener un atributo del tipo `Game` (y todo lo demás de un `ApplicationListener`) y crear un constructor con el mismo de argumento

```java
public MainMenuScreen(final Drop game) {
		this.game = game;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, 800, 480);

	}
```

* La razón por la que se usa el atributo del tipo `Game` es para poder usar el `SpriteBatch` y el `BitmapFont` de la clase principal, en lugar de usar uno por pantalla

* Todo el juego se programa en las diferentes pantallas y no en `Game`

* El método `dispose()` de las clases `Screen` no es llamado automáticamente. La forma más correcta de hacerlo es desde la clase `Game`

## Scene2D

* La clase `MoveToAction` permite mover actores y cambiar la duración del movimiento así como la nueva posición

* Los personajes tendrían que extender la clase `Actor` de `Scene2D`

* Ejemplo de código:

```java
@Override
public void create() {        
    stage = new Stage();
    Gdx.input.setInputProcessor(stage);
    
    MyActor myActor = new MyActor();
    
    MoveToAction moveAction = new MoveToAction();
    moveAction.setPosition(300f, 0f);
    moveAction.setDuration(10f);
    myActor.addAction(moveAction);
    
    stage.addActor(myActor);
}
```

* `SequenceAction` sirve para crear una secuencia de acciones

* Usar el `setBounds` es importante para limitar el `Actor`. Si no se hace, no renderiza bien

### Skins

* Tengo que meter el JSON a la `Skin`

```java
skin = new Skin(Gdx.files.internal("data/uiskin.json"));
```

* Ejemplo de gestionar un click a un botón:

```java
button.addListener(new ClickListener(){
            @Override 
            public void clicked(InputEvent event, float x, float y){
                button.setText("You clicked the button");
            }
        });
``` 
