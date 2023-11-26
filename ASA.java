import java.util.List;
import java.util.Stack;

public class ASA implements Parser {
    private int i = 0;
    private boolean hayErrores = false;
    private final List<Token> tokens;

    public ASA(List<Token> tokens) {
        this.tokens = tokens;
    }

    @Override
    public boolean parse() {
        Stack<Integer> pila = new Stack<>();
        pila.push(0);


        int tokensC[] = new int[100000000];
        for(int j = 0; j < tokens.size(); j++){
            switch (tokens.get(j).tipo) {
                case SELECT:
                    tokensC[j] = 0; 
                    break;
                case DISTINCT:
                    tokensC[j] = 1; 
                    break;
                case FROM:
                    tokensC[j] = 2; 
                    break;
                case IDENTIFICADOR:
                    tokensC[j] = 3; 
                    break;
                case ASTERISCO:
                    tokensC[j] = 4; 
                    break;
                case COMA:
                    tokensC[j] = 5; 
                    break;
                case PUNTO:
                    tokensC[j] = 6; 
                    break;
                case EOF:
                    tokensC[j] = 7; 
                    break;
            }
        }

        /*
         * aceptar = 100
         * RX = -X
         */
         //   0       1     2    3 4 5 6 7 8 9  10   11  12   13   14   15  16   17   18
         //select distintc from id * , . $ Q D   P   A   A1   A2   A3   T   T1   T2   T3
        int [][] tabla = {
            {2, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},          //0
            {0, 0, 0, 0, 0, 0, 0, 100, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //1
            {0, 4, 0, 9, 6, 0, 0, 0, 0, 3, 5, 7, 0, 8, 0, 0, 0, 0, 0},          //2
            {0, 0, 17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //3
            {0, 0, 0, 9, 6, 0, 0, 0, 0, 0, 15, 7, 0, 8, 0, 0, 0, 0, 0},         //4
            {0, 0, -3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //5
            {0, 0, -4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //6
            {0, 0, -5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //7
            {0, 0, -8, 0, 0, 10, 0, 0, 0, 0, 0, 0, 16, 0, 0, 0, 0, 0, 0},       //8
            {0, 0, -11, 0, 0, -11, 12, 0, 0, 0, 0, 0, 0, 0, 11, 0, 0, 0, 0},    //9
            {0, 0, 0, 9, 0, 0, 0, 0, 0, 0, 0, 14, 0, 8, 0, 0, 0, 0, 0},         //10
            {0, 0, -9, 0, 0, -9, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //11
            {0, 0, 0, 13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //12
            {0, 0, -10, 0, 0, -10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //13
            {0, 0, -7, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //14
            {0, 0, -2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //15
            {0, 0, -6, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},         //16
            {0, 0, 0, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0, 18, 0},       //17
            {0, 0, 0, 0, 0, 20, 0, -14, 0, 0, 0, 0, 0, 0, 0, 0, 24, 0, 0},      //18
            {0, 0, 0, 23, 0, -17, 0, -17, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 22},    //19
            {0, 0, 0, 19, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 21, 0, 18, 0},       //20
            {0, 0, 0, 0, 0, 0, 0, -13, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //21
            {0, 0, 0, 0, 0, -15, 0, -15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},      //22
            {0, 0, 0, 0, 0, 0, 0, -16, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //23
            {0, 0, 0, 0, 0, 0, 0, -12, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},        //24
            {0, 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}          //25
        };

        int[] reducciones = {4, 2, 1, 1, 1, 2, 2, 0, 2, 2, 0, 2, 2, 0, 2, 1, 0};

        int[] reduccionesSimbolo = {
            //"Q", "D", "D", "P", "P", "A", "A1", "A1", "A2", "A3", "A3", "T", "T1", "T1", "T2", "T3", "T3"
            8,   9,   9,   10,   10,  11,  12,   12,   13,   14,   14,   15,  16,   16,   17,   18,   18};

        int estadoActual;
        int accion;
        int simboloActual;
        simboloActual = tokensC[i];
        int borrar;

        while (true) {
            estadoActual = pila.peek();
            accion = tabla[estadoActual][simboloActual];

            if (accion == 100) {
                break;
            } else if(accion == 0){
                hayErrores = true;
                break;
            } else if (accion > 0) {
                pila.push(accion);
                i++;
                simboloActual = tokensC[i];
            } else if (accion < 0) {
                borrar = reducciones[(-accion)-1];
                simboloActual = reduccionesSimbolo[(-accion)-1];
                i--;
                for (int j = 0; j < borrar; j++)
                    pila.pop();
            }
        }
        if (hayErrores) {
            System.out.println("La sintanxis es incorrecta");
            return false;
        } else {
            System.out.println("La sintaxis es correcta");
            return true;
        }
    }
}
