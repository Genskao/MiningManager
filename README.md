# MiningManager

This goal to the plugin is to give the possibility to scan the chunk and see if there is lot of resources, mine automatically all precious resources and regenerate the world. <br />
The plugin works from spigot 1.11 to 1.16.

You can found the plugin here: https://www.spigotmc.org/resources/miningmanager.83050/

## Command and permission

- **/mm <help>** Have help about the plugin <br />
  - *no permission*
- **/mm scan** Give to the player the ability to scan a chunk <br />
  - *mm.scan*
- **/mm scan auto** Give to the player the ability to scan a chunk when the player move <br />
  - *mm.scan.auto*
- **/mm mining** Start the auto mining on the selected chunk <br />
  - *mm.mining*
- **/mm mining stop** Stop the auto mining <br />
  - *mm.mining.stop*
- **/mm mining show** Show the precious resources left <br />
  - *mm.mining.show*
- **/mm reload** Reload the plugin and the configuration <br />
  - *mm.reload*
- **/mm regeneration** Start the regeneration of the precious resources of the worlds <br />
  - *mm.regeneration*
- **/mm regeneration stop** Stop the regeneration <br />
  - *mm.regeneration.stop*
   
### Others
 
- **mm.ignore.price** Ignore price for scan
- **mm.ignore.towny** Ignore towny permission to start mining

## Configuration

### Documentation

```
mining:
  interval: Time (in second) to place the block to the selected chest
  timeout: Time (in second) to select a chest to start mining
  start: Time (in second) to start the mining after you have select a chest
  effect:
    smite: Active/Unactive smite effect on the mined block
    explosion: Active/Unactive explosion with no power to have sound/graphic effect
  price: Price to start the mining, the money get back if you don't select chest. Set to 0 to unactive.
scan:
  price: Price to start a scan of the chunk. Set to 0 to unactive.
regeneration:
  active: Active/Unactive the regeneration of blocks
  interval: Interval between blocks in seconds
```

### Default configuration

```
mining:
  interval: 10
  timeout: 15
  start: 10
  effect:
    smite: false
    explosion: true
  price: 100.0
scan:
  price: 10.0
regeneration:
  active: true
  interval: 30
```
