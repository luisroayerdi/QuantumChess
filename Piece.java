import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;


public class Piece {
    /*
    0 - Pawn
    1 - Bishop
    2 - Knight
    3 - Rook
    4 - Queen
    5 - King
     */
    public boolean IsWhite;
    public int ButtonNumber;
    public int Piece;
    public ArrayList<Integer[]> Positions = new ArrayList<>(); // If 2 or more, In SUPER
    public ArrayList<Integer[]> Entangle = new ArrayList<>();
    boolean FirstMove = true;

    Piece (int Num, boolean IsWhite) {
        this.IsWhite = IsWhite;
        ButtonNumber = Num;
        switch (Num) {
            case 0,1,2,3,4,5,6,7 : {
                Piece = 0;
                break;
            }
            case 8,15: {
                Piece = 3;
                break;
            }
            case 9,14: {
                Piece = 2;
                break;
            }
            case 10,13: {
                Piece = 1;
                break;
            }
            case 11: {
                Piece = 4;
                break;
            }
            case 12:{
                Piece = 5;
                break;
            }
        }
    }

    public String name (){
        return switch (Piece) {
            case 0 -> "P";
            case 1 -> "B";
            case 2 -> "K";
            case 3 -> "R";
            case 4 -> "Q";
            case 5 -> "King";
            default -> "E";
        };
    }

    /*
    3 :
    0 (AKA None) - Move + Attack
    1 - Only Move
    2 - Only Attack

    4 :
    0 - Normal
    1 - Challange a Super Position
    2 - Entangle with Super Position
     */
    ArrayList<Piece> CurrentPiece;
    public ArrayList<Integer[]> GetPos(Integer[] CurrentPos, int Size, Piece[] AllPieces) {
        ArrayList<Integer[]> AllPos = new ArrayList<>(); // in Board terms (0 - 7) Size * (x % 8) + (int)(Size * .1f)
        Integer[] Converted = new Integer[]{(CurrentPos[0] - (int)(Size * .1f)) / Size, (CurrentPos[1] - (int)(Size * .1f)) / Size};
        CurrentPiece = new ArrayList<>();
        switch (Piece) {
            case 0 : {
                int NoMove = 0;
                AllPos.add(new Integer[]{Converted[0], Converted[1] - (IsWhite ? -1 : 1), 1, 0});
                int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                        NoMove = 2;
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                        NoMove = 1;
                    }
                }

                if (FirstMove && NoMove != 1) {
                    AllPos.add(new Integer[]{Converted[0], Converted[1] - (IsWhite ? -1 : 1) * 2, 1, NoMove});
                    NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                        } else {
                            AllPos.remove(0);
                        }
                    }
                }

                AllPos.add(new Integer[]{Converted[0] + 1, Converted[1] - (IsWhite ? -1 : 1), 2,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2) {
                    if (NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] - 1, Converted[1] - (IsWhite ? -1 : 1), 2,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2) {
                    if (NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }
                break;
            }
            case 2 : {
                AllPos.add(new Integer[]{Converted[0] + 2, Converted[1] - 1,0,0});
                int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] + 2, Converted[1] + 1,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] - 2, Converted[1] - 1,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] - 2, Converted[1] + 1,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] + 1, Converted[1] - 2,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] + 1, Converted[1] + 2,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] - 1, Converted[1] - 2,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }

                AllPos.add(new Integer[]{Converted[0] - 1, Converted[1] + 2,0,0});
                NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                if (NodePos != 2 && NodePos != 0) {
                    if (NodePos == 3 || NodePos == 4) {
                        AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                    } else {
                        AllPos.remove(AllPos.size() - 1);
                    }
                }
                break;
            }
            case 5 : {
                for (int x = 1; x >= -1; x--) {
                    for (int y = 1; y >= -1; y--) {
                        if (x == 0 && y == 0) {
                            continue;
                        }
                        AllPos.add(new Integer[]{Converted[0] + x, Converted[1] + y, 0,0});
                        int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                        if (NodePos != 2 && NodePos != 0) {
                            if (NodePos == 3 || NodePos == 4) {
                                AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 1, 1});
                            } else {
                                AllPos.remove(AllPos.size() - 1);
                            }
                        }
                    }
                }
                break;
            }
            case 1,4 : {
                int xIn = 1;
                int yIn = 1;
                boolean Tange = false;
                while (Converted[0] + xIn <= 7 && Converted[1] + yIn <= 7) {
                    AllPos.add(new Integer[]{Converted[0] + xIn, Converted[1] + yIn,0, (Tange ? 2 : 0)});
                    xIn++;
                    yIn++;
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                xIn = 1;
                yIn = 1;
                Tange = false;
                CurrentPiece = new ArrayList<>();
                while (Converted[0] + xIn <= 7 && Converted[1] - yIn >= 0) {
                    AllPos.add(new Integer[]{Converted[0] + xIn, Converted[1] - yIn,0,(Tange ? 2 : 0)});
                    xIn++;
                    yIn++;
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                xIn = 1;
                yIn = 1;
                Tange = false;
                CurrentPiece = new ArrayList<>();
                while (Converted[0] - xIn >= 0 && Converted[1] + yIn <= 7) {
                    AllPos.add(new Integer[]{Converted[0] - xIn, Converted[1] + yIn,0,(Tange ? 2 : 0)});
                    xIn++;
                    yIn++;
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                xIn = 1;
                yIn = 1;
                Tange = false;
                CurrentPiece = new ArrayList<>();
                while (Converted[0] - xIn >= 0 && Converted[1] - yIn >= 0) {
                    AllPos.add(new Integer[]{Converted[0] - xIn, Converted[1] - yIn,0,(Tange ? 2 : 0)});
                    xIn++;
                    yIn++;
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }

                if (Piece == 1) {
                    break;
                }
            }
            case 3 :{
                boolean Tange = false;
                CurrentPiece = new ArrayList<>();
                for (int x = Converted[0] + 1; x < 8; x++) {
                    AllPos.add(new Integer[]{x, Converted[1],0,(Tange ? 2 : 0)});
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                Tange = false;
                CurrentPiece = new ArrayList<>();
                for (int x = Converted[0] - 1; x >= 0; x--) {
                    AllPos.add(new Integer[]{x, Converted[1],0,(Tange ? 2 : 0)});
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                Tange = false;
                CurrentPiece = new ArrayList<>();
                for (int x = Converted[1] + 1; x < 8; x++) {
                    AllPos.add(new Integer[]{Converted[0], x,0,(Tange ? 2 : 0)});
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                Tange = false;
                CurrentPiece = new ArrayList<>();
                for (int x = Converted[1] - 1; x >= 0; x--) {
                    AllPos.add(new Integer[]{Converted[0], x,0,(Tange ? 2 : 0)});
                    int NodePos = CheckPiece(AllPos.get(AllPos.size() - 1), AllPieces, Size);
                    if (NodePos != 0) {
                        if (NodePos == 1) {
                            AllPos.remove(AllPos.size() - 1);
                        } else if (NodePos == 3 || NodePos == 4) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                            Tange = true;
                            continue;
                        } else if (NodePos == 5 || NodePos == 6) {
                            AllPos.set(AllPos.size() - 1, new Integer[]{AllPos.get(AllPos.size() - 1)[0], AllPos.get(AllPos.size() - 1)[1], 0, 1});
                        }
                        break;
                    }
                }
                break;
            }
        }
        return AllPos;
    }

    byte CheckPiece(Integer[] Pos, Piece[] AllPiece, int Size) {
        for (int x = 0; x < AllPiece.length; x++) {
            for (int y = 0; y < AllPiece[x].Positions.size(); y++) {
                // Checks Position
                if ((AllPiece[x].Positions.get(y)[0] - (int) (Size * .1f)) / Size == Pos[0] && (AllPiece[x].Positions.get(y)[1] - (int) (Size * .1f)) / Size == Pos[1]) {
                    if (AllPiece[x].IsWhite == IsWhite) { // If Same
                        if (AllPiece[x].Positions.size() > 1) { // Super Position
                            if (CurrentPiece.contains(AllPiece[x])) {
                                return 5;
                            }
                            CurrentPiece.add(AllPiece[x]);
                            return 3;
                        }
                        return 1; // Friend in Pos
                    } else {
                        if (AllPiece[x].Positions.size() > 1) { // Super Position
                            if (CurrentPiece.contains(AllPiece[x])) {
                                return 6;
                            }
                            CurrentPiece.add(AllPiece[x]);
                            return 4;
                        }
                        return 2; // Enemy in Pos
                    }
                }
            }
        }
        return 0; // None
    }
}
