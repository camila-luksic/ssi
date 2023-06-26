
        import javax.swing.*;
        import java.awt.*;
        import java.util.Scanner;

public class Frame extends JPanel {
    private int x1, y1, x2, y2;
    private int mujeres, varones;
    private boolean swapPairs;
    private int currentLeftIndex;
    private int currentRightIndex;

    public Frame(int varones, int mujeres) {
        x1 = 50;
        y1 = 75;
        x2 = 200;
        y2 = 75;
        this.varones = varones;
        this.mujeres = mujeres;
        swapPairs = false;
        currentLeftIndex = 0;
        currentRightIndex = 0;

        Timer timer = new Timer(10, e -> movePairs());
        timer.start();

        Timer swapTimer = new Timer(5000, e -> swapPairs());
        swapTimer.start();
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.BLACK);

        if (varones > 0) {
            int contador = 0;
            // Dibujar los cuadros de hombres
            for (int i = 0; i < varones; i++) {
                if (currentLeftIndex == i) {
                    g.setColor(Color.BLUE);
                    g.fillRect(x1, y1 + (i * 60), 50, 50);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    contador++;
                    g.drawString("M" + contador, x1 + 20, y1 + (i * 60) + 30);
                } else {
                    g.setColor(Color.GRAY);
                    g.fillRect(x1, y1 + (i * 60), 50, 50);
                    g.setColor(Color.WHITE);
                    g.setFont(new Font("Arial", Font.BOLD, 20));
                    contador++;
                    g.drawString("M" + contador, x1 + 20, y1 + (i * 60) + 30);
                }
            }

            if (mujeres > 0) {
                int contadora = 0;
                // Dibujar los cuadros de mujeres
                for (int i = 0; i < mujeres; i++) {
                    if (currentRightIndex == i) {
                        g.setColor(Color.MAGENTA);
                        g.fillRect(x2, y2 + (i * 60), 50, 50);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        contadora++;
                        g.drawString("F" + contadora, x2 + 20, y2 + (i * 60) + 30);
                    } else {
                        g.setColor(Color.GRAY);
                        g.fillRect(x2, y2 + (i * 60), 50, 50);
                        g.setColor(Color.WHITE);
                        g.setFont(new Font("Arial", Font.BOLD, 20));
                        contadora++;
                        g.drawString("F" + contadora, x2 + 20, y2 + (i * 60) + 30);
                    }
                }
            }

            if (swapPairs) {
                g.setColor(Color.BLACK);
                int maxPairs = Math.min(varones, mujeres);
                for (int i = 0; i < maxPairs; i++) {
                    g.drawLine(x1 + 50, y1 + 25 + (i * 60), x2, y2 + 25 + (i * 60));
                }
            }
        }
    }

    private void movePairs() {
        if (!swapPairs && x1 < 200) {
            x1++;
            x2--;
            repaint();
        }
    }

    private void swapPairs() {
        swapPairs = true;
        currentLeftIndex = getNextValidIndex(currentLeftIndex, varones);
        currentRightIndex = getNextValidIndex(currentRightIndex, mujeres);

        repaint();

        Timer resetTimer = new Timer(2000, e -> resetPairs());
        resetTimer.setRepeats(false);
        resetTimer.start();
    }

    private int getNextValidIndex(int currentIndex, int total) {
        int nextIndex = currentIndex;
        do {
            nextIndex++;
            if (nextIndex == total) {
                nextIndex = 0;
            }
        } while (nextIndex == currentIndex);
        return nextIndex;
    }

    private void resetPairs() {
        x1 = 50;
        x2 = 200;
        swapPairs = false;
        repaint();
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese la cantidad de varones: ");
        int varones = scanner.nextInt();

        System.out.print("Ingrese la cantidad de mujeres: ");
        int mujeres = scanner.nextInt();

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("Salón de Baile");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(800, 400);
                frame.setLocationRelativeTo(null);
                frame.add(new Frame(varones, mujeres));
                frame.setVisible(true);
            }
        });

        scanner.close();
    }
}