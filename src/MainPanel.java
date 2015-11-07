import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainPanel extends JPanel implements Runnable, KeyListener {
	// パネルサイズ
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	// 線のy座標
	public static final double y1 = 140.0d, y2 = 460.0d;
	//キー押下フラグ
	private boolean keyLeft, keyRight, keyEnter, keyEsc, keyR, keyShift;
	// 敵, 線の数
	public static final int NUM_ENEMY = 6;
	private static final int NUM_LINE = 2;
	private static final int NUM_TITLE = 5;
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
	public static final int y[] = {160, 210, 260, 310, 360, 410};
	// アニメーション用スレッド
	Thread gameThread = null;
	// シーン(0:GameTitle 1:GameMain 2:GameOver)
	static int scene = 0;
	// 秒数
	private double sec = 0.00;
	// タイマー
	public static Timer timer;
	// 時間表示用String
	private String time;
	static double hiscore;
	static String hitime;
	// 画像
	static Image Simg, Eimg;
	static AudioClip se, dddn, bgm;
	private MediaTracker tracker;
	private BufferedImage back0, back1, back2, over;


	public MainPanel() {
		// パネルの推奨サイズを設定、pack()するときに必要
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setSize(WIDTH, HEIGHT);
		//音声読み込み
		se = Applet.newAudioClip(this.getClass().getResource("line.wav"));
		dddn = Applet.newAudioClip(this.getClass().getResource("death.wav"));
		bgm = Applet.newAudioClip(this.getClass().getResource("bgm.wav"));
		// 画像読み込み
		Simg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("self.gif"));
		Eimg = Toolkit.getDefaultToolkit().getImage(getClass().getResource("cage.gif"));
		// MediaTrackerに登録
		tracker = new MediaTracker(this);
		tracker.addImage(Simg, 0);
		tracker.addImage(Eimg, 0);
		// イメージ読み込み完了まで待機
		try {
			tracker.waitForID(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try{
			this.back0 = ImageIO.read(getClass().getResource("back0.jpg"));
			this.back1 = ImageIO.read(getClass().getResource("back1.jpg"));
			this.back2 = ImageIO.read(getClass().getResource("back2.jpg"));
			this.over = ImageIO.read(getClass().getResource("gameover.gif"));
		} catch (IOException ex) {
			ex.printStackTrace();
			this.back0 = null;
			this.back1 = null;
			this.back2 = null;
			this.over = null;
		}
		Stream.read();

		if(scene == 0 || scene == 1){
			// 敵を格納する配列を作成
			enemy = new Enemy[NUM_ENEMY];
			// 敵を作成
			enemy[0] = new Enemy(x[0], y[0], 9);
			enemy[1] = new Enemy(x[1], y[1], 9);
			enemy[2] = new Enemy(x[2], y[2], 9);
			enemy[3] = new Enemy(x[3], y[3], 9);
			enemy[4] = new Enemy(x[4], y[4], 9);
			enemy[5] = new Enemy(x[5], y[5], 9);

			// 線を格納する配列を作成
			line = new Line[NUM_LINE];
			// 線を作成
			line[0] = new Line(0.0d, y1, 800.0d, y1, "WHITE");
			line[1] = new Line(0.0d, y2, 800.0d, y2, "WHITE");

			// 自機を作成
			self = new Self(389, (int)y1, 8, 12);

			// 文字列を格納する配列を作成
			title = new Strings[NUM_TITLE];
			end = new Strings[NUM_END];
			// 文字列を作成
			title[0] = new Strings("DeDe DOOM", 272, 200, "メイリオ", 40, "WHITE");
			title[1] = new Strings("ﾃﾞﾈ!ﾃﾞﾈﾃﾞﾈ!ﾃﾞﾈﾈ、ﾃﾞﾃﾞﾃﾞﾈﾃﾞ!ﾃﾞﾈﾈﾃﾞﾈ!", 150, 300, "メイリオ", 30, "WHITE");
			title[2] = new Strings("(左右キーで飛んでくる　　を避けてね)", 140, 360, "メイリオ", 30, "WHITE");
			title[3] = new Strings("Enterキーでスタート", 258, 420, "メイリオ", 30, "WHITE");
			title[4] = new Strings("Created by @ikasumi_meron", 550, 580, "Arial", 18, "WHITE");

			end[0] = new Strings("GAME OVER", 272, 200, "Arial", 40, "BLACK");
			end[1] = new Strings("Escキーでタイトル", 270, 400, "メイリオ", 30, "WHITE");
			end[2] = new Strings("Rキーでリトライ", 286, 450, "メイリオ", 30, "WHITE");

			gameThread = new Thread(this);
			gameThread.start();
		}
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
			// 背景を描画
			g.drawImage(back0, 0, 0,this);
			// デデンネ
			g.drawImage(Simg, 384, 219, this);
			g.drawImage(Eimg, 464, 332, this);
			// 文字列を描画
			for (int l = 0; l < NUM_TITLE; l++) {
				title[l].paintComponent(g);
			}
			break;
		case 1:
			// 背景を描画
			g.drawImage(back1, 0, 0,this);
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
			// 背景を描画
			g.drawImage(back2, 0, 0,this);
			// デデンネ
			g.drawImage(over, 376, 215,this);
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
		bgm.loop();
		// 時間計測開始
		long t1 = System.nanoTime();
		// プログラムが終了するまでフレーム処理を繰り返す
		while (scene != 2) {
			// キー入力の受け付け開始
			addKeyListener(this);
			// フォーカスを要求
			requestFocus();

			// 各敵を速度分だけ移動させる
			for (int i = 0; i < NUM_ENEMY; i++) {
				enemy[i].move();
			}
			// 自機の移動
			if(keyLeft) {
				self.move(1);
			}else if(keyRight) {
				self.move(2);
			}else{
				self.move(0);
			}
			// 上または下のLineに当たったらy方向速度の符号を反転させる
			if (Self.sy < y1){
				se.stop();
				se.play();
			}
			if(Self.sy > y2 - Self.HEIGHT) {
				se.stop();
				se.play();
			}

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
				end[3] = new Strings("HISCORE:" + time + "秒", 314, 343, "メイリオ", 20, "GREEN");
				direction = new Strings("HISCORE:" + time + "sec", 600, 110, "Arial", 20, "GREEN");
			}else{
				end[3] = new Strings("HISCORE:" + hitime + "秒", 314, 343, "メイリオ", 20, "WHITE");
				direction = new Strings("HISCORE:" + hitime + "sec", 600, 110, "Arial", 20, "WHITE");
			}

			// 時間を表示する文字列の作成
			end[4] = new Strings(time + "秒逃げ切りました。", 232, 300, "メイリオ", 30, "WHITE");
			clock = new Strings(time, 330, 100, "DSEG7 Classic", 50, "WHITE");
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
		Stream.write(hitime);
		bgm.stop();
		System.gc();
		// キー入力の受け付け開始
		addKeyListener(this);
		// フォーカスを要求
		requestFocus();
		// Shift+Enterでハイスコアをリセット
		if (keyEnter && keyShift){
			Stream.write("0");
			Stream.read();
			se.play();
		}
		// Escが押されたらシーンをタイトルへ
		if (keyEsc){
			// 敵を初期化
			enemy[0] = new Enemy(x[0], y[0], 9);
			enemy[1] = new Enemy(x[1], y[1], 9);
			enemy[2] = new Enemy(x[2], y[2], 9);
			enemy[3] = new Enemy(x[3], y[3], 9);
			enemy[4] = new Enemy(x[4], y[4], 9);
			enemy[5] = new Enemy(x[5], y[5], 9);
			// 自機を初期化
			self = new Self(378, (int )y1, 8, 10);
			// シーンをタイトルに
			scene = 0;
			repaint();
		}
		if (keyR){
			// 敵を初期化
			enemy[0] = new Enemy(x[0], y[0], 9);
			enemy[1] = new Enemy(x[1], y[1], 9);
			enemy[2] = new Enemy(x[2], y[2], 9);
			enemy[3] = new Enemy(x[3], y[3], 9);
			enemy[4] = new Enemy(x[4], y[4], 9);
			enemy[5] = new Enemy(x[5], y[5], 9);
			// 自機を初期化
			self = new Self(378, (int )y1, 8, 10);
			// シーンをタイトルに
			scene = 1;
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
			// Shiftキーが押されたとき
			case KeyEvent.VK_SHIFT: keyShift = true; break;
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
			// Shiftキーが押されたとき
			case KeyEvent.VK_SHIFT: keyShift = true; break;
		}
	}

	// キーがタイプされたときの処理
	public void keyTyped(KeyEvent e) {}
}
