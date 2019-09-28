package simulation;

public abstract class Individual {
    private GridInfo myGridInfo;
    private boolean moved = false;

    public Individual(GridInfo gridInfo) {
        myGridInfo = gridInfo;
    }

    public abstract void move();

    public void setMyGridInfo(GridInfo gridInfo) {myGridInfo = gridInfo;}

    public GridInfo getMyGridInfo() {return myGridInfo;}

    public boolean hasMoved() {return moved;}

    public void setMoved(boolean bool) {moved = bool;}
}
