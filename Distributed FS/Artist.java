/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nisalgamage
 */
public class Artist {

    private String terms_freq;
    private String id, terms, name, location, similar;
    private double familiarity, longitude, latitude, hotttnesss;

    public String retid() {
        return this.id;
    }

    public String retterms_freq() {
        return this.terms_freq.toString();
    }

    public String retterms() {
        return this.terms;
    }

    public String retname() {
        return this.name;
    }

    public String retlocation() {
        return this.location;
    }

    public String retsimilar() {
        return this.similar;
    }

    public double retfamiliarity() {
        return this.familiarity;
    }

    public double retlongitude() {
        return this.longitude;
    }

    public double retlatitude() {
        return this.latitude;
    }

    public double rethotttnesss() {
        return this.hotttnesss;
    }

}
