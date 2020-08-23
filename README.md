# MiningManager

This goal to the plugin is to give the possibility to scan the chunk and see if there is lot of resources, mine automatically all precious resources and regenerate the world.

## Command and permission
 - **/mm <help>** Have help about the plugin <br />
  *no permission* <br />
 - **/mm scan** Give to the player the ability to scan a chunk <br />
   *mm.scan.only* <br />
 - **/mm scan auto** Give to the player the ability to scan a chunk when the player move <br />
   *mm.scan.auto* <br />
 - **/mm mining** Start the auto mining on the selected chunk <br />
   *mm.mining.start* <br />
 - **/mm mining stop** Stop the auto mining <br />
   *mm.mining.stop* <br />
 - **/mm mining show** Show the precious resources left <br />
   *mm.mining.show* <br />
 - **/mm reload** Reload the plugin and the configuration <br />
   *mm.reload* <br />
 - **/mm regeneration** Start the regenration of the precious resources of the worlds <br />
   *mm.regeneration.start* <br />
 - **/mm regeneration stop** Stop the regenration <br />
   *mm.regeneration.stop* <br />

## Configuration
mining-interval: Time in second to place the block to the chest <br />
mining-timeout: The time in second to click on chest when you want start the mining <br />
mining-start: How many time after the click on the chest (in second)? <br />
mining-effect: Do you want some... effect? more fun for the player :D <br />
  smite: Smite the block while mining <br />
  explosion: Explosion to have sound effect <br />
Do you want to regenerate mined blocks? <br />
regeneration-active: action or not the regeneration blocks <br />
regeneration-interval: Interval between blocks in seconds <br />

### Default configuration <br />
mining-interval: 10 <br />
mining-timeout: 15 <br />
mining-start: 0 <br />
mining-effect: <br />
  smite: false <br />
  explosion: true <br />
regeneration-active: true <br />
regeneration-interval: 30 <br />
