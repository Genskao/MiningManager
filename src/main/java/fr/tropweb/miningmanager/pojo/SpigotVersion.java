package fr.tropweb.miningmanager.pojo;

public class SpigotVersion {
    private static final String VERSION_REGEX = ".*\\(MC\\: (\\d+.\\d+(.\\d+)*)\\)";

    private static String version;
    private static int currentMajor;
    private static int currentMinor;

    public SpigotVersion(final String fullVersion) {
        // set the version
        version = fullVersion;
        currentMajor = -1;
        currentMinor = -1;

        // prepare information
        final String[] split = fullVersion.replaceAll(VERSION_REGEX, "$1").split("\\.");

        // we should have 2 number
        if (split.length >= 2) {
            currentMajor = Integer.valueOf(split[0]);
            currentMinor = Integer.valueOf(split[1]);
        }
    }

    public boolean checkVersion(final int major, final int minor) {
        return currentMajor >= major && currentMinor >= minor;
    }
}
