import java.util.Arrays;

public class FirestarterUviuViuViuViu {

    public static void main(String... args) {
        System.out.println(args.length);
        String inParams = Arrays.toString(args);


        // Валидация
        if (args.length == 0 ||
                (inParams.contains("-server") && inParams.contains("-client"))
        ) {
            System.out.println("Error: Shit params detected!\nYour shit:\n\t" + inParams);
            System.exit(-1);
        }



        if (inParams.contains("-server")) {
            new TCPServer();
        }
        else if (inParams.contains("-client")) {
            new TCPClient();
        }
    }
}
