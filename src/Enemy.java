import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Graphics;

import javax.swing.JPanel;

public class Enemy extends JPanel{
	// 敵の大きさ
	public static final int SIZE = 30;
	// 敵の位置 (x, y) □の左上の座標
	private int x, y;
	// 敵の速度 (vx)
	private int vx;
	private AudioClip se = Applet.newAudioClip(this.getClass().getResource("death.wav"));

	// コンストラクタ（新しい敵オブジェクトを作る工場）
	public Enemy(int x, int y, int vx) {
		// 敵の属性を設定
		this.x = x;
		this.y = y;
		this.vx = vx;
	}

	public void move() {
		// 敵を速度分だけ移動させる
		x += vx;

		// 初期座標をランダムに
		if (x >= MainPanel.WIDTH) {
			x = -430;
			y = (int)(Math.random() * (MainPanel.y[5] - MainPanel.y[0])) + (int)MainPanel.y[0];
		}
		// 自機と敵の当たり判定
		if(Self.sx + Self.WIDTH >= x &&
			Self.sx <= x + SIZE &&
			Self.sy + Self.HEIGHT >= y &&
			Self.sy <= y + SIZE) {
			// 敵に触れるとシーンをGameOverへ
			MainPanel.scene = 2;
			// デデドン(絶望)
			se.play();
		}
	}
	// 敵の描画
	public void draw(Graphics g) {
		g.drawImage(MainPanel.Eimg, x-2, y-2, this);
	}
}