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

* Página de un [juego simple](https://github.com/libgdx/libgdx/wiki/A-simple-game) para referencias
