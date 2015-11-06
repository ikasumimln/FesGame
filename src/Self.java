import java.awt.Graphics;

import javax.swing.JPanel;

public class Self extends JPanel{
	// 自機の位置の左上の座標(x, y)
	private int x, y;
	// 自機の幅, 高さ(WIDTH, HEIGHT)
	public static int WIDTH = 24, HEIGHT = 44;
	// 自機の速度 (vx, vy)
	protected int vx, vy;
	// 自機画像(22*40px)
	// グローバル変数
	public static int sx, sy;

	// コンストラクタ（新しい自機オブジェクトを作る工場）
	public Self(int x, int y, int vx, int vy) {
		// 敵の属性を設定
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}

	public void move() {
		// グローバル変数に代入
		sx = x;
		sy = y;
		// 敵を速度分だけ移動させる
		y += vy;

		// 左移動
		if (MainPanel.keyLeft) {
			x -= vx;
			if (x < 0) x = 0;
		}

		// 右移動
		if (MainPanel.keyRight) {
			this.x += vx;
			if (x > MainPanel.WIDTH - WIDTH) x = MainPanel.WIDTH - WIDTH;
		}

		// 上または下のLineに当たったらy方向速度の符号を反転させる
		if (y < MainPanel.y1 || y > MainPanel.y2 - HEIGHT) {
			vy = -vy;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(MainPanel.img, x, y, WIDTH, HEIGHT, this);
	}
}

