//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.springframework.core;

public abstract class JdkVersion {
    public static final int JAVA_13 = 0;
    public static final int JAVA_14 = 1;
    public static final int JAVA_15 = 2;
    public static final int JAVA_16 = 3;
    public static final int JAVA_17 = 4;
    public static final int JAVA_18 = 5;
    private static final String javaVersion = System.getProperty("java.version");
    private static final int majorJavaVersion;

    public static String getJavaVersion() {
        return javaVersion;
    }

    public static int getMajorJavaVersion() {
        return majorJavaVersion;
    }

    public static boolean isAtLeastJava14() {
        return true;
    }

    public static boolean isAtLeastJava15() {
        return getMajorJavaVersion() >= 2;
    }

    public static boolean isAtLeastJava16() {
        return getMajorJavaVersion() >= 3;
    }

    static {
        if (javaVersion.indexOf("1.8.") != -1) {
            majorJavaVersion = JAVA_18;
        }else if (javaVersion.indexOf("1.7.") != -1) {
            majorJavaVersion = JAVA_17;
        } else if (javaVersion.indexOf("1.6.") != -1) {
            majorJavaVersion = JAVA_16;
        } else if (javaVersion.indexOf("1.5.") != -1) {
            majorJavaVersion = JAVA_15;
        } else {
            majorJavaVersion = JAVA_14;
        }

    }
}
