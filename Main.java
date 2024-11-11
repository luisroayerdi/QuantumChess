import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;
import java.util.*;

// Me-sha

public class Main extends JFrame {

    JButton SuperPosition = new JButton();
    JLabel[][] Board = new JLabel[8][8];
    JButton[] WhitePiece = new JButton[16];
    JButton[] BlackPiece = new JButton[16];
    ArrayList<JButton> SuperPiece = new ArrayList<>();
    Dictionary<JButton, SuperPiece> SuperPosPiece = new Hashtable<>();

    ArrayList<Entangle> FormBond = new ArrayList<>();

    JButton[][] MoveButton = new JButton[8][8];
    Piece[] AllPieces = new Piece[32];
    int SelectedPiece = -1;
    int Size = 100;
    boolean WhiteTurn = true;
    boolean Super = false;

    public Main() {
        SuperPosition.setBounds(Size * 8 + 10, 0, (int)(Size * 2f), Size);
        SuperPosition.setBackground(Color.lightGray);
        SuperPosition.setText("Super Position");
        SuperPosition.addActionListener(e -> {
            Super = !Super;
            SuperPosition.setBackground(Super ? Color.blue : Color.lightGray);

            String Test = "Current Super Positions : " + SuperPiece.size();
            //System.out.println(Test);
        });

        this.add(SuperPosition);

        for (int x = 0; x < MoveButton.length; x++) {
            for (int y = 0; y < MoveButton[x].length; y++) {
                MoveButton[x][y] = new JButton();
                MoveButton[x][y].setBounds(Size * x + (int)(Size * .25f), Size * y + (int)(Size * .25f), (int)(Size * .5f), (int)(Size * .5f));
                MoveButton[x][y].setVisible(false);
                MoveButton[x][y].setBackground(Color.green);

                Integer[] Pos = new Integer[]{x, y};
                MoveButton[x][y].addActionListener(e -> {
                    // If Red, Challange
                    // If Blue, Entangle
                    // If Green, Normal Check

                    for (int z = 0; z < WhitePiece.length; z++ ) {
                        WhitePiece[z].setBackground(Color.lightGray);
                    }
                    for (int z = 0; z < BlackPiece.length; z++ ) {
                        BlackPiece[z].setBackground(Color.gray);
                    }
                    for (int z = 0; z < FormBond.size(); z++ ) {
                        if (FormBond.get(z).Target.IsWhite) {
                            WhitePiece[FormBond.get(z).Target.ButtonNumber].setBackground(Color.red);
                        } else {
                            BlackPiece[FormBond.get(z).Target.ButtonNumber].setBackground(Color.red);
                        }
                    }

                    if (Super && AllPieces[SelectedPiece].Positions.size() <= 1) {
                        AllPieces[SelectedPiece].Positions.clear();
                        MoveButton[Pos[0]][Pos[1]].setVisible(false);
                        AllPieces[SelectedPiece].Positions.add(new Integer[]{Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f)});
                        AllPieces[SelectedPiece].Positions.add(new Integer[]{0, 0, 0});
                        //Board[Pos[0]][Pos[1]].setBackground(Board[Pos[0]][Pos[1]].getBackground() == Color.white ? new Color(173, 216, 230) : new Color(0, 0, 139));

                        SuperPiece.add(new JButton());
                        SuperPiece.get(SuperPiece.size() - 1).setBounds(Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f), (int) (Size * .8f), (int) (Size * .8f));
                        SuperPiece.get(SuperPiece.size() - 1).setBackground(new Color(173, 216, 230));
                        try {
                            SuperPiece.get(SuperPiece.size() - 1).setText(WhitePiece[SelectedPiece].getText());
                        } catch (Exception ignore) {
                            SuperPiece.get(SuperPiece.size() - 1).setText(BlackPiece[SelectedPiece - 16].getText());
                        }

                        // If a piece is captured, then Add it to Entangle

                        return;
                    } else if (Super) {
                        SuperPiece.add(new JButton());
                        SuperPiece.get(SuperPiece.size() - 1).setBounds(Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f), (int) (Size * .8f), (int) (Size * .8f));
                        SuperPiece.get(SuperPiece.size() - 1).setBackground(new Color(173, 216, 230));
                        SuperPiece SuperScript;
                        try {
                            SuperPiece.get(SuperPiece.size() - 1).setText(WhitePiece[SelectedPiece].getText());
                            WhitePiece[SelectedPiece].setVisible(false);
                            SuperScript = new SuperPiece(WhitePiece[SelectedPiece], SuperPiece.get(SuperPiece.size() - 1), SuperPiece.get(SuperPiece.size() - 2));
                            SuperPosPiece.put(SuperPiece.get(SuperPiece.size() - 1), SuperScript);
                            SuperPosPiece.put(SuperPiece.get(SuperPiece.size() - 2), SuperScript);
                        } catch (Exception ignore) {
                            SuperPiece.get(SuperPiece.size() - 1).setText(BlackPiece[SelectedPiece - 16].getText());
                            BlackPiece[SelectedPiece - 16].setVisible(false);
                            SuperScript = new SuperPiece(BlackPiece[SelectedPiece - 16], SuperPiece.get(SuperPiece.size() - 1), SuperPiece.get(SuperPiece.size() - 2));
                            SuperPosPiece.put(SuperPiece.get(SuperPiece.size() - 1), SuperScript);
                            SuperPosPiece.put(SuperPiece.get(SuperPiece.size() - 2), SuperScript);
                        }

                        int Current = SelectedPiece;
                        SuperPiece.get(SuperPiece.size() - 1).addActionListener(ee -> {
                            AllPieces[Current].Positions = SuperPosPiece.get(((JButton) ee.getSource())).Challange(AllPieces[Current].Positions);
                            ((JButton)ee.getSource()).setBounds(2000,2000, 0, 0);
                            SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()).setBounds(2000,2000, 0, 0);
                            ((JButton)ee.getSource()).setVisible(false);
                            SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()).setVisible(false);
                            Check(AllPieces[Current]);

                            this.remove(((JButton)ee.getSource()));
                            this.remove(SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()));
                            SuperPiece.remove(SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()));
                            SuperPiece.remove(((JButton)ee.getSource()));
                            SuperPosPiece.remove(SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()));
                            SuperPosPiece.remove(((JButton)ee.getSource()));

                            for (int z = 0; z < FormBond.size(); z++) {
                                if (FormBond.get(z).MovementRelations.get(AllPieces[Current]) != null) {
                                    if (!FormBond.get(z).MakeDecision()) {
                                        FormBond.get(z).Target.Positions.clear();
                                        if (FormBond.get(z).Target.IsWhite) {
                                            WhitePiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                        } else {
                                            BlackPiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                        }
                                        FormBond.remove(z);
                                        z--;
                                        continue;
                                    }
                                    if (FormBond.get(z).Target.IsWhite) {
                                        WhitePiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                    } else {
                                        BlackPiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                    }
                                }
                            }

                            this.revalidate();
                            this.repaint();
                        });

                        SuperPiece.get(SuperPiece.size() - 2).addActionListener(ee -> {
                            AllPieces[Current].Positions = SuperPosPiece.get(((JButton) ee.getSource())).Challange(AllPieces[Current].Positions);
                            ((JButton)ee.getSource()).setBounds(2000,2000, 0, 0);
                            SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()).setBounds(2000,2000, 0, 0);
                            ((JButton)ee.getSource()).setVisible(false);
                            SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()).setVisible(false);
                            Check(AllPieces[Current]);

                            this.remove(((JButton)ee.getSource()));
                            this.remove(SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()));
                            SuperPiece.remove(SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()));
                            SuperPiece.remove(((JButton)ee.getSource()));
                            SuperPosPiece.remove(SuperPosPiece.get(((JButton) ee.getSource())).OtherButton((JButton) ee.getSource()));
                            SuperPosPiece.remove(((JButton)ee.getSource()));

                            for (int z = 0; z < FormBond.size(); z++) {
                                if (FormBond.get(z).MovementRelations.get(AllPieces[Current]) != null) {
                                    if (!FormBond.get(z).MakeDecision()) {
                                        FormBond.get(z).Target.Positions.clear();
                                        if (FormBond.get(z).Target.IsWhite) {
                                            WhitePiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                        } else {
                                            BlackPiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                        }
                                        FormBond.remove(z);
                                        z--;
                                        continue;
                                    }
                                    if (FormBond.get(z).Target.IsWhite) {
                                        WhitePiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                    } else {
                                        BlackPiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                    }
                                }
                            }

                            this.revalidate();
                            this.repaint();
                        });

                        this.add(SuperPiece.get(SuperPiece.size() - 1), 1);
                        this.add(SuperPiece.get(SuperPiece.size() - 2), 1);

                        for (int xx = 0; xx < MoveButton.length; xx++) {
                            for (int yy = 0; yy < MoveButton.length; yy++) {
                                MoveButton[xx][yy].setVisible(false);
                                this.add(MoveButton[xx][yy], 1);
                            }
                        }

                        AllPieces[SelectedPiece].Positions.remove((AllPieces[SelectedPiece].Positions.size() - 1));
                        Super = false;
                        SuperPosition.setBackground(Color.lightGray);
                        AllPieces[SelectedPiece].Positions.add(new Integer[]{Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f)});
                        //Board[Pos[0]][Pos[1]].setBackground(Board[Pos[0]][Pos[1]].getBackground() == Color.white ? new Color(173, 216, 230) : new Color(0, 0, 139));
                        //try {
                            //WhitePiece[SelectedPiece].setBackground(new Color(173, 216, 230));
                        //} catch (Exception ignore) {
                            //BlackPiece[SelectedPiece - 16].setBackground(new Color(173, 216, 230));
                        //}
                        AllPieces[SelectedPiece].FirstMove = false;

                        //Copy, Paste Above
                        if (WhiteTurn) {
                            for (int q = 0; q < BlackPiece.length; q++) {
                                if (BlackPiece[q].getX() == WhitePiece[SelectedPiece].getX() && BlackPiece[q].getY() == WhitePiece[SelectedPiece].getY()) {
                                    //BlackPiece[q].hide();
                                    BlackPiece[q].setBackground(Color.red);
                                    ArrayList<int[]> Strong = new ArrayList<>();
                                    ArrayList<Integer[]> Weak = new ArrayList<>();

                                    for (int z = 0; z < AllPieces[SelectedPiece].Positions.size(); z++) {
                                        Strong.add(new int[]{AllPieces[SelectedPiece].Positions.get(z)[0], AllPieces[SelectedPiece].Positions.get(z)[1]});
                                    }
                                    if (Strong.get(0)[0] == BlackPiece[q].getX() && Strong.get(0)[1] == BlackPiece[q].getY()) {
                                        Weak.add(new Integer[]{-1, -1});
                                        Weak.add(new Integer[]{BlackPiece[q].getX(), BlackPiece[q].getY()});
                                    } else {
                                        Weak.add(new Integer[]{BlackPiece[q].getX(), BlackPiece[q].getY()});
                                        Weak.add(new Integer[]{-1, -1});
                                    }

                                    FormBond.add(new Entangle(AllPieces[SelectedPiece], AllPieces[q + 16], Strong, Weak));
                                    // Turn the Piece Entangled with the moved piece
                                }
                            }
                        } else {
                            for (int q = 0; q < WhitePiece.length; q++) {
                                if (WhitePiece[q].getX() == BlackPiece[SelectedPiece - 16].getX() && WhitePiece[q].getY() == BlackPiece[SelectedPiece - 16].getY()) {
                                    //WhitePiece[q].hide();
                                    WhitePiece[q].setBackground(Color.red);
                                    ArrayList<int[]> Strong = new ArrayList<>();
                                    ArrayList<Integer[]> Weak = new ArrayList<>();

                                    for (int z = 0; z < AllPieces[SelectedPiece].Positions.size(); z++) {
                                        Strong.add(new int[]{AllPieces[SelectedPiece].Positions.get(z)[0], AllPieces[SelectedPiece].Positions.get(z)[1]});
                                    }
                                    if (Strong.get(0)[0] == WhitePiece[q].getX() && Strong.get(0)[1] == WhitePiece[q].getY()) {
                                        Weak.add(new Integer[]{-1, -1});
                                        Weak.add(new Integer[]{WhitePiece[q].getX(), WhitePiece[q].getY()});
                                    } else {
                                        Weak.add(new Integer[]{WhitePiece[q].getX(), WhitePiece[q].getY()});
                                        Weak.add(new Integer[]{-1, -1});
                                    }

                                    FormBond.add(new Entangle(AllPieces[SelectedPiece], AllPieces[q], Strong, Weak));
                                    // Turn the Piece Entangled with the moved piece
                                }
                            }
                        }

                        WhiteTurn = !WhiteTurn;
                        for (int q = 0; q < WhitePiece.length; q++) {
                            WhitePiece[q].setEnabled(WhiteTurn);
                        }
                        for (int q = 0; q < BlackPiece.length; q++) {
                            BlackPiece[q].setEnabled(!WhiteTurn);
                        }
                        for (int q = 0; q < SuperPiece.size(); q++) {
                            SuperPiece.get(q).setEnabled(SuperPosPiece.get(SuperPiece.get(q)).IsWhite(AllPieces, WhitePiece, BlackPiece) == WhiteTurn);
                        }
                        SelectedPiece = -1;
                        return;
                    }

                    // green, red, cyan

                    for (int xx = 0; xx < MoveButton.length; xx++) {
                        for (int yy = 0; yy < MoveButton.length; yy++) {
                            MoveButton[xx][yy].setVisible(false);
                        }
                    }

                    Integer[] OldPos = new Integer[]{-1, -1};
                    if (AllPieces[SelectedPiece].Positions.size() == 1) {
                        OldPos = AllPieces[SelectedPiece].Positions.get(0);
                    }
                    AllPieces[SelectedPiece].Positions.clear();
                    AllPieces[SelectedPiece].Positions.add(new Integer[]{Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f)});

                    if (SelectedPiece >= 16) {
                        BlackPiece[SelectedPiece - 16].setBounds(Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f), (int) (Size * .8f), (int) (Size * .8f));
                    } else {
                        WhitePiece[SelectedPiece].setBounds(Size * (Pos[0] % 8) + (int) (Size * .1f), Size * (Pos[1] % 8) + (int) (Size * .1f), (int) (Size * .8f), (int) (Size * .8f));
                    }

                    if (((JButton)e.getSource()).getBackground() == Color.red) {
                        if (SelectedPiece >= 16) {
                            for (int q = 0; q < SuperPiece.size(); q++) {
                                //System.out.println("Pos : (" + BlackPiece[SelectedPiece - 16].getX() + ", " + BlackPiece[SelectedPiece - 16].getY() + ") vs (" + SuperPiece.get(q).getX() + ", " + SuperPiece.get(q).getY() + ")");
                                if (BlackPiece[SelectedPiece - 16].getX() == SuperPiece.get(q).getX() && BlackPiece[SelectedPiece - 16].getY() == SuperPiece.get(q).getY()) {
                                    boolean Selected = true;
                                    for (int w = 0; w < BlackPiece.length; w++) {
                                        if (SuperPosPiece.get(SuperPiece.get(q)).OGButton == BlackPiece[AllPieces[w + 16].ButtonNumber]) {
                                            AllPieces[w + 16].Positions = SuperPosPiece.get(SuperPiece.get(q)).Challange(AllPieces[w + 16].Positions);
                                            Selected = false;
                                            break;
                                        }
                                    }
                                    if (Selected) {
                                        for (int w = 0; w < WhitePiece.length; w++) {
                                            if (SuperPosPiece.get(SuperPiece.get(q)).OGButton == WhitePiece[AllPieces[w].ButtonNumber]) {
                                                AllPieces[w].Positions = SuperPosPiece.get(SuperPiece.get(q)).Challange(AllPieces[w].Positions);
                                                Selected = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (Selected) {
                                        System.out.println("NO SUPER POSITION LOCATED");
                                    }

                                    //AllPieces[SelectedPiece].Positions = SuperPosPiece.get(SuperPiece.get(q)).Challange(AllPieces[SelectedPiece].Positions);
                                    SuperPiece.get(q).setBounds(2000,2000, 0, 0);
                                    SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)).setBounds(2000,2000, 0, 0);
                                    SuperPiece.get(q).setVisible(false);
                                    SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)).setVisible(false);
                                    Check(AllPieces[SelectedPiece]);

                                    this.remove(SuperPiece.get(q));
                                    this.remove(SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)));

                                    JButton Re = SuperPiece.get(q);
                                    SuperPosPiece.remove(SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)));
                                    SuperPiece.remove(SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)));
                                    SuperPosPiece.remove(Re);
                                    SuperPiece.remove(Re);

                                    for (int z = 0; z < FormBond.size(); z++) {
                                        if (FormBond.get(z).MovementRelations.get(AllPieces[SelectedPiece]) != null) {
                                            if (!FormBond.get(z).MakeDecision()) {
                                                FormBond.get(z).Target.Positions.clear();
                                                if (FormBond.get(z).Target.IsWhite) {
                                                    WhitePiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                                } else {
                                                    BlackPiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                                }
                                                FormBond.remove(z);
                                                z--;
                                                continue;
                                            }
                                            if (FormBond.get(z).Target.IsWhite) {
                                                WhitePiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                            } else {
                                                BlackPiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                            }
                                        }
                                    }

                                    this.revalidate();
                                    this.repaint();
                                    break;
                                }
                            }
                        } else {
                            for (int q = 0; q < SuperPiece.size(); q++) {
                                if (WhitePiece[SelectedPiece].getX() == SuperPiece.get(q).getX() && WhitePiece[SelectedPiece].getY() == SuperPiece.get(q).getY()) {
                                    boolean Selected = true;
                                    for (int w = 0; w < BlackPiece.length; w++) {
                                        if (SuperPosPiece.get(SuperPiece.get(q)).OGButton == BlackPiece[AllPieces[w + 16].ButtonNumber]) {
                                            AllPieces[w + 16].Positions = SuperPosPiece.get(SuperPiece.get(q)).Challange(AllPieces[w + 16].Positions);
                                            Selected = false;
                                            break;
                                        }
                                    }
                                    if (Selected) {
                                        for (int w = 0; w < WhitePiece.length; w++) {
                                            if (SuperPosPiece.get(SuperPiece.get(q)).OGButton == WhitePiece[AllPieces[w].ButtonNumber]) {
                                                AllPieces[w].Positions = SuperPosPiece.get(SuperPiece.get(q)).Challange(AllPieces[w].Positions);
                                                Selected = false;
                                                break;
                                            }
                                        }
                                    }

                                    if (Selected) {
                                        System.out.println("NO SUPER POSITION LOCATED");
                                    }

                                    //AllPieces[SelectedPiece].Positions = SuperPosPiece.get(SuperPiece.get(q)).Challange(AllPieces[SelectedPiece].Positions);
                                    SuperPiece.get(q).setBounds(2000,2000, 0, 0);
                                    SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)).setBounds(2000,2000, 0, 0);
                                    SuperPiece.get(q).setVisible(false);
                                    SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)).setVisible(false);
                                    Check(AllPieces[SelectedPiece]);

                                    this.remove(SuperPiece.get(q));
                                    this.remove(SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)));

                                    JButton Re = SuperPiece.get(q);
                                    SuperPosPiece.remove(SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)));
                                    SuperPiece.remove(SuperPosPiece.get(SuperPiece.get(q)).OtherButton(SuperPiece.get(q)));
                                    //System.out.println(SuperPosPiece.size() + " " + SuperPiece.size());
                                    SuperPosPiece.remove(Re);
                                    SuperPiece.remove(Re);

                                    for (int z = 0; z < FormBond.size(); z++) {
                                        if (FormBond.get(z).MovementRelations.get(AllPieces[SelectedPiece]) != null) {
                                            if (!FormBond.get(z).MakeDecision()) {
                                                FormBond.get(z).Target.Positions.clear();
                                                if (FormBond.get(z).Target.IsWhite) {
                                                    WhitePiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                                } else {
                                                    BlackPiece[FormBond.get(z).Target.ButtonNumber].setVisible(false);
                                                }
                                                FormBond.remove(z);
                                                z--;
                                                continue;
                                            }
                                            if (FormBond.get(z).Target.IsWhite) {
                                                WhitePiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                            } else {
                                                BlackPiece[FormBond.get(z).Target.ButtonNumber].setBounds(FormBond.get(z).Target.Positions.get(0)[0], FormBond.get(z).Target.Positions.get(0)[1], (int)(Size * .8f), (int)(Size * .8f));
                                            }
                                        }
                                    }

                                    this.revalidate();
                                    this.repaint();
                                    break;
                                }
                            }
                        }
                        System.out.println(AllPieces[SelectedPiece].Positions.size());
                        // Challenge the Quantum Piece (Done)
                    } else if (((JButton)e.getSource()).getBackground() == Color.cyan) {
                        // Entangle the Piece
                        ArrayList<Piece> Super = FindSuper(OldPos);
                        for (int z = 0; z < Super.size(); z++) {
                            // Loop of each Super
                            ArrayList<int[]> Boss = new ArrayList<>();
                            ArrayList<Integer[]> Sub = new ArrayList<>();

                            int Location = (Super.get(z).Positions.get(0) == Super.get(z).Entangle.get(0)) ? 0 : 1;
                            int OtherLocation = (Super.get(z).Positions.get(0) == Super.get(z).Entangle.get(0)) ? 1 : 0;

                            Boss.add(new int[] {Super.get(z).Positions.get(Location)[0], Super.get(z).Positions.get(Location)[1]});
                            Boss.add(new int[] {Super.get(z).Positions.get(OtherLocation)[0], Super.get(z).Positions.get(OtherLocation)[1]});

                            Sub.add(OldPos);
                            Sub.add(AllPieces[SelectedPiece].Positions.get(0));

                            FormBond.add(new Entangle(Super.get(z), AllPieces[SelectedPiece], Boss, Sub));
                            Super.get(z).Entangle = new ArrayList<>();
                        }
                        // End of loop
                    }

                    AllPieces[SelectedPiece].FirstMove = false;
                    if (WhiteTurn) {
                        for (int q = 0; q < BlackPiece.length; q++) {
                            if (BlackPiece[q].getX() == WhitePiece[SelectedPiece].getX() && BlackPiece[q].getY() == WhitePiece[SelectedPiece].getY()) {
                                BlackPiece[q].hide();
                                for (int w = 0; w < BlackPiece.length; w++) {
                                    if (BlackPiece[q] == BlackPiece[AllPieces[w + 16].ButtonNumber]) {
                                        AllPieces[w + 16].Positions.clear();
                                    }
                                }
                            }
                        }
                    } else {
                        for (int q = 0; q < WhitePiece.length; q++) {
                            if (WhitePiece[q].getX() == BlackPiece[SelectedPiece - 16].getX() && WhitePiece[q].getY() == BlackPiece[SelectedPiece - 16].getY()) {
                                WhitePiece[q].hide();
                                for (int w = 0; w < WhitePiece.length; w++) {
                                    if (WhitePiece[q] == WhitePiece[AllPieces[w].ButtonNumber]) {
                                        AllPieces[w].Positions.clear();
                                    }
                                }
                            }
                        }
                    }

                    WhiteTurn = !WhiteTurn;
                    for (int q = 0; q < WhitePiece.length; q++) {
                        WhitePiece[q].setEnabled(WhiteTurn);
                    }
                    for (int q = 0; q < BlackPiece.length; q++) {
                        BlackPiece[q].setEnabled(!WhiteTurn);
                    }
                    for (int q = 0; q < SuperPiece.size(); q++) {
                        SuperPiece.get(q).setEnabled(SuperPosPiece.get(SuperPiece.get(q)).IsWhite(AllPieces, WhitePiece, BlackPiece) == WhiteTurn);
                    }
                    SelectedPiece = -1;
                });
                this.add(MoveButton[x][y]);
            }
        }
        for (int x = 0; x < WhitePiece.length; x++) {
            WhitePiece[x] = new JButton();
            WhitePiece[x].setBounds(Size * (x % 8) + (int)(Size * .1f), Size * (x >= 8 ? 0 : 1) + (int)(Size * .1f), (int)(Size * .8f), (int)(Size * .8f));
            WhitePiece[x].setOpaque(true);
            WhitePiece[x].setBackground(Color.LIGHT_GRAY);
            AllPieces[x] = new Piece(x, true);
            AllPieces[x].Positions.add(new Integer[]{Size * (x % 8) + (int)(Size * .1f), Size * (x >= 8 ? 0 : 1) + (int)(Size * .1f)});
            WhitePiece[x].setText(AllPieces[x].name());

            int Demo = x;
            WhitePiece[x].addActionListener(e -> {
                if (AllPieces[Demo].Positions.size() > 1) {

                }

                for (int xx = 0; xx < MoveButton.length; xx++) {
                    for (int yy = 0; yy < MoveButton.length; yy++) {
                        MoveButton[xx][yy].setVisible(false);
                    }
                }

                SelectedPiece = Demo;
                ArrayList<Integer[]> CanMove = AllPieces[Demo].GetPos(new Integer[]{WhitePiece[Demo].getX(), WhitePiece[Demo].getY()}, Size, AllPieces);
                for (int z = 0; z < CanMove.size(); z++) { // Determines where a Piece can Move
                    try {
                        MoveButton[CanMove.get(z)[0]][CanMove.get(z)[1]].setVisible(true);
                        Color ChangeColor = CanMove.get(z)[3] == 0 ? Color.green : CanMove.get(z)[3] == 1 ? Color.red : Color.CYAN;
                        MoveButton[CanMove.get(z)[0]][CanMove.get(z)[1]].setBackground(ChangeColor);
                    } catch (Exception ignore) {}
                }

                if (Super) {
                    if (CanMove.size() <= 1) {
                        Super = false;
                        SuperPosition.setBackground(Color.lightGray);
                    } else {
                        for (int q = 0; q < WhitePiece.length; q++) {
                            WhitePiece[q].setEnabled(false);
                        }
                        WhitePiece[Demo].setEnabled(true);
                    }
                }
            });

            this.add(WhitePiece[x]);
        }

        for (int x = 0; x < BlackPiece.length; x++) {
            BlackPiece[x] = new JButton();
            BlackPiece[x].setBounds(Size * (x % 8) + (int)(Size * .1f), Size * (7 - (x >= 8 ? 0 : 1)) + (int)(Size * .1f), (int)(Size * .8f), (int)(Size * .8f));
            BlackPiece[x].setOpaque(true);
            BlackPiece[x].setBackground(Color.GRAY);
            AllPieces[x+16] = new Piece(x, false);
            AllPieces[x+16].Positions.add(new Integer[]{Size * (x % 8) + (int)(Size * .1f), Size * (7 - (x >= 8 ? 0 : 1)) + (int)(Size * .1f)});
            BlackPiece[x].setText(AllPieces[x+16].name());
            BlackPiece[x].setEnabled(false);

            int Demo = x;
            BlackPiece[x].addActionListener(e -> {
                if (AllPieces[Demo+16].Positions.size() > 1) {

                }

                for (int xx = 0; xx < MoveButton.length; xx++) {
                    for (int yy = 0; yy < MoveButton.length; yy++) {
                        MoveButton[xx][yy].setVisible(false);
                    }
                }

                SelectedPiece = Demo + 16;
                ArrayList<Integer[]> CanMove = AllPieces[Demo+16].GetPos(new Integer[]{BlackPiece[Demo].getX(), BlackPiece[Demo].getY()}, Size, AllPieces);
                for (int z = 0; z < CanMove.size(); z++) {
                    try {
                        MoveButton[CanMove.get(z)[0]][CanMove.get(z)[1]].setVisible(true);
                        Color ChangeColor = CanMove.get(z)[3] == 0 ? Color.green : CanMove.get(z)[3] == 1 ? Color.red : Color.CYAN;
                        MoveButton[CanMove.get(z)[0]][CanMove.get(z)[1]].setBackground(ChangeColor);
                    } catch (Exception ignore){}
                }

                if (Super) {
                    if (CanMove.size() <= 1) {
                        Super = false;
                        SuperPosition.setBackground(Color.lightGray);
                    } else {
                        for (int q = 0; q < BlackPiece.length; q++) {
                            BlackPiece[q].setEnabled(false);
                        }
                        BlackPiece[Demo].setEnabled(true);
                    }
                }
            });
            this.add(BlackPiece[x]);
        }

        for (int x = 0; x < Board.length; x++) {
            for (int y = 0; y < Board[x].length; y++) {
                Board[x][y] = new JLabel();
                Board[x][y].setBounds(Size * x, Size * y, Size, Size);
                Board[x][y].setOpaque(true);
                Board[x][y].setBackground((x + y) % 2 == 0 ? Color.BLACK : Color.WHITE);
                this.add(Board[x][y]);
            }
        }

        this.setLayout(null);
        //this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setSize(1050, 800);
    }

    public static void main(String[] args) {
        Main lew = new Main();
    }

    public void Check(Piece CurrentPiece) {
        if (CurrentPiece.IsWhite) {
            for (int x = 0; x < BlackPiece.length; x++) {
                if (BlackPiece[x].isVisible()) {
                    if (BlackPiece[x].getX() == WhitePiece[CurrentPiece.ButtonNumber].getX() && BlackPiece[x].getY() == WhitePiece[CurrentPiece.ButtonNumber].getY()) {
                        BlackPiece[x].setVisible(false);
                    }
                }
            }
        } else {
            for (int x = 0; x < WhitePiece.length; x++) {
                if (WhitePiece[x].isVisible()) {
                    if (WhitePiece[x].getX() == BlackPiece[CurrentPiece.ButtonNumber].getX() && WhitePiece[x].getY() == BlackPiece[CurrentPiece.ButtonNumber].getY()) {
                        WhitePiece[x].setVisible(false);
                    }
                }
            }
        }

        // Add Move Here
    }

    public ArrayList<Piece> FindSuper(Integer[] OldPos) {
        JButton CurrentMove = (SelectedPiece < 16 ? WhitePiece[SelectedPiece] : BlackPiece[SelectedPiece - 16]);

        ArrayList<Piece> InUse = new ArrayList<>();

        int xDif = (OldPos[0] == CurrentMove.getX()) ? 0 : (OldPos[0] - CurrentMove.getX()) / Math.abs(OldPos[0] - CurrentMove.getX());
        int yDif = (OldPos[1] == CurrentMove.getY()) ? 0 : (OldPos[1] - CurrentMove.getY()) / Math.abs(OldPos[1] - CurrentMove.getY());
        int[] Difference = new int[]{xDif, yDif};

        if (Difference[0] == 0) {
            for (int z = OldPos[1]; (Difference[1] == 1) ? z > CurrentMove.getY() : z < CurrentMove.getY(); z -= Size * Difference[1]) {
                for (int x = 0; x < AllPieces.length; x++) {
                    for (int y = 0; y < AllPieces[x].Positions.size(); y++) {
                        if (Arrays.equals(AllPieces[x].Positions.get(y), new Integer[]{OldPos[0], z})) {
                            InUse.add(AllPieces[x]);
                            AllPieces[x].Entangle.add(AllPieces[x].Positions.get(y));
                        }
                    }
                }
            }
        } else if (Difference[1] == 0) {
            for (int z = OldPos[0]; (Difference[0] == 1) ? z > CurrentMove.getX() : z < CurrentMove.getX(); z -= Size * Difference[0]) {
                for (int x = 0; x < AllPieces.length; x++) {
                    for (int y = 0; y < AllPieces[x].Positions.size(); y++) {
                        if (Arrays.equals(AllPieces[x].Positions.get(y), new Integer[]{z, OldPos[1]})) {
                            InUse.add(AllPieces[x]);
                            AllPieces[x].Entangle.add(AllPieces[x].Positions.get(y));
                        }
                    }
                }
            }
        } else {
            int xChange = OldPos[0] - Size * Difference[0];
            int yChange = OldPos[1] - Size * Difference[1];
            while ((Difference[1] == 1) ? yChange > CurrentMove.getY() : yChange < CurrentMove.getY() && (Difference[0] == 1) ? xChange > CurrentMove.getX() : xChange < CurrentMove.getX()) {
                for (int x = 0; x < AllPieces.length; x++) {
                    //if (AllPieces[x].Positions.size() > 1) {
                        for (int y = 0; y < AllPieces[x].Positions.size(); y++) {
                            if (Arrays.equals(AllPieces[x].Positions.get(y), new Integer[]{xChange, yChange})) {
                                InUse.add(AllPieces[x]);
                                AllPieces[x].Entangle.add(AllPieces[x].Positions.get(y));
                            }
                        }
                    //}
                }
                xChange -= Size * Difference[0];
                yChange -= Size * Difference[1];
            }
        }

        System.out.println(InUse.size());
         return InUse;
    }
}