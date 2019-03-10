import java.util.ArrayList;

public class Model {

    final public int CELL_SIZE = 40;

    private int tiles_x;
    private int tiles_y;
    private int numberOfBombs;

    private ArrayList<IView> views = new ArrayList<IView>();


    Model(int x, int y, int numberOfBombs){
        tiles_x = x;
        tiles_y = y;
        this.numberOfBombs = numberOfBombs;
    }

    void notifyObservers(){
        for (IView view : this.views){
            view.updateView();
        }
    }

    void addView(IView view){
        views.add(view);
        view.updateView();
    }


    int getTiles_x(){
        return tiles_x;
    }

    int getTiles_y(){
        return tiles_y;
    }

    int getNumberOfBombs(){
        return numberOfBombs;
    }

    //TODO: MUTATORS
}
