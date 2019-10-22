import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;
import java.text.AttributedCharacterIterator;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fib extends JPanel
{
    int width = 15, height = 5;
    final int SPEED = 4; // 1000ms

    Timer timer;
    int phase;

    public Fib() {
        phase = 0;
    }
    
    public void start() {
    	timer = new Timer(SPEED, new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                phase++;
                repaint();

                if(phase >= 360) {
                    phase = 0;
                }
            }
        });
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawLine(0, height / 2, width, height / 2);

        drawWave(g, width, height, phase);
    }

    private void drawWave(Graphics g, int width, int height, int phase) {
        for(double x = -(width / 2); x <= (width / 2); x = x + 0.5) {
            double y = 50 * Math.sin((x + phase) * (Math.PI / 180));

            int x1 = (int)x;
            int y1 = (int)y;

            g.setColor(Color.BLUE);

            g.drawLine((width / 2) + x1, (height / 2) - y1 - 1, (width / 2) + x1, (height / 2) - y1 - 1);
            g.drawLine((width / 2) + x1, (height / 2) - y1, (width / 2) + x1, (height / 2) - y1);
            g.drawLine((width / 2) + x1, (height / 2) - y1 + 1, (width / 2) + x1, (height / 2) - y1 + 1);
        }
    }
    
    static GraphicsConfiguration gc;
    
    public static void main(String[] args) {
    	Fib f = new Fib();
    	JFrame frame = new JFrame(gc);
    	//f.setVisible(true);
    	frame.add(f, BorderLayout.WEST);
    	//f.setLocation(1500, 1500);
    	frame.setVisible(true);
    	f.start();
    }
}