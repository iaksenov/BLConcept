package csi.pos.ui.swing;

import ru.crystals.pos.ui.events.POSStatusEvent;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.util.Calendar;
import java.util.concurrent.Executors;

public class StatusPanel extends JPanel {

    private final JLabel cashier;
    private final JLabel clock;

    public StatusPanel() {
        setMinimumSize(new Dimension(0, 30));
        setLayout(new BorderLayout(5, 5));
        cashier = new JLabel();
        clock = new JLabel();
        clock.setMinimumSize(new Dimension(40, 0));
        setBackground(Color.PINK);
        add(cashier, BorderLayout.CENTER);
        add(clock, BorderLayout.EAST);
        Executors.newFixedThreadPool(1).submit(this::run);
    }

    public void setData(POSStatusEvent posStatusEvent) {
        cashier.setText(posStatusEvent.getCurrentCashierFIO());
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Calendar c = Calendar.getInstance();
                boolean dots = (c.get(Calendar.SECOND) % 2) == 0;
                String timeText = String.format("%1$tH" + (dots ? ":" : " ") + "%1$tM", c);
                clock.setText(timeText);
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    public static void main(String[] args) {
        new StatusPanel();
    }

}
