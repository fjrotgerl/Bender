import java.util.Arrays;

/**
 * Created by Kekko on 15/03/2017.
 */
public class Bender {
    String mapa;
    char[][] mapaBidimensional;

    public Bender(String mapa) {
        this.mapa = mapa;
    }

    public String run() {
        StringBuilder sb = new StringBuilder();
        char[][] array = buildArray(this.mapa);
        int[] position = startPosition(array);
        int posX = 0;
        int posY = 0;
        int y2 = 0;
        int x2 = 0;
        boolean south;
        boolean east;
        boolean north;
        boolean west;
        for (int y = position[0]; y < array.length; y++) {
            for (int x = position[1]; x < array[0].length; x++) {
                if (array[position[0]+1][position[1]] == ' ' && y == position[0] && x == position[1] ) {
                    y2 = y+1;
                    sb.append("S");
                    break;
                }
                south = y < array.length-1 && array[y+1][x] == ' ' || y < array.length-1 && array[y+1][x] == '$';
                if (array[y][x] == ' ') {
                    if (south == true) {
                        posX = x;
                        posY = y+1;
                        x--;
                        sb.append("S");
                        y2 = y+1;
                        break;
                    }
                    east = array[y][x+1] == ' ' || array[y][x+1] == '$';
                    if (east == true && south == false) {
                        sb.append("E");
                        posX = x+1;
                        posY = y;
                        x2 = x;
                    }
                    north = array[y2-1][x] == ' ' || array[y2-1][x] == '$';
                    if (y2 < array.length-1 && north == true && south == false && east == false) {
                        y2--;
                        x--;
                        posY = y2;
                        posX = x+1;
                        sb.append("N");
                    }
                    west = array[y][x-1] == ' ' || array[y][x-1] == '$';
                    if (west == true && north == false && south == false && east == false) {
                        x2--;
                        x--;
                        sb.append("W");
                        posX = x2+1;
                        posY = y2;
                    }
                } if (array[posY][posX] == '$') {
                    break;
                }
            } if (array[posY][posX] == '$') {
                System.out.println("Has guanyat!");
                break;
            }
        }
        return sb.toString();
    }

    public char[][] buildArray(String mapa) {
        String[] mapaSeparat = mapa.split("\n");
        int X = mapaSeparat[0].length();
        int Y = mapa.split("\n").length;
        this.mapaBidimensional = new char[Y][X];
        int i = 0;
        for (int y = 0; y < Y; y++) {
            for (int x = 0; x < X; x++) {
                this.mapaBidimensional[y][x] = mapaSeparat[y].charAt(i++);
            }
            i = 0;
        }
        return this.mapaBidimensional;
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
}
