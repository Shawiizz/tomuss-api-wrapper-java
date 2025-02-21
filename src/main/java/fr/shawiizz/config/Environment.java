package fr.shawiizz.config;

public class Environment {
    public static final String TOMUSS_URL = "https://tomuss.univ-lyon1.fr";
    public static final String CAS_URL = "https://cas.univ-lyon1.fr";
    public static final String USER_AGENT = "Mozilla/5.0 (X11; Linux x86_64; rv:109.0) Gecko/20100101 Firefox/114.0";

    /**
     * Returns the CAS login URL
     */
    public static String getCasLoginUrl() {
        return CAS_URL + "/cas/login";
    }

    /**
     * Returns the CAS logout URL
     */
    public static String getCasLogoutUrl() {
        return CAS_URL + "/cas/logout";
    }
}
