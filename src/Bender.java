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

            if (mapaBidimensional[actual[0]][actual[1]] == ' ' || mapaBidimensional[actual[0]][actual[1]] == 'X') {
                collision = false;
                position = actual;
                sb.append(movement[cont]);
                continue;
            } if (mapaBidimensional[actual[0]][actual[1]] == '#') {
                if (collision == true) { cont++; }
                else {
                    collision = true;
                    cont = 0;
                }
                continue;
            } if (mapaBidimensional[actual[0]][actual[1]] == 'T') {
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
            } if (mapaBidimensional[actual[0]][actual[1]] == 'I') {
                if (inverter == true) { inverter = false; }
                else { inverter = true; }
                collision = false;
                position = actual;
                sb.append(movement[cont]);
                cont = 0;
                continue;
            } if (mapaBidimensional[actual[0]][actual[1]] == '$') {
                sb.append(movement[cont]);
                return sb.toString();
            }
        }
    }

    public char[][] buildArray(String mapa) {
        char[][] mapaBidimensional;
        String[] mapaSeparat = mapa.split("\n");
        int X = mapaSeparat[0].length();
        int Y = mapa.split("\n").length;
        mapaBidimensional = new char[Y][X];
        int i = 0;
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                mapaBidimensional[y][x] = mapaSeparat[y].charAt(i++);
            }
            i = 0;
        }
        return mapaBidimensional;
    }

    public int[] startPosition(char[][] map) {
        int[] position = new int[2];
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
        int[] position = new int[4];
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                if (map[y][x] == 'T' && position[0] == 0) {
                    position[0] = y;
                    position[1] = x;
                } else if (map[y][x] == 'T' && position[2] == 0) {
                    position[2] = y;
                    position[3] = x;
                }
            }
        }
        return position;
    }

    public int[] movePosition(int[] position, char direction) {
        position = Arrays.copyOf(position,2);
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