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
    public static boolean keyLeft, keyRight, keyEnter, keyEsc;
    // 敵、線の数
    public static final int NUM_ENEMY = 6;
    private static final int NUM_LINE = 2;
    private static final int NUM_SELF = 3;
    private static final int NUM_TITLE = 3;
    private static final int NUM_END = 3;
    // 敵、線、自機を格納する配列
    private Enemy[] enemy;
    private Line[] line;
    private Self[] self;
    private Strings[] title;
    private Strings[] end;
    private int x[] = {-30, -230, -430, -630, -830, -1030};
    public static int y[] = {160, 210, 260, 310, 360, 410};
    // アニメーション用スレッド
    Thread gameThread;
    //デス数
    int score;
    // シーン(0:タイトル 1:メイン 2:ゲームオーバー)
	public static int scene = 0;
	public static int i;

    public MainPanel() {
        // パネルの推奨サイズを設定、pack()するときに必要
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(WIDTH, HEIGHT);

        // 敵を格納する配列を作成
        enemy = new Enemy[NUM_ENEMY];
        // 敵を作成
        enemy[0] = new Enemy(x[0], y[0], 10);
        enemy[1] = new Enemy(x[1], y[1], 10);
        enemy[2] = new Enemy(x[2], y[2], 10);
        enemy[3] = new Enemy(x[3], y[3], 10);
        enemy[4] = new Enemy(x[4], y[4], 10);
        enemy[5] = new Enemy(x[5], y[5], 10);

    	// 線を格納する配列を作成
        line = new Line[NUM_LINE];
        //線を作成
        line[0] = new Line(0.0d, y1, 800.0d, y1, "BLACK");
        line[1] = new Line(0.0d, y2, 800.0d, y2, "BLACK");

    	// 自機を格納する配列を作成
    	self = new Self[NUM_SELF];
        //自機を作成
        self[0] = new Self(-422, (int) y1, 9, 12);
        self[1] = new Self(1178, (int) y1, 9, 12);
        self[2] = new Self(378, (int )y1, 9, 12);

        // 文字列を格納する配列を作成
        title = new Strings[NUM_TITLE];
        end = new Strings[NUM_END];
        // 文字列を作成
        title[0] = new Strings("とんかつを作るにはな、", 250, 100, "メイリオ", 30);
        title[1] = new Strings("こうやって油の中に指を", 240, 200, "メイリオ", 30);
        title[2] = new Strings("アアアアアアアアアアーーーーーーーーー♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥♥", 0, 300, "メイリオ", 30);

        end[0] = new Strings("GAME OVER", 300, 100, "Arial", 30);
        end[1] = new Strings("PRESS ENTER TO TITLE", 300, 200, "Arial", 25);
        end[2] = new Strings("PRESS R TO RETRY", 300, 300, "Arial", 20);

        // スレッドを起動
        gameThread = new Thread(this);
		gameThread.start();
    }

	// ゲームスレッドのメイン
	public void run() {
		while (gameThread == Thread.currentThread()) {
			// ゲームメイン処理
			switch (scene) {
				case 0: GameTitle(); break;
				case 1: GameMain(); break;
				case 2: GameOver(); break;
			}
			// 20ミリ秒待つ
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch (scene) {
		case 0:
			//文字列を描画
			for (int l = 0; l < NUM_TITLE; l++) {
				title[l].paintComponent(g);
			}
			break;
		case 1:
			// 各敵を描画
			for (int i = 0; i < NUM_ENEMY; i++) {
				enemy[i].draw(g);
				}
			//線を描画
			for (int j = 0; j < NUM_LINE; j++) {
				line[j].paintComponent(g);
				}
			//自機を描画
			for (int ii = 0; ii < NUM_SELF; ii++) {
				self[ii].draw(g);
				}
			break;
		case 2:
			//文字列を描画
			for (int ll = 0; ll < NUM_END; ll++) {
				end[ll].paintComponent(g);
				}
			break;
			}
		}


	// ゲームタイトル処理
	public void GameTitle() {
		// キー入力の受け付け開始
		addKeyListener(this);
		// フォーカスを要求
		requestFocus();

		// エンターキーが押されたらシーンをゲームメインへ
		if (keyEnter) scene = 1;
	}

	// メインループ
	public void GameMain() {
		// プログラムが終了するまでフレーム処理を繰り返す
		while (true) {
			// キー入力の受け付け開始
			addKeyListener(this);
			// フォーカスを要求
			requestFocus();

			// 各敵を速度分だけ移動させる
			for (i = 0; i < NUM_ENEMY; i++) {
				enemy[i].move();
			}
			//自機の移動
			for (int ii = 0; ii < NUM_SELF; ii++) {
				self[ii].move();
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

	public void GameOver(){
		while(true){
			// キー入力の受け付け開始
			addKeyListener(this);
			// フォーカスを要求
			requestFocus();
			// Escが押されたらシーンをタイトルへ
			if (keyEsc){
				scene = 0;
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
    		// Enterキーが押されたとき
    		case KeyEvent.VK_ENTER: keyEnter = true; break;
    		// Escキーが押されたとき
    		case KeyEvent.VK_ESCAPE: keyEsc = true; break;
    	}
    }
 // キーが離されたときの処理
    public void keyReleased(KeyEvent e) {
    	switch (e.getKeyCode()) {
    		// 左キーが離されたとき
    		case KeyEvent.VK_LEFT: keyLeft = false; break;
    		// 右キーが離されたとき
    		case KeyEvent.VK_RIGHT: keyRight = false; break;
    		// Enterキーが離されたとき
    		case KeyEvent.VK_ENTER: keyEnter = false; break;
    		// Escキーが押されたとき
    		case KeyEvent.VK_ESCAPE: keyEsc = false; break;
    	}
    }

    // キーがタイプされたときの処理
    public void keyTyped(KeyEvent e) {}

}
