package fr.tropweb.miningmanager.commands.threads;

import fr.tropweb.miningmanager.engine.Engine;

public class RegenerateThread implements Runnable {

    private final Engine engine;

    public RegenerateThread(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void run() {

    }
}
