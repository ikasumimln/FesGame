import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;

/*
 * Created on 2006/02/24
 */

public class MainPanel extends JPanel implements Runnable, KeyListener {
	// パネルサイズ
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    // 線のy座標
    public static final double y1 = 140.0d, y2 = 460.0d;
    //キー押下フラグ
    public static boolean keyLeft = false, keyRight = false;
    // 敵、線の数
    public static final int NUM_ENEMY = 6;
    private static final int NUM_LINE = 2;
    private static final int NUM_OWN = 3;
    private static final int SPACE =-Enemy.SIZE;
    // 敵、線、自機を格納する配列
    private Enemy[] enemy;
    private Line[] line;
    private Own[] own;
    // アニメーション用スレッド
    private Thread thread;
    //デス数
    int score;
    // シーン(0:タイトル 1:メイン 2:ゲームオーバー)
	int scene;

    public MainPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);

        // 敵を格納する配列を作成
        enemy = new Enemy[NUM_ENEMY];
        // 敵を作成
        enemy[0] = new Enemy(SPACE, 160, 10);
        enemy[1] = new Enemy(SPACE - 200, 210, 10);
        enemy[2] = new Enemy(SPACE - 400, 260, 10);
        enemy[3] = new Enemy(SPACE - 600, 310, 10);
        enemy[4] = new Enemy(SPACE - 800, 360, 10);
        enemy[5] = new Enemy(SPACE - 1000, 410, 10);

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

        // キー入力の受け付け開始
    	addKeyListener(this);
    	// フォーカスを要求
    	requestFocus();

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
            own[k].draw(g);
        }
    }

    // メインループ
    public void run() {
        // プログラムが終了するまでフレーム処理を繰り返す
        while (true) {
            // キー入力の受け付け開始
        	addKeyListener(this);
        	// フォーカスを要求
        	requestFocus();

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


	@Override
    // キーが押されたときの処理
    public void keyPressed(KeyEvent e) {
    	switch (e.getKeyCode()) {
			// 左キーが押されたとき
    		case KeyEvent.VK_LEFT: keyLeft = true; break;
    		// 右キーが押されたとき
    		case KeyEvent.VK_RIGHT: keyRight = true; break;
    	}
    }
 // キーが離されたときの処理
    public void keyReleased(KeyEvent e) {
    	switch (e.getKeyCode()) {
    		// 左キーが離されたとき
    		case KeyEvent.VK_LEFT: keyLeft = false; break;
    		// 右キーが離されたとき
    		case KeyEvent.VK_RIGHT: keyRight = false; break;
    	}
    }

    // キーがタイプされたときの処理
    public void keyTyped(KeyEvent e) {}

}
