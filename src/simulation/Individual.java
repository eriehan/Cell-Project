package simulation;

public abstract class Individual {
    private GridInfo myGridInfo;
    private boolean moved = false;
    private boolean dead = false;

    public Individual(GridInfo gridInfo) {
        myGridInfo = gridInfo;
    }

    public abstract void move();

    public void setMyGridInfo(GridInfo gridInfo) {myGridInfo = gridInfo;}

    public GridInfo getMyGridInfo() {return myGridInfo;}

    public boolean hasMoved() {return moved;}

    public void setMoved(boolean bool) {moved = bool;}

    public void setDead(boolean bool) {dead = bool;}

    public boolean isDead() {return dead;}
}
