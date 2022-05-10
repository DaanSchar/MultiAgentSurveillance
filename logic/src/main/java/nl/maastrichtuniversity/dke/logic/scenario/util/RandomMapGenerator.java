package nl.maastrichtuniversity.dke.logic.scenario.util;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Environment;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TeleportTile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.Tile;
import nl.maastrichtuniversity.dke.logic.scenario.environment.TileType;
import java.util.Random;

public class RandomMapGenerator {
    private final int width=120;
    private final int height=80;
    private Tile[][] tileMap;


    public Environment build(){
        this.tileMap = new Tile[width][height];
        createBorder(); //WALL BORDER
        createRandomArea(18,8,TileType.SPAWN_INTRUDERS); //SPAWN
        createRandomArea(18,8,TileType.SPAWN_GUARDS); //SPAWN
        createRandomArea(3,3,TileType.TARGET); //TARGET
        createTeleport(3,3,0); //TELEPORT
        createTeleport(3,3,0); //TELEPORT
        createRandomArea(10,20,TileType.SHADED); //SHADED
        createRandomArea(1,1,TileType.SENTRY); //SENTRY
        createRandomArea(1,1,TileType.SENTRY); //SENTRY
        createRandomArea(1,1,TileType.SENTRY); //SENTRY
        createBigHouse(18,18); //BIG HOUSE
        createBigHouse(14,14); //BIG HOUSE
        createSmallHouse(6,6); //SMALL HOUSE
        createSmallHouse(8,8); //SMALL HOUSE
        createRandomArea(1,20,TileType.WALL); //RANDOM WALL
        createRandomArea(20,1,TileType.WALL); //RANDOM WALL
        createRandomArea(1,10,TileType.WALL); //RANDOM WALL
        createRandomArea(10,1,TileType.WALL); //RANDOM WALL
        createRandomArea(50,1,TileType.WALL); //RANDOM WALL
        createRandomArea(1,50,TileType.WALL); //RANDOM WALL
        fillInEmptyTiles();
        return new Environment(this.width, this.height, this.tileMap);
    }
    private void fillInEmptyTiles() {
        for (int x = 0; x < width; x++)
            for (int y = 0; y < height; y++)
                if (tileMap[x][y] == null)
                    tileMap[x][y] = new Tile(new Position(x, y), TileType.EMPTY);
    }
    private void createRandomArea(int maxWidth,int maxHeight,TileType type){
        Random rand=new Random();
        int randomX1 = rand.nextInt(width-maxWidth);
        int randomY1 = rand.nextInt(height-maxHeight);
        if (freeArea(randomX1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight))){
            for (int x = randomX1; x < (randomX1+maxWidth); x++){
                for (int y = randomY1; y < (randomY1+maxHeight); y++){
                    tileMap[x][y] = new Tile(new Position(x, y), type);
                }
            }
        }
        else{createRandomArea(maxWidth,maxHeight,type);}

    }
    private void createArea(int x1, int y1, int x2, int y2, TileType type){
        for (int x = x1; x < x2; x++){
            for (int y = y1; y < y2; y++){
                tileMap[x][y] = new Tile(new Position(x, y), type);
            }
        }
    }
    private void createBorder(){
        createArea(0,0,1,height,TileType.WALL);
        createArea(0,height-1,width,height,TileType.WALL);
        createArea(width-1,0,width,height,TileType.WALL);
        createArea(0,0,width,1,TileType.WALL);
    }

    private void createTeleport(int maxWidth,int maxHeight,int rotation){
        Random rand=new Random();
        int randomX1 = rand.nextInt(width-maxWidth);
        int randomY1 = rand.nextInt(height-maxHeight);
        if (freeArea(randomX1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight))){
            int[] target =createTeleportDis();
            for (int x = randomX1; x < (randomX1+maxWidth); x++){
                for (int y = randomY1; y < (randomY1+maxHeight); y++){
                    tileMap[x][y] = new TeleportTile(new Position(x, y),target[0],target[1], rotation);
                }
            }
        }
        else{createTeleport(maxWidth,maxHeight,rotation);}

    }
    public int[] createTeleportDis(){
        int[] target = new int[2];
        Random rand=new Random();
        int randomX1 = rand.nextInt(width-1);
        int randomY1 = rand.nextInt(height-1);
        target[0]=randomX1;
        target[1]=randomY1;
        if (freeArea(randomX1,randomY1,(1+randomX1),(randomY1+1))){
            for (int x = randomX1; x < (randomX1+1); x++){
                for (int y = randomY1; y < (randomY1+1); y++){
                    tileMap[x][y] = new Tile(new Position(x, y), TileType.DESTINATION_TELEPORT);
                }
            }
        }
        else{createTeleportDis();}

        return target;
    }

    private void createBigHouse(int maxWidth,int maxHeight){
        Random rand=new Random();
        int randomX1 = rand.nextInt(width-maxWidth);
        int randomY1 = rand.nextInt(height-maxHeight);
        if (freeArea(randomX1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight))){
            createArea(randomX1,randomY1,randomX1+1,randomY1+maxHeight,TileType.WALL);
            createArea(randomX1,(randomY1+maxHeight)-1,(maxWidth+randomX1),(randomY1+maxHeight),TileType.WALL);
            createArea((randomX1+maxHeight)-1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight),TileType.WALL);
            createArea(randomX1,randomY1,(randomX1+maxHeight),randomY1+1,TileType.WALL);
            createDoor(randomX1+(maxWidth/2),randomY1);
            createWindow(randomX1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight));

        }
        else{createBigHouse(maxWidth,maxHeight);}

    }
    private void createSmallHouse(int maxWidth,int maxHeight){
        Random rand=new Random();
        int randomX1 = rand.nextInt(width-maxWidth);
        int randomY1 = rand.nextInt(height-maxHeight);
        if (freeArea(randomX1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight))){
            createArea(randomX1,randomY1,randomX1+1,randomY1+maxHeight,TileType.WALL);
            createArea(randomX1,(randomY1+maxHeight)-1,(maxWidth+randomX1),(randomY1+maxHeight),TileType.WALL);
            createArea((randomX1+maxHeight)-1,randomY1,(maxWidth+randomX1),(randomY1+maxHeight),TileType.WALL);
            createArea(randomX1,randomY1,(randomX1+maxHeight),randomY1+1,TileType.WALL);
            createDoor(randomX1+(maxWidth/2),randomY1);
        }
        else{createSmallHouse(maxWidth,maxHeight);}
    }
    private void createRandomWalls(){

    }
    private void createDoor(int x,int y){
        tileMap[x][y]=new Tile(new Position(x, y), TileType.DOOR);
        tileMap[x-1][y]=new Tile(new Position(x-1, y), TileType.DOOR);
    }
    private void createWindow(int x1,int y1,int x2,int y2){
        int window1y = y1+5;

        tileMap[x1][window1y]=new Tile(new Position(x1, window1y), TileType.WINDOW);
        tileMap[x1][window1y+5]=new Tile(new Position(x1, window1y+5), TileType.WINDOW);
        tileMap[x2-1][window1y]=new Tile(new Position(x2-1, window1y), TileType.WINDOW);
        tileMap[x2-1][window1y+5]=new Tile(new Position(x2-1, window1y+5), TileType.WINDOW);

    }

    private boolean freeArea(int x1,int y1,int x2,int y2){
        for (int x = x1; x <= x2; x++){
            for (int y = y1; y <= y2; y++){
                if((tileMap[x][y] != null)){
                    return false;
                }
            }
        }
        return true;
    }
}
