import java.util.*;

public class Entangle {
    public Piece Controller;
    public Piece Target;
    public Dictionary<int[], Integer[]> MovementRelations = new Hashtable<>(); // -1, -1 means kill target

    Entangle(Piece Father, Piece Submissive, ArrayList<int[]> SuperPos, ArrayList<Integer[]> SubClass) {
        Controller = Father;
        Target = Submissive;

        MovementRelations.put(SuperPos.get(0), SubClass.get(0));
        MovementRelations.put(SuperPos.get(1), SubClass.get(1));
    }

    public boolean MakeDecision() {
        for (int x = 0; x < Controller.Positions.size(); x++) {
            try {
                Target.Positions.clear();
                Target.Positions.add(MovementRelations.get(Controller.Positions.get(x)));
                if (Arrays.equals(Target.Positions.get(0), new Integer[]{-1, -1})) {
                    return false;
                }
            } catch (Exception ignore) {

            }
        }
        return true;
    }
}
