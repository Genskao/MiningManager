# MiningManager

This goal to the plugin is to give the possibility to scan the chunk and see if there is lot of resources, mine automatically all precious resources and regenerate the world. <br />
The plugin works from spigot 1.11 to 1.16.

You can found the plugin here: https://www.spigotmc.org/resources/miningmanager.83050/

## Command and permission
 - **/mm <help>** Have help about the plugin <br />
  *no permission*
 - **/mm scan** Give to the player the ability to scan a chunk <br />
   *mm.scan*
 - **/mm scan auto** Give to the player the ability to scan a chunk when the player move <br />
   *mm.scan.auto*
 - **/mm mining** Start the auto mining on the selected chunk <br />
   *mm.mining*
 - **/mm mining stop** Stop the auto mining <br />
   *mm.mining.stop*
 - **/mm mining show** Show the precious resources left <br />
   *mm.mining.show*
 - **/mm reload** Reload the plugin and the configuration <br />
   *mm.reload*
 - **/mm regeneration** Start the regenration of the precious resources of the worlds <br />
   *mm.regeneration*
 - **/mm regeneration stop** Stop the regenration <br />
   *mm.regeneration.stop*

## Configuration
```
mining-interval: Time in second to place the block to the chest
mining-timeout: The time in second to click on chest when you want start the mining
mining-start: How many time after the click on the chest (in second)?
mining-effect: Do you want some... effect? more fun for the player :D
  smite: Smite the block while mining
  explosion: Explosion to have sound effect
Do you want to regenerate mined blocks?
regeneration-active: action or not the regeneration blocks
regeneration-interval: Interval between blocks in seconds
```

### Default configuration
```
mining-interval: 10
mining-timeout: 15
mining-start: 0
mining-effect:
  smite: false
  explosion: true
regeneration-active: true
regeneration-interval: 30
```

## Features
- Add Economy
- Add Towny