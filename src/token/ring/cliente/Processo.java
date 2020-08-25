package token.ring.cliente;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Leonardo Steinke
 */
public class Processo {

    private String TOKEN = null;
    private int PORTA;
    private int PORTAPROXIMO;
    private Socket socket;

    public Processo() {
    }

    public void realizaProcesso() throws InterruptedException {
        while (TOKEN == null) {
            System.out.println("Aguardando TOKEN");
            Thread.sleep(1000);
        }
        System.out.println("Realizando processo");
    }

    public void releaseToken() throws IOException {
        socket = new Socket("localhost", PORTAPROXIMO);
        ObjectOutputStream saida = new ObjectOutputStream(socket.getOutputStream());
        saida.writeChars(TOKEN);
        saida.flush();
        socket.close();
        this.TOKEN = null;

    }

    public String getToken() {
        return TOKEN;
    }

    public void setToken(String Token) {
        this.TOKEN = Token;
    }

}
