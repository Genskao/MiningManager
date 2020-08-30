# ChangeLog

**implemented:**

- Economy plugins (VaultAPI)
- Prices for scan and mining

**Fixed bugs:**

- Start mining scan the chunk and mine 

## [Release 1.0.6](https://github.com/Genskao/MiningManager/releases/tag/v1.0.6)

**Fixed bugs:**

- Player cannot use colored shulker box
- **/mm reload** should be done 2 time to change the regeneration status
- Quartz Ore never scan on the version 1.11, 1.12

**Others:**

- Backward compatibility with spigot 1.11.

**Merged pull requests:**

- [Player cannot use colored shulker box](https://github.com/Genskao/MiningManager/pull/8)

## [Release 1.0.5](https://github.com/Genskao/MiningManager/releases/tag/v1.0.5)

**Refactoring:**

- The mining task has been refactored
- Fix error in the titles of README.md

**Fixed bugs:**

- **/mm reload** does not reload the changes on the config.yml
- Players can start mining on chunk of others player
- Fix error message:
  - **no block to** to **no precious block to**

**Merged pull requests:**

- [The mining task has been refactored](https://github.com/Genskao/MiningManager/pull/5)
- [Players cannot mining out a chunk that is already being mining out](https://github.com/Genskao/MiningManager/pull/6)
- [**/mm reload** reload the changes on the config.yml](https://github.com/Genskao/MiningManager/pull/7)

## [Release 1.0.4](https://github.com/Genskao/MiningManager/releases/tag/v1.0.4)

**Fixed bugs:**

- Regeneration stopped is not record into the configuration

**Merged pull requests:**

- [Release 1.0.4](https://github.com/Genskao/MiningManager/pull/4)

## [Release 1.0.3](https://github.com/Genskao/MiningManager/releases/tag/v1.0.3)

**Fixed bugs:**

- Check the version with generic method.

**Others:**

- Backward compatibility with spigot 1.14.

**Merged pull requests:**

- [Check the version with generic method.](https://github.com/Genskao/MiningManager/commit/48061155c97bee0398d9995e0b0bd1f2261c289b)

## [Release 1.0.2](https://github.com/Genskao/MiningManager/releases/tag/v1.0.2)

**Others:**

- Backward compatibility with spigot 1.15.

**Merged pull requests:**

- [Backward compatibility with spigot 1.15.](https://github.com/Genskao/MiningManager/pull/3)

## [Release 1.0.1](https://github.com/Genskao/MiningManager/releases/tag/v1.0.1)

**Fixed bugs:**

- java.lang.ClassCastException / cannot be cast to org.bukkit.entity.Player: Block the console to use the plugin
- Block placed and broken never removed from database
- Bad error message "was does not" to "does not"
- Bad permission to use properly the plugin:
  - mm.scan.one to mm.scan.
  - mm.mining.one to mm.mining
  - mm.regeneration.start to mm.regeneration
- MiningManager does not exist when the application start for the first time

**Others:**

- Add javadoc and comments.

**Merged pull requests:**

- [Release 1.0.1](https://github.com/Genskao/MiningManager/pull/2)


## [Release 1.0.0](https://github.com/Genskao/MiningManager/releases/tag/v1.0.0)

**Implemented:**

Create the first release of the project, the user can:

- Mine automatically
- Scan chunk
- Start the regeneration of the worlds

**Merged pull requests:**

- [[Release 1.0.0] The first feature](https://github.com/Genskao/MiningManager/pull/1)
