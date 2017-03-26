import java.util.Arrays;

/**
 * Created by Kekko on 15/03/2017.
 */
public class Bender {
    String mapa;

    // En aquesta funció cridam al String actual que anem a utilitzar
    public Bender(String mapa) {
        this.mapa = mapa;
    }

    // En la funció run ens encarregam bàsicament de mirar el caràcter que tenim en la posició actual
    public String run() {
        // Cream un StringBuilder per ficar els caràcters corresponents a la seva direcció
        StringBuilder sb = new StringBuilder();
        // Cream dos arrays
        // El primer array serà de 4 posicions i indicarà la direcció que hem de seguir
        char[] normal = new char[] {'S','E','N','W'};
        // El segon array també serà de 4 posicions
        // Aquest indicarà la direcció que hem de seguir però a la inversa
        char[] inverted = new char[] {'N','W','S','E'};
        // Aquest array emmagatzemarà la direcció correcta segons un booleà
        char[] movement;
        // Construim un array bidimensional del mapa
        // Utilitzam un String
        char[][] mapaBidimensional = buildArray(this.mapa);
        // Emmagatzemam en un array d'integers de dos posicions la posició inicial (la 'X')
        int[] position = startPosition(mapaBidimensional);
        // Emmagatzemam en un array de 4 posicions les posicions de les dos 'T'
        int[] tPosition = transportPosition(mapaBidimensional);
        // Cream un comptador que indicarà quan ha de canviar a la següent posició
        int cont = 0;
        // Cream 2 booleans
        // collision s'encarrega de tornar el comptador a 0
        // o també de aumentar el comptador
        boolean collision = false;
        // inverter s'encarrega de assignar al array movement les direccions correctes
        // depenent de si hi ha una 'I' o no
        boolean inverter = false;

        while (true) {
            // Primer de tot en el bucle assignam les direccions correctes segons el boolea inverter
            if (inverter == true) { movement = inverted; }
            else { movement = normal; }
            // Cream un array de dos posicions que s'encarregarà de moure per el mapa
            int[] actual = movePosition(position, movement[cont]);

            // Feim un condicional que miri si la posició actual és un espai o una 'X'
            // Si la compleix feim que la vaiable collision es torni a false (per defecte),
            // a més de que es mogui i afegim la lletra corresponent al StringBuilder
            if (mapaBidimensional[actual[0]][actual[1]] == ' ' || mapaBidimensional[actual[0]][actual[1]] == 'X') {
                collision = false;
                position = actual;
                sb.append(movement[cont]);
                continue;
            }
            // En aquest condicional miram si la posició actual conté el caràcter '#'
            // D'aquesta manera miram d'incrementar el comptador depenent del valor de collision
            if (mapaBidimensional[actual[0]][actual[1]] == '#') {
                if (collision == true) { cont++; }
                else {
                    collision = true;
                    cont = 0;
                }
                continue;
            }
            // En aquest condicional miram si la posició conté el caràcter 'T'
            if (mapaBidimensional[actual[0]][actual[1]] == 'T') {
                // Aquest condicional el que fa és mirar la posició de la 'T'
                // Si les dos primeres posicions són iguals a les dos primeres posicions de tPosition
                // escogirem les dos següents
                // Si nó escogirem les dos primeres de tPosition
                // D'aquesta manera tindrem la posició de la 'T' correcta
                if (actual[0] == tPosition[0] && actual[1] == tPosition[1] ) {
                    position[0] = tPosition[2];
                    position[1] = tPosition[3];
                } else {
                    position[0] = tPosition[0];
                    position[1] = tPosition[1];
                }
                collision = false;
                sb.append(movement[cont]);
                continue;
            }
            // Si la posició actual és una 'I' modificam la variable inverter
            // d'aquesta manera s'asignaran unes posicions diferents al array movement
            if (mapaBidimensional[actual[0]][actual[1]] == 'I') {
                if (inverter == true) { inverter = false; }
                else { inverter = true; }
                collision = false;
                position = actual;
                sb.append(movement[cont]);
                cont = 0;
                continue;
            }
            // Si la posició actual és un '$' retornam el String
            if (mapaBidimensional[actual[0]][actual[1]] == '$') {
                sb.append(movement[cont]);
                return sb.toString();
            }
        }
    }

    public char[][] buildArray(String mapa) {
        // Cream un array bidimensional
        char[][] mapaBidimensional;
        // Feim un array del String que estigui dividit per '\n'
        String[] mapaSeparat = mapa.split("\n");
        // Declaram dos variables
        // X i Y indican la longitud del array
        int X = mapaSeparat[0].length();
        int Y = mapa.split("\n").length;
        // Li assignam les dimensions corresponents al array
        mapaBidimensional = new char[Y][X];
        // Cream un comptador
        int i = 0;
        // Amb els dos for's recorrem tot l'array
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                // Assignam cada lletra del String al array bidimensional
                mapaBidimensional[y][x] = mapaSeparat[y].charAt(i++);
            }
            // Reseteam el comptador
            i = 0;
        }
        return mapaBidimensional;
    }

    public int[] startPosition(char[][] map) {
        // Cream un array de dos posicions per emmagatzemar les posicions inicials
        int[] position = new int[2];
        // Cercam per tot l'array la lletra 'X' i emmagatzemam la seva posició
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'X') {
                    position[0] = y;
                    position[1] = x;
                }
            }
        }
        return position;
    }

    private static int[] transportPosition(char[][] map) {
        // Cream un array de posicions per a les dos 'T'
        int[] position = new int[4];
        // Cercam per tot l'array les dos 'T' i les emmagatzemam
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                // Si position[0] és '0' vol dir que no se l'hi ha assignat encara res
                // D'aquesta manera no es repetirà la posició de la 'T'
                // I tindrem la correcta
                if (map[y][x] == 'T' && position[0] == 0) {
                    position[0] = y;
                    position[1] = x;
                }
                // El mateix però amb position[2]
                else if (map[y][x] == 'T' && position[2] == 0) {
                    position[2] = y;
                    position[3] = x;
                }
            }
        }
        return position;
    }

    public int[] movePosition(int[] position, char direction) {
        // Copiam l'array position de 2 posicions
        position = Arrays.copyOf(position,2);
        // Ara miram el que ha de fer cada lletra
        // Per exemple si és una 'S' anirem cap avall (incrementam position[0])
        // Així amb totes les lletres i les direccions corresponents
        if (direction == 'S') {
            position[0]++;
            return position;
        } else if (direction == 'E') {
            position[1]++;
            return position;
        } else if (direction == 'N') {
            position[0]--;
            return position;
        } else if (direction == 'W') {
            position[1]--;
            return position;
        }
        return null;
    }
}