import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
// import a json package
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.google.gson.Gson;

/* JSON Format

 {
    "metadata" :
    {
        file :
        {
            name  : "File1"
            numberOfPages : "3"
            pageSize : "1024"
            size : "2291"
            page :
            {
                number : "1"
                guid   : "22412"
                size   : "1024"
            }
            page :
            {
                number : "2"
                guid   : "46312"
                size   : "1024"
            }
            page :
            {
                number : "3"
                guid   : "93719"
                size   : "243"
            }
        }
    }
}
 
 
 */

public class DFS {
    /////////////////////////////////////////////////////////////////////////////////
    /**
     * This class is for pagesJson, containing the setters and getters
     */
    public class PagesJson {
        Long guid;
        Long size;
        String CreateTimeStamp;
        String WriteTimeStamp;
        String ReadTimeStamp;
        int pageNum;

        /**
         * Constructor for the pages
         *
         * @param guid              - specifies the global unique identifier
         * @param size              - size of
         * @param CreationTimeStamp
         * @param ReadTimeStamp
         * @param WriteTimeStamp
         * @param counter
         */
        public PagesJson(Long guid, Long size, String CreationTimeStamp, String ReadTimeStamp, String WriteTimeStamp,
                int counter) {
            this.guid = guid;
            this.size = size;
            this.CreateTimeStamp = CreationTimeStamp;
            this.ReadTimeStamp = ReadTimeStamp;
            this.WriteTimeStamp = WriteTimeStamp;
            this.pageNum = counter;
        }

        // getters
        public long getSize() {

            return size;
        }

        public long getGUID() {

            return this.guid;
        }

        public String getCreateTimeStamp() {
            return this.CreateTimeStamp;
        }

        public int getPageNumber() {
            return this.pageNum;
        }

        // setters
        public void setSize(Long size) {
            this.size = size;
        }

        public void setGUID(Long guid) {
            this.guid = guid;
        }

        public void setCreateTimeStamp(String CreateTimeStamp) {
            this.CreateTimeStamp = CreateTimeStamp;
        }

        public void setWriteTimeStamp(String WriteTimeStamp) {
            this.WriteTimeStamp = WriteTimeStamp;
        }

        public void setReadTimeStamp(String ReadTimeStamp) {
            this.ReadTimeStamp = ReadTimeStamp;
        }

        public void setPageNumber(int pageNum) {
            this.pageNum = pageNum;
        }

    }

    ;

    /*
     * name : "File1" numberOfPages : "3" pageSize : "1024" size : "2291" page :
     * 
     */

    /////////////////////////////////////////////////////////////////////////////////////
    /**
     * This class is for FileJson and its setters and getters
     */
    public class FileJson {
        String name;
        int MaxPageNumber;
        Long size;
        String CreateTimeStamp;
        String WriteTimeStamp;
        String ReadTimeStamp;
        int counter;
        int PageNumber;

        ArrayList<PagesJson> pages = new ArrayList<PagesJson>();

        public FileJson() {
            this.size = 0L;
            this.PageNumber = 0;
            this.counter = 0;
            this.CreateTimeStamp = LocalDateTime.now().toString();
            this.ReadTimeStamp = "0";
            this.WriteTimeStamp = "0";
            this.MaxPageNumber = 0;
            this.pages = new ArrayList<PagesJson>();
        }

        public void addPage(Long guid, Long size, String CreateTimeStamp, String ReadTimeStamp, String WriteTimeStamp,
                int counter) {
            PagesJson newPage = new PagesJson(guid, size, CreateTimeStamp, ReadTimeStamp, WriteTimeStamp, counter);
            pages.add(newPage);
        }

        // Getters
        public String getName() {
            return this.name;
        }

        public Long getSize() {
            return this.size;
        }

        public int getMaxPageNumber() {
            return this.MaxPageNumber;
        }

        public int getPageNumber() {
            return this.PageNumber;
        }

        public ArrayList<PagesJson> getPages() {
            return pages;
        }

        public String getCreateTimeStamp() {
            return CreateTimeStamp;
        }

        // Setters
        public void setName(String name) {
            this.name = name;
        }

        public void setSize(Long size) {
            this.size = size;
        }

        public void setMaxPageNumber(int MaxPageNumber) {
            this.MaxPageNumber = MaxPageNumber;
        }

        public int getNumOfPages() {
            return pages.size();
        }

        public void addSize(Long size) {
            this.size += size;
        }

        public void setCounter(int counter) {
            this.counter = counter;
        }

        public void setPageNumber(int PageNumber) {
            this.PageNumber = PageNumber;
        }

        public void addPageNumber(int PageNumber) {
            this.PageNumber += PageNumber;
        }

        public void setWriteTimeStamp(String WriteTimeStamp) {
            this.WriteTimeStamp = WriteTimeStamp;
        }

        public void setReadTimeStamp(String ReadTimeStamp) {
            this.ReadTimeStamp = ReadTimeStamp;
        }

        public void printListOfPages() {
            System.out.printf("\n%-5s%-15s%-15s\n", "", "Page Number", "GUID");
            for (int i = 0; i < pages.size(); i++) {
                PagesJson temp = pages.get(i);

                System.out.printf("%-5s%-15s%-15d\n", "", temp.getPageNumber(), temp.getGUID());
            }
            System.out.println("");
        }
        public PagesJson getPG(int x){
            return this.pages.get(x);
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////
    /**
     * This class is for FilesJSon and its setters and getters
     */
    public class FilesJson {
        List<FileJson> metaFile;

        // ArrayList<FileJson> file = new ArrayList<FileJson>();
        public FilesJson() {
            metaFile = new ArrayList<FileJson>();
        }

        // Getter
        public FileJson getFile(int index) {
            return metaFile.get(index);
        }

        public int getSize() {
            return metaFile.size();
        }

        public void addFile(FileJson newFile) {
            metaFile.add(newFile);
        }

        public boolean fileExists(String filename) {
            for (int i = 0; i < metaFile.size(); i++) {
                FileJson temp = metaFile.get(i);

                if (temp.getName().equals(filename)) {
                    return true;
                }
            }
            return false;
        }

        public void deleteFile(String filename) {
            ListIterator<FileJson> listIterator = metaFile.listIterator();

            while (listIterator.hasNext()) {
                FileJson temp = listIterator.next();

                if (temp.getName().equals(filename))
                    listIterator.remove();
            }
        }

        public void clear() {
            metaFile.clear();
        }

        public void printListOfFiles() {
            System.out.printf("\n%-15s%-15s\n", "Filename", "Number of Pages");
            for (int i = 0; i < metaFile.size(); i++) {
                FileJson temp = metaFile.get(i);

                System.out.printf("%-15s%-15d\n", temp.getName(), temp.getNumOfPages());

                if (temp.getNumOfPages() > 0)
                    temp.printListOfPages();
            }
            System.out.println("");
        }
    }

    /////////////////////////////////////////////////////////////

    int port;
    Chord chord;
    FilesJson MetaData;

    private long md5(String objectName) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.reset();
            m.update(objectName.getBytes());
            BigInteger bigInt = new BigInteger(1, m.digest());
            return Math.abs(bigInt.longValue());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();

        }
        return 0;
    }

    public DFS(int port) throws Exception {

        this.port = port;
        long guid = md5("" + port);
        this.MetaData = new FilesJson();
        chord = new Chord(port, guid);
        Files.createDirectories(Paths.get(guid + "/repository"));
        Files.createDirectories(Paths.get(guid + "/tmp"));
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                chord.leave();
            }
        });
    }

    public void join(String Ip, int port) throws Exception {
        chord.joinRing(Ip, port);
        chord.print();
    }

    /**
     * print the status of the peer in the chord
     */
    public void print() throws Exception {
        chord.print();
    }

    public FilesJson readMetaData() throws Exception {
        // JsonParser jsonParser = null;
        FilesJson filesJson = null;
        try {
            Gson gson = new Gson();
            long guid = md5("Metadata");
            ChordMessageInterface peer = chord.locateSuccessor(guid);
            RemoteInputFileStream metadataraw = peer.get(guid);
            metadataraw.connect();
            Scanner scan = new Scanner(metadataraw);
            scan.useDelimiter("\\A");
            String strMetaData = scan.next();
            filesJson = gson.fromJson(strMetaData, FilesJson.class);

        } catch (NoSuchElementException ex) {
            filesJson = new FilesJson();
        }
        return filesJson;
    }

    // data is in the cloud after this method
    public void writeMetaData(FilesJson json) throws Exception {
        // JsonParser jsonParser = null;
        long guid = md5("Metadata");
        ChordMessageInterface peer = chord.locateSuccessor(guid);
        Gson gson = new Gson();
        peer.put(guid, gson.toJson(json));
    }

    public void mv(String oldName, String newName) throws Exception {
        FilesJson md = this.readMetaData();
        boolean fileExists = false;
        for (int i = 0; i < md.getSize(); i++) {
            if (md.getFile(i).getName().equalsIgnoreCase(oldName)) {
                md.getFile(i).setName(newName);
                String timeWriteStamp = LocalDateTime.now().toString();
                md.getFile(i).setWriteTimeStamp(timeWriteStamp);
                fileExists = true;
            }
        }
        if (fileExists)
            System.out.println(oldName + " has been renamed to " + newName + ".\n");
        else
            System.out.println("File not found.\n");
    }

    ///// Done confirmed////
    /**
     * list lists all files contained within the current connected port
     *
     * @return
     * @throws Exception
     */
    public String ls() throws Exception {

        FilesJson md = this.readMetaData();
        String listOfFiles = "";
        for (int i = 0; i < md.getSize(); i++) {
            String FileName = md.getFile(i).getName();
            String maxpg = Integer.toString(md.getFile(i).getMaxPageNumber());
            listOfFiles += " " + FileName + " " + maxpg + "\n";
        }
        System.out.println(listOfFiles);
        return listOfFiles;
    }

    public void touch(String fileName) throws Exception {
        // Write Metadata
        FileJson MetaFile = new FileJson();
        FilesJson md = this.readMetaData();
        MetaFile.setName(fileName);
        md.addFile(MetaFile);
        writeMetaData(md);

    }

    public void delete() throws Exception {
        FilesJson md = readMetaData();
        Scanner scan = new Scanner(System.in);
        int indexOfFile = scan.nextInt();
        FileJson file1 = md.getFile(indexOfFile);
        if (file1.getNumOfPages() > 0) {
            for (int i = 0; i < file1.getNumOfPages(); i++) {
                PagesJson page = file1.pages.get(i);
                long guid = page.getGUID();
                ChordMessageInterface peer = chord.locateSuccessor(guid);
                peer.delete(guid);
            }
        }
        writeMetaData(md);

    }

    public List<String> search(String keyword) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        Gson gson = new Gson();
        List<String> result = new ArrayList<>();
            FilesJson met = this.readMetaData();

             for (int i = 0; i < met.getSize(); i++) {
                FileJson temp = met.getFile(i);

                for(int x=0;x<temp.getNumOfPages();x++){
                    PagesJson n=temp.getPG(x);

                    long pageGuid=n.getGUID();
                    ChordMessageInterface peer=chord.locateSuccessor(pageGuid);

                /*    executor.execute(new Runnable() {
                        public void run() {
                            try {
                                //System.out.printf(peer.search(keyword, pageGuid).toString()+"\n");
                                //result=peer.search((String) keyword, pageGuid).toString();
                                String results = gson.toJson(peer.search(keyword, pageGuid), SongValues.class);
                                results=peer.search(keyword, pageGuid);
                                
                                
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                    });
*/
                   PageSearchRunnable pagesearchthread=new PageSearchRunnable(peer,pageGuid,keyword,result);
                   executor.execute(pagesearchthread);
                }
             
            }
            
            while(!executor.isTerminated()){
             return result;
            }

            executor.shutdown();
    return result;
       
    }

    /**
     * Reads from the actual pages in chord
     *
     * @param fileName   Name of file being read
     * @param pageNumber Index of page being read
     * @return the data contained in the page
     * @throws Exception
     */
    public RemoteInputFileStream read(String fileName, int pageNumber) throws Exception {
        pageNumber--;
        RemoteInputFileStream InputStream = null;
        FilesJson md = readMetaData();
        for (int i = 0; i < md.getSize(); i++) {
            if (md.getFile(i).getName().equalsIgnoreCase(fileName)) {
                ArrayList<PagesJson> pagesList = md.getFile(i).getPages();
                for (int k = 0; k < pagesList.size(); k++) {
                    if (k == pageNumber) {
                        PagesJson pageToRead = pagesList.get(k);
                        String timeOfRead = LocalDateTime.now().toString();
                        pageToRead.setReadTimeStamp(timeOfRead);
                        md.getFile(i).setReadTimeStamp(timeOfRead);
                        Long pageGUID = pageToRead.guid;
                        ChordMessageInterface peer = chord.locateSuccessor(pageGUID);
                        InputStream = peer.get(pageGUID);
                    }
                }
                writeMetaData(md);
            }
        }
        return InputStream;
    }

    

    /**
     * tail - to read from the last page
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public RemoteInputFileStream tail(String filename) throws Exception {
        RemoteInputFileStream tail = null;
        for (int i = 0; i < MetaData.getSize(); i++) {
            if (MetaData.getFile(i).getName().equalsIgnoreCase(filename)) {
                ArrayList<PagesJson> pagesList = MetaData.getFile(i).getPages();
                int last = pagesList.size() - 1;
                PagesJson pageToRead = pagesList.get(last);
                String timeOfRead = LocalDateTime.now().toString();
                pageToRead.setReadTimeStamp(timeOfRead);
                MetaData.getFile(i).setReadTimeStamp(timeOfRead);
                Long pageGUID = md5(filename + pageToRead.getCreateTimeStamp());
                ChordMessageInterface peer = chord.locateSuccessor(pageGUID);
                tail = peer.get(pageGUID);
                writeMetaData(MetaData);
            }
        }
        return tail;
    }

    /**
     * to read from the first page
     *
     * @param filename
     * @return
     * @throws Exception
     */
    public RemoteInputFileStream head(String filename) throws Exception {
        RemoteInputFileStream head = null;
        for (int i = 0; i < MetaData.getSize(); i++) {
            if (MetaData.getFile(i).getName().equalsIgnoreCase(filename)) {
                ArrayList<PagesJson> pagesList = MetaData.getFile(i).getPages();
                int first = 0;
                PagesJson pageToRead = pagesList.get(first);
                String timeOfRead = LocalDateTime.now().toString();
                pageToRead.setReadTimeStamp(timeOfRead);
                MetaData.getFile(i).setReadTimeStamp(timeOfRead);
                Long pageGUID = md5(filename + pageToRead.getCreateTimeStamp());
                ChordMessageInterface peer = chord.locateSuccessor(pageGUID);
                head = peer.get(pageGUID);
                writeMetaData(MetaData);
            }
        }
        return head;
    }

    /**
     * Adds the specified data to the end of a page/file
     *
     * @param filename name of file
     * @param data     data being passed in
     * @throws Exception needed for read and write metadata
     */

    public void append(String filename, RemoteInputFileStream data) throws Exception {
        // Let guid be the last page in Metadata.filename
        // ChordMessageInterface peer = chord.locateSuccessor(guid);
        // peer.put(guid, data);
        // Write Metadata

        FilesJson md = this.readMetaData();
        for (int i = 0; i < md.getSize(); i++) {
            if (md.getFile(i).getName().equalsIgnoreCase(filename)) {
                Long sizeOfFile = (long) data.available();
                String timeOfAppend = LocalDateTime.now().toString();
                md.getFile(i).setWriteTimeStamp(timeOfAppend);
                md.getFile(i).addPageNumber(1);
                md.getFile(i).addSize(sizeOfFile);
                String objectName = filename + LocalDateTime.now();
                Long guid = md5(objectName);
                ChordMessageInterface peer = chord.locateSuccessor(guid);
                peer.put(guid, data);
                md.getFile(i).addPage(guid, sizeOfFile, timeOfAppend, "0", "0", 0);

            }
        }
        writeMetaData(md);

    }

}
