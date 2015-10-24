import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

/*
 * Created on 2006/02/24
 */

public class MainPanel extends JPanel implements Runnable {
	// パネルサイズ
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    // 線のy座標
    public static final double y1 = 140.0d, y2 = 460.0d;
    // 敵、線の数
    private static final int NUM_ENEMY = 6;
    private static final int NUM_LINE = 2;
    private static final int NUM_OWN = 3;
    // 敵、線、自機を格納する配列
    private Enemy[] enemy;
    private Line[] line;
    private Own[] own;
    // アニメーション用スレッド
    private Thread thread;

    public MainPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);

        // 敵を格納する配列を作成
        enemy = new Enemy[NUM_ENEMY];
        // 敵を作成
        enemy[0] = new Enemy(0, 160, 10, 0);
        enemy[1] = new Enemy(0, 210, 10, 0);
        enemy[2] = new Enemy(0, 260, 10, 0);
        enemy[3] = new Enemy(0, 310, 10, 0);
        enemy[4] = new Enemy(0, 360, 10, 0);
        enemy[5] = new Enemy(0, 410, 10, 0);

    	// 線を格納する配列を作成
        line = new Line[NUM_LINE];
        //線を作成
        line[0] = new Line(0.0d, y1, 800.0d, y1, "BLACK");
        line[1] = new Line(0.0d, y2, 800.0d, y2, "BLACK");

    	// 自機を格納する配列を作成
    	own = new Own[NUM_OWN];
        //自機を作成
        own[0] = new Own(378, (int) y1, 9, 12);
        own[1] = new Own(1178, (int) y1, 9, 12);
        own[2] = new Own(-422, (int )y1, 9, 12);

        // スレッドを起動
        thread = new Thread(this);
        thread.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 各敵を描画
        for (int i = 0; i < NUM_ENEMY; i++) {
            enemy[i].draw(g);
        }
        //線を描画
        for (int j = 0; j < NUM_LINE; j++) {
            line[j].paintComponent(g);
        }
        //自機を描画
        for (int k = 0; k < NUM_OWN; k++) {
            own[k].paint(g);
        }
    }

    // メインループ
    public void run() {
        // プログラムが終了するまでフレーム処理を繰り返す
        while (true) {
            // 各敵を速度分だけ移動させる
            for (int i = 0; i < NUM_ENEMY; i++) {
                enemy[i].move();
            }
            //自機の移動
            for (int k = 0; k < NUM_OWN; k++) {
            	own[k].move();
            }
            // 再描画
            repaint();

            // 20ミリ秒だけ休止
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
