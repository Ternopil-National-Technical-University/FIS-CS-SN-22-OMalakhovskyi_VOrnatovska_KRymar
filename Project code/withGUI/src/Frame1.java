import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Frame1 extends JFrame {
    
    JPanel contentPane;
    
    JPanel gamePane = new JPanel();
    
    BorderLayout borderLayout1 = new BorderLayout();
    
    JLabel status = new JLabel();
    
    JButton[][] buttons = new JButton[4][4];
    int[][] matrix = new int[4][4];
    
    public Frame1() {
        enableEvents(AWTEvent.WINDOW_EVENT_MASK); //проба запуску події
        try {
            jbInit();
            status.setText("Запуск пройшон нармально");
        } catch (Exception exeption) {
            exeption.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        contentPane.setLayout(borderLayout1);
        
        this.setSize(new Dimension(210, 280));
        this.setTitle("Курсова");
        this.setResizable(false);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menuGame = new JMenu("Гра");
        JMenu menuHelp = new JMenu("Хелпа тут");
        
        menuBar.add(menuGame);
        menuBar.add(menuHelp);
        this.setJMenuBar(menuBar);
        
        JMenuItem newGameButton = new JMenuItem("Нова гра");
        JMenuItem exitButton = new JMenuItem("Вихід");
        JMenuItem aboutButton = new JMenuItem("Про нас");
        newGameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                newGame();
            }
        });
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);//вихід 
            }
        });
        aboutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {JOptionPane.showMessageDialog(null,"Гра пятнашки, пиляли спільно!","Палітєх", JOptionPane.QUESTION_MESSAGE);
            }
        });

        menuGame.add(newGameButton);
        menuGame.add(exitButton);
        menuHelp.add(aboutButton);
        gamePane.setLayout(null);
        
        
        int count = 0;
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                buttons[i][j] = new JButton("" + count);

                buttons[i][j].addMouseListener(new mAdapter(i, j));
                buttons[i][j].setSize(50, 50);
                buttons[i][j].setLocation(50 * j, 50 * i);
                buttons[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
                gamePane.add(buttons[i][j]);
                matrix[i][j] = count;
                count++;
            }
        
        buttons[0][0].setText(" "); 
        contentPane.add(gamePane, BorderLayout.CENTER); //панель з кнопками додат
        status.setBorder(BorderFactory.createEtchedBorder()); // статус стрічка рамка, обрамлення
        contentPane.add(status, BorderLayout.SOUTH); //стрічка статусу
        newGame();
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            System.exit(0);
        }
    }


    public void randomizeMatrix() { //випадкове заповнення
        for (int i = 0; i < 100; i++) {
            int a = (int) (Math.random() * 4);
            int b = (int) (Math.random() * 4);
            int a2 = (int) (Math.random() * 4);
            int b2 = (int) (Math.random() * 4);

            int c = matrix[a][b];
            matrix[a][b] = matrix[a2][b2];
            matrix[a2][b2] = c;
        }
    }

    public void newGame() {
        randomizeMatrix();
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++) {
                if (matrix[i][j] != 0)
                    buttons[i][j].setText("" + matrix[i][j]);
                else
                    buttons[i][j].setText("");
            }
        status.setText("Нова гра почата");
        gamePane.setVisible(true);
    }

    class mAdapter extends java.awt.event.MouseAdapter {
        int posi, posj;
        int startx = 0;
        int starty = 0;

        mAdapter(int posI, int posJ) { //конструктор кнопок
            this.posi = posI;
            this.posj = posJ;
        }

        public void mousePressed(MouseEvent e) { //натиснені клавіші
            buttons[posi][posj].setCursor(new Cursor(Cursor.MOVE_CURSOR));
            startx = e.getX();
            starty = e.getY();
        }

        public void mouseReleased(MouseEvent event) { // це при відпусканні

            buttons[posi][posj].setCursor(new Cursor(Cursor.HAND_CURSOR));

            int endx = event.getX();
            int endy = event.getY();

            int shiftx = endx - startx;
            int shifty = endy - starty;
            if (Math.abs(shiftx) > Math.abs(shifty)) {

                if (shiftx > 0) {// вправо

                    if ((posj != 3) && (matrix[posi][posj + 1] == 0)) {
                        matrix[posi][posj + 1] = matrix[posi][posj];
                        matrix[posi][posj] = 0;
                        buttons[posi][posj].setText("");
                        buttons[posi][posj + 1].setText(""
                                + matrix[posi][posj + 1]);
                        status.setText("Хороший хід");
                    } else
                        status.setText("не мухлюй");
                } else {// вліво

 
                    if ((posj != 0) && (matrix[posi][posj - 1] == 0)) {
                        matrix[posi][posj - 1] = matrix[posi][posj];
                        matrix[posi][posj] = 0;
                        buttons[posi][posj].setText("");
                        buttons[posi][posj - 1].setText(""
                                + matrix[posi][posj - 1]);
                        status.setText("Хороший хід");
                    } else
                        status.setText("не мухлюй");
                }
            } else {
                //по осі y
                if (shifty > 0) {// вниз

                    if ((posi != 3) && (matrix[posi + 1][posj] == 0)) {
                        matrix[posi + 1][posj] = matrix[posi][posj];
                        matrix[posi][posj] = 0;
                        buttons[posi][posj].setText("");
                        buttons[posi + 1][posj].setText(""
                                + matrix[posi + 1][posj]);
                        status.setText("Хороший хід");
                    } else
                        status.setText("не мухлюй");
                } else {// вверх

                    if ((posi != 0) && (matrix[posi - 1][posj] == 0)) {
                        matrix[posi - 1][posj] = matrix[posi][posj];
                        matrix[posi][posj] = 0;
                        buttons[posi][posj].setText("");
                        buttons[posi - 1][posj].setText(""
                                + matrix[posi - 1][posj]);
                        status.setText("Хороший хід");
                    } else
                        status.setText("не мухлюй");
                }
            }

            int count = 1;
            int error = 0;
            for (int i = 0; i < 4; i++)
                for (int j = 0; j < 4; j++) {
                    if (matrix[i][j] != count)
                        error++;
                    count++;
                }
            if (error == 1) {
                status.setText("Ви перемогли");
                int result = JOptionPane.showConfirmDialog(null, "Ви перемогли",
                        "Ще партійку?", JOptionPane.YES_NO_OPTION);
                if (result == JOptionPane.YES_OPTION)
                    newGame(); //тут тіпа початко якшошо
                else
                    gamePane.setVisible(false); // тут тіпа хепі енд
            }
        }
    }
}