CREATE TABLE IF NOT EXISTS precious_ore (
    world TEXT NOT NULL,
    x INTEGER NOT NULL,
    y INTEGER NOT NULL,
    z INTEGER NOT NULL,
    material TEXT NOT NULL,
    placed INTEGER NOT NULL,
    PRIMARY KEY (world, x, y, z)
);

