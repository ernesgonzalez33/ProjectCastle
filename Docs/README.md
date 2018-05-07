# ProjectCastle

Tactical RPG with only one level too easy to beat. It can get bigger.

## How to start the game

Run the executable jar.

To make the executable:

```
./gradlew desktop:dist
```

## How to play

### Main Menu Screen

The first screen is very simple, just a title and a button. To start playing the game, touch that button.

### Game Screen

The first level (and only one so far) takes place in the bounds of a castle. The main objective is to defeat the only enemy you have in front, very easy to do, in fact, the only way to die in this game is not attacking the enemy.

In your turn, you have two actions you can do with each one of your characters: move or attack. Every time you touch your character, a menu pops out with these two buttons. To attack an enemy, you have to be in an adjacent tile, so the normal procedure is to move near the enemy and attack it when you are adjacent to it.

You only can move once, but if you attack first, then you can't move again. So think well what you should do.

Also, each time you open the menu, you can see the information about your character, and to see the info about the enemies, you can touch them.

In their turn, the enemies will come close to you and attack you, they have the same rules as you and know the same about you that you know about them. They move thinking about your stats.

Your stats (and enemies') grow when you attack or defend, feel free to notice the change every time the info shows at you. There are still some tweaks to do on this matter.