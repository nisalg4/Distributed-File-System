import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class CommandLine {
    DFS dfs;

    public CommandLine(int p, int portToJoin) throws Exception {
        dfs = new DFS(p);

        // User interface:
        // join, ls, touch, delete, read, tail, head, append, move
        if (portToJoin > 0) {
            System.out.println("Joining " + portToJoin);
            dfs.join("127.0.0.1", portToJoin);
        }

        BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
        String line = buffer.readLine();
        while (!line.equals("quit")) {
            String[] result = line.split("\\s");
            if (result[0].equals("join") && result.length > 1) {
                dfs.join("127.0.0.1", Integer.parseInt(result[1]));
            }
            if (result[0].equals("delete")) {
                dfs.delete();
            }
            if (result[0].equals("ls")) {
                dfs.ls();
            }
            if (result[0].equals("search")) {
                dfs.search(result[1]);
            }
            if (result[0].equals("print")) {
                dfs.print();
            }
            if (result[0].equals("touch")) {
                dfs.touch(result[1]);
                System.out.print("New File Created\n");
            }
            if (result[0].equals("mv")) {
                dfs.mv(result[1], result[2]);
            }
            if (result[0].equals("append")) {
                RemoteInputFileStream input = new RemoteInputFileStream(result[2]);
                dfs.append(result[1], input);
                System.out.println("Page added\n");
            }
            if (result[0].equals("read")) {
                int pageNumber = Integer.parseInt(result[2]);
                int i;
                RemoteInputFileStream r = dfs.read(result[1], pageNumber);
                r.connect();
                while ((i = r.read()) != -1) {
                    System.out.print((char) i);
                }
                System.out.println();
                System.out.println("page read");
            }
            if (result[0].equals("head")) {
                dfs.head(result[1]);
            }
            if (result[0].equals("tail")) {
                dfs.tail(result[1]);
            }
            line = buffer.readLine();
        }
    }

  static public void main(String args[]) throws Exception
    {

        RemoteInputFileStream in = new RemoteInputFileStream("/Users/nisalgamage/Desktop/DFS/music.json", false);
        in.connect();
        Reader targetReader = new InputStreamReader(in);  
        if (args.length < 1) {
            
            throw new IllegalArgumentException("Parameter: <port> <portToJoin>");
        }
        if (args.length > 1) {
            CommandLine dfsCommand = new CommandLine(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            //dfsCommand.dfs(targetReader, Integer.parseInt(args[1])); 
        }else {
            CommandLine dfsCommand = new CommandLine(Integer.parseInt(args[0]), 0);
        }
       
    }
}
