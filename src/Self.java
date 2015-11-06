import java.awt.Graphics;

import javax.swing.JPanel;

public class Self extends JPanel{
	// 自機の位置の左上の座標(x, y)
	private int x, y;
	// 自機の幅, 高さ(WIDTH, HEIGHT)
	public static final int WIDTH = 32, HEIGHT = 42;
	// 自機の速度 (vx, vy)
	private int vx, vy;
	// グローバル変数
	static int sx = 0, sy = 0;

	// コンストラクタ（新しい自機オブジェクトを作る工場）
	public Self(int x, int y, int vx, int vy) {
		// 敵の属性を設定
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
	}

	public void move(int dir) {
		// グローバル変数に代入
		sx = x;
		sy = y;
		// 自機を速度分だけ移動させる
		y += vy;

		// 左移動
		if (dir == 1) {
			x -= vx;
			if (x < 0) x = 0;
		}

		// 右移動
		if (dir == 2) {
			this.x += vx;
			if (x > MainPanel.WIDTH - WIDTH) x = MainPanel.WIDTH - WIDTH;
		}

		// 上または下のLineに当たったらy方向速度の符号を反転させる
		if (y < MainPanel.y1 || y > MainPanel.y2 - HEIGHT) {
			vy = -vy;
		}
	}

	public void draw(Graphics g) {
		g.drawImage(MainPanel.Simg, x, y, WIDTH, HEIGHT, this);
	}
}

