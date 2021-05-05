package com.mlg.obu.Bean;

public class TokenBean {

    /**
     * result : 0
     * token : rBbOxxXlbb4E9GCAmpq8xbGW/nyFfwNr8nSzAkggyXnOa2nstUEDtF67ZZ/D2WFU
     * tokenvalidtime : 20180330094056621
     * key : U2KLG6xZRksUbLXvhCE4lg==
     * keyvalidtime : 20180330094056621
     * timestamp : 20180330094056621
     */

    private int result;
    private String token;
    private String tokenvalidtime;
    private String key;
    private String keyvalidtime;
    private String timestamp;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenvalidtime() {
        return tokenvalidtime;
    }

    public void setTokenvalidtime(String tokenvalidtime) {
        this.tokenvalidtime = tokenvalidtime;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getKeyvalidtime() {
        return keyvalidtime;
    }

    public void setKeyvalidtime(String keyvalidtime) {
        this.keyvalidtime = keyvalidtime;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
