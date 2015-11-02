import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements Runnable, KeyListener {
	// パネルサイズ
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	// 線のy座標
	public static final double y1 = 140.0d, y2 = 460.0d;
	//キー押下フラグ
	public static boolean keyLeft, keyRight, keyEnter, keyEsc, keyR;
	// 敵, 線の数
	public static final int NUM_ENEMY = 6;
	private static final int NUM_LINE = 2;
	private static final int NUM_TITLE = 4;
	private static final int NUM_END = 5;
	// 敵, 自機, 文字列を格納する配列
	private Enemy[] enemy;
	private Line[] line;
	private Self self;
	private Strings[] title;
	private Strings[] end;
	private Strings clock;
	private Strings direction;
	// 敵の初期座標(x, y)
	private int x[] = {-30, -230, -430, -630, -830, -1030};
	public static int y[] = {160, 210, 260, 310, 360, 410};
	// アニメーション用スレッド
	Thread gameThread;
	// シーン(0:GameTitle 1:GameMain 2:GameOver)
	public static int scene = 0;
	// 秒数
	public static double sec = 0.00;
	// タイマー
	public static Timer timer;
	// 時間表示用String
	private String time;
	// ループ用i
	public static int i;
	private double hiscore = 0;
	private String hitime = "0";

	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);

		if(scene == 0 || scene == 1){
			// 敵を格納する配列を作成
			enemy = new Enemy[NUM_ENEMY];
			// 敵を作成
			enemy[0] = new Enemy(x[0], y[0], 11);
			enemy[1] = new Enemy(x[1], y[1], 11);
			enemy[2] = new Enemy(x[2], y[2], 11);
			enemy[3] = new Enemy(x[3], y[3], 11);
			enemy[4] = new Enemy(x[4], y[4], 11);
			enemy[5] = new Enemy(x[5], y[5], 11);

			// 線を格納する配列を作成
			line = new Line[NUM_LINE];
			// 線を作成
			line[0] = new Line(0.0d, y1, 800.0d, y1, "BLACK");
			line[1] = new Line(0.0d, y2, 800.0d, y2, "BLACK");

			// 自機を作成
			self = new Self(389, (int )y1, 9, 14);

			// 文字列を格納する配列を作成
			title = new Strings[NUM_TITLE];
			end = new Strings[NUM_END];
			// 文字列を作成
			title[0] = new Strings("＼にゃんぱすー／", 280, 100, "メイリオ", 30, "BLACK");
			title[1] = new Strings("左から右へ受け流すのん", 239, 200, "メイリオ", 30, "BLACK");
			title[2] = new Strings("左右キーで移動なん", 269, 300, "メイリオ", 30, "BLACK");
			title[3] = new Strings("Enterキーで開始なのん", 245, 400, "メイリオ", 30, "BLACK");

			end[0] = new Strings("GAME OVER", 267, 200, "Arial", 40, "BLACK");
			end[1] = new Strings("Escキーでタイトル", 262, 400, "メイリオ", 30, "BLACK");
			end[2] = new Strings("Rキーでリトライ", 279, 450, "メイリオ", 30, "BLACK");
		}

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
		// scene別描画処理
		switch (scene) {
		case 0:
			// 文字列を描画
			for (int l = 0; l < NUM_TITLE; l++) {
				title[l].paintComponent(g);
			}
			break;
		case 1:
			// 各敵を描画
			for (int i = 0; i < NUM_ENEMY; i++) {
				enemy[i].draw(g);
				}
			// 線を描画
			for (int j = 0; j < NUM_LINE; j++) {
				line[j].paintComponent(g);
				}
			// 自機を描画
			self.draw(g);
			clock.paintComponent(g);
			direction.paintComponent(g);
			break;
		case 2:
			// 文字列を描画
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
		// 時間計測開始
		long t1 = System.nanoTime();
		// プログラムが終了するまでフレーム処理を繰り返す
		while (scene != 2) {
			// キー入力の受け付け開始
			addKeyListener(this);
			// フォーカスを要求
			requestFocus();

			// 各敵を速度分だけ移動させる
			for (i = 0; i < NUM_ENEMY; i++) {
				enemy[i].move();
			}
			// 自機の移動
			self.move();
			// 再描画
			repaint();
			// 現在の経過時間を代入
			long t2 = System.nanoTime();
			sec = (t2 - t1)/1000000000.0;
			// 有効桁数を小数点以下2位に
			NumberFormat format = NumberFormat.getInstance();
			format.setMaximumFractionDigits(2);
			time = (format.format(sec));
			if(sec > hiscore){
				hiscore = sec;
				hitime = time;
				end[3] = new Strings("HISCORE:" + time + "秒", 310, 350, "メイリオ", 20, "RED");
				direction = new Strings("HISCORE:" + time + "sec", 600, 110, "Arial", 20, "RED");
			}else{
				end[3] = new Strings("HISCORE:" + hitime + "秒", 310, 350, "メイリオ", 20, "BLACK");
				direction = new Strings("HISCORE:" + hitime + "sec", 600, 110, "Arial", 20, "BLACK");
			}

			// 時間を表示する文字列の作成
			end[4] = new Strings(time + "秒逃げ切りました。", 232, 300, "メイリオ", 30, "BLACK");
			clock = new Strings(time, 330, 100, "DSEG7 Classic", 50, "BLACK");
			// 確認用 System.out.println(t2-t1);

			// 20ミリ秒だけ休止
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void GameOver(){
		// キー入力の受け付け開始
		addKeyListener(this);
		// フォーカスを要求
		requestFocus();
		// Escが押されたらシーンをタイトルへ
		if (keyEsc){
			// シーンをタイトルに
			scene = 0;
			// 敵を初期化
			enemy[0] = new Enemy(x[0], y[0], 11);
			enemy[1] = new Enemy(x[1], y[1], 11);
			enemy[2] = new Enemy(x[2], y[2], 11);
			enemy[3] = new Enemy(x[3], y[3], 11);
			enemy[4] = new Enemy(x[4], y[4], 11);
			enemy[5] = new Enemy(x[5], y[5], 11);

			// 自機を初期化
			self = new Self(378, (int )y1, 9, 15);
			// 再描画
			repaint();
		}
		if (keyR){
			// シーンをタイトルに
			scene = 1;
			// 敵を初期化
			enemy[0] = new Enemy(x[0], y[0], 11);
			enemy[1] = new Enemy(x[1], y[1], 11);
			enemy[2] = new Enemy(x[2], y[2], 11);
			enemy[3] = new Enemy(x[3], y[3], 11);
			enemy[4] = new Enemy(x[4], y[4], 11);
			enemy[5] = new Enemy(x[5], y[5], 11);

			// 自機を初期化
			self = new Self(378, (int )y1, 9, 15);
			// 再描画
			repaint();
		}
	}

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
			// Rキーが押されたとき
			case KeyEvent.VK_R: keyR = true; break;
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
			// Escキーが離されたとき
			case KeyEvent.VK_ESCAPE: keyEsc = false; break;
			// Rキーが離されたとき
			case KeyEvent.VK_R: keyR = false; break;
		}
	}

	// キーがタイプされたときの処理
	public void keyTyped(KeyEvent e) {}
}
