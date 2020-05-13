import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class PageSearchRunnable implements Runnable {
    ChordMessageInterface chordMessageInterface;
    long pg;
    String kword;
    List<String> res;

    public PageSearchRunnable(ChordMessageInterface CMI, long pageguid, String keyword, List<String> result) {
        this.chordMessageInterface = CMI;
        this.pg = pageguid;
        this.kword = keyword;
        this.res = result;
    }

    public void run() {
        try {
            // chordMessageInterface.search(kword, pg);
            res.add(chordMessageInterface.search(kword, pg).toString());
           // System.out.println(chordMessageInterface.search(kword, pg).toString());
            String x=chordMessageInterface.search(kword, pg).toString();
            Gson gson=new Gson();
            SongValues[] ni=gson.fromJson(x, SongValues[].class);
            int counter=0;
            for(SongValues item:ni){
                
                System.out.println("\nEntry : "+counter);
                System.out.println("Song name :"+item.retSong().rettitle());
                System.out.println("Artist name : "+item.artist.retname()+"\n----------------------------\n");
                counter++;
            }
            if (ni.length<1){
                System.out.printf("None available\n");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


}