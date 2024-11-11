import javax.swing.*;
import java.util.ArrayList;

public class SuperPiece {
    public JButton OGButton;
    JButton SuperPos1;
    JButton SuperPos2;

    SuperPiece(JButton OG, JButton Super1, JButton Super2) {
        OGButton = OG;
        SuperPos1 = Super1;
        SuperPos2 = Super2;
    }

    public ArrayList<Integer[]> Challange(ArrayList<Integer[]> Pos) {
        int Random = (int) Math.round(Math.random() * 2);
        if (Random == 1) {
            OGButton.setBounds(SuperPos2.getBounds());
        } else {
            OGButton.setBounds(SuperPos1.getBounds());
        }
        Pos.clear();
        Pos.add(new Integer[]{OGButton.getX(), OGButton.getY()});
        OGButton.setVisible(true);
        return Pos;
    }

    public JButton OtherButton(JButton NotOne) {
        if (NotOne == SuperPos1) {
            return SuperPos2;
        }
        return SuperPos1;
    }

    public boolean IsWhite(Piece[] AllPieces, JButton[] White, JButton[] Black) {
        for (int x = 0; x < AllPieces.length; x++) {
            if (x < 16) {
                if (White[AllPieces[x].ButtonNumber] == OGButton) {
                    return true;
                }
            } else {
                if (Black[AllPieces[x].ButtonNumber] == OGButton) {
                    return false;
                }
            }
        }
        throw new RuntimeException("Cannot Find Current Piece");
    }
}
