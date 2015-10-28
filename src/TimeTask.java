import java.text.NumberFormat;
import java.util.TimerTask;

//TimerTask内部クラス
class TimeTask extends TimerTask {
    public void run() {
    	MainPanel.i += 0.01;

    	NumberFormat format = NumberFormat.getInstance();
   		format.setMaximumFractionDigits(2);
   		MainPanel.time = (format.format(MainPanel.i));
   		System.out.println(MainPanel.time);
    }
}