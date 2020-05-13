/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author nisalgamage
 */
public class Song {

    private int year;
    private String title, artist_mbtags,id,key;
    private double duration;
    private float mode_confidence, artist_mbtags_count, key_confidence, tatums_start, hotttnesss, beats_start, time_signature_confidence, bars_confidence,
            bars_start, start_of_fade_out, tempo, end_of_fade_in, beats_confidence, mode,
            time_signature, loudness;

    public String retkey() {
        return this.key.toString();
    }

    public int retyear() {
        return this.year;
    }

    public String retid() {
        return this.id.toString();
    }

    public String retartist_mbtags() {
        return this.artist_mbtags;
    }

     public String rettitle() {
        return this.title;
    }
     
    public double retduration() {
        return this.duration;
    }

    public float retmode_confidence() {
        return this.mode_confidence;
    }

    public float retartist_mbtags_count() {
        return this.artist_mbtags_count;
    }

    public float retkey_confidence() {
        return this.key_confidence;
    }

    public float rettatums_start() {
        return this.tatums_start;
    }

    public float rethotttnesss() {
        return this.hotttnesss;
    }

    public float retbeats_start() {
        return this.beats_start;
    }

    public float rettime_signature_confidence() {
        return this.time_signature_confidence;
    }

    public float retbars_confidence() {
        return this.bars_confidence;
    }

    public float retbars_start() {
        return this.bars_start;
    }

    public float retstart_of_fade_out() {
        return this.start_of_fade_out;
    }

    public float rettempo() {
        return this.tempo;
    }

    public float retend_of_fade_in() {
        return this.end_of_fade_in;
    }

    public float retbeats_confidence() {
        return this.beats_confidence;
    }

    public float retmode() {
        return this.mode;
    }

    public float rettime_signature() {
        return this.time_signature;
    }

    public float retloudness() {
        return this.loudness;
    }

}
