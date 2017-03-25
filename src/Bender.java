import java.util.Arrays;

/**
 * Created by Kekko on 15/03/2017.
 */
public class Bender {
    String mapa;

    public Bender(String mapa) {
        this.mapa = mapa;
    }

    public String run() {
        StringBuilder sb = new StringBuilder();
        char[] normal = new char[] {'S','E','N','W'};
        char[] inverted = new char[] {'N','W','S','E'};
        char[] movement;
        char[][] mapaBidimensional = buildArray(this.mapa);
        int[] position = startPosition(mapaBidimensional);
        int[] tPosition = transportPosition(mapaBidimensional);
        int cont = 0;
        boolean perimetro = false;
        boolean inverter = false;

        while (true) {
            if (inverter) { movement = inverted; }
            else { movement = normal; }

            int[] aux = movePosition(position, movement[cont]);
            if (mapaBidimensional[aux[0]][aux[1]] == ' ' || mapaBidimensional[aux[0]][aux[1]] == 'X') {
                position = aux;
                perimetro = false;
                sb.append(movement[cont]);
                continue;
            } if (mapaBidimensional[aux[0]][aux[1]] == '#') {
                if (perimetro) { cont++; }
                else {
                    cont = 0;
                    perimetro = true;
                }
                continue;
            } if (mapaBidimensional[aux[0]][aux[1]] == 'T') {
                if (aux[0] == tPosition[0] && aux[1] == tPosition[1] ) {
                    position[0] = tPosition[2];
                    position[1] = tPosition[3];
                } else {
                    position[0] = tPosition[0];
                    position[1] = tPosition[1];
                }
                perimetro = false;
                sb.append(movement[cont]);
                continue;
            } if (mapaBidimensional[aux[0]][aux[1]] == 'I') {
                if (inverter) { inverter = false; }
                else { inverter = true; }
                perimetro = false;
                position = aux;
                sb.append(movement[cont]);
                cont = 0;
                continue;
            }

            if (mapaBidimensional[aux[0]][aux[1]] == '$') {
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