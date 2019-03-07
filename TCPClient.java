import java.net.*;
import java.io.*;

public class TCPClient {
    public TCPClient() {

        Socket s = null;

        try {
            int serverPort = 6880;
            String ip = "localhost";
            String data = "Hello spider man!";

            s = new Socket(ip, serverPort);
            DataInputStream input = new DataInputStream(s.getInputStream());
            DataOutputStream output = new DataOutputStream(s.getOutputStream());

            //Step 1 send length
            System.out.println("Length" + data.length());
            output.writeInt(data.length());
            //Step 2 send length
            System.out.println("Writing.......");
            output.writeBytes(data); // UTF is a string encoding

            //Step 1 read length
            int nb = input.readInt();
            byte[] digit = new byte[nb];
            //Step 2 read byte
            for (int i = 0; i < nb; i++)
                digit[i] = input.readByte();

            String st = new String(digit);
            System.out.println("Received: " + st);
        } catch (UnknownHostException e) {
            System.out.println("Sock:" + e.getMessage());
        } catch (EOFException e) {
            System.out.println("EOF:" + e.getMessage());
        } catch (IOException e) {
            System.out.println("IO:" + e.getMessage());
        } finally {
            if (s != null)
                try {
                    s.close();
                } catch (IOException e) {/*close failed*/}
        }
    }

    class Connection extends Thread {
        DataInputStream input;
        DataOutputStream output;
        Socket clientSocket;

        public Connection(Socket aClientSocket) {
            try {
                clientSocket = aClientSocket;
                input = new DataInputStream(clientSocket.getInputStream());
                output = new DataOutputStream(clientSocket.getOutputStream());
                this.start();
            } catch (IOException e) {
                System.out.println("Connection:" + e.getMessage());
            }
        }

        public void run() {

            try { // an echo server
                //  String data = input.readUTF();

                FileWriter out = new FileWriter("test.txt");
                BufferedWriter bufWriter = new BufferedWriter(out);

                //Step 1 read length
                int nb = input.readInt();
                System.out.println("Read Length" + nb);
                byte[] digit = new byte[nb];
                //Step 2 read byte
                System.out.println("Writing.......");

                for (int i = 0; i < nb; i++) digit[i] = input.readByte();

                String st = new String(digit);
                bufWriter.append(st);
                bufWriter.close();
                System.out.println("receive from : " +
                        clientSocket.getInetAddress() + ":" +
                        clientSocket.getPort() + " message - " + st);

                //Step 1 send length
                output.writeInt(st.length());
                //Step 2 send length
                output.writeBytes(st); // UTF is a string encoding
                //  output.writeUTF(data);

            } catch (EOFException e) {
                System.out.println("EOF:" + e.getMessage());
            } catch (IOException e) {
                System.out.println("IO:" + e.getMessage());
            } finally {
                try {
                    clientSocket.close();
                } catch (IOException e) {/*close failed*/}
            }

        }
    }
}
