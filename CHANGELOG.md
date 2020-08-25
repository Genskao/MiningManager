# ChangeLog

**Refactoring:**

- The mining task has been refactored
- Fix error in the titles of README.md4

**Fixed bugs:**

- Players cannot mining out a chunk that is already being mining out
- Fix error message:
  - **no block to** to **no precious block to**

**Merged pull requests:**

- [The mining task has been refactored](https://github.com/Genskao/MiningManager/pull/5)
- [Players cannot mining out a chunk that is already being mining out](https://github.com/Genskao/MiningManager/pull/6)

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
