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
