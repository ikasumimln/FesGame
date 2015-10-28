import java.text.NumberFormat;
import java.util.Timer;
import java.util.TimerTask;

//TimerTask内部クラス
public class TimeTask extends TimerTask {
	public static double i = 0;
    public static String time;
    public void run() {
    	i += 0.01;

    	NumberFormat format = NumberFormat.getInstance();
   		format.setMaximumFractionDigits(2);
   		time = (format.format(i));
   		System.out.println(time);
    }
}

class TimerSample{
    public static void main(String[] args) {
    		// タイマーインスタンス作成
    		Timer timer = new Timer();
    		// タイマー開始　0.01秒ごとにタイマータスクTimeTaskを繰り返し
    		timer.schedule(new TimeTask(), 0, 10);
    }

}