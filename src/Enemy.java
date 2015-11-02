import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	// 敵の大きさ
	public static final int SIZE = 30;
	// 敵の位置 (x, y) □の左上の座標
	private int x, y;
	// 敵の速度 (vx)
	private int vx;
	//グローバル変数を作成
	public static int ex,ey;

	// コンストラクタ（新しい敵オブジェクトを作る工場）
	public Enemy(int x, int y, int vx) {
		// 敵の属性を設定
		this.x = x;
		this.y = y;
		this.vx = vx;
	}

	public void move() {
		// グローバル変数に代入
		ex = x;
		ey = y;
		// 敵を速度分だけ移動させる
		x += vx;

		// 初期座標をランダムに
		if (x >= MainPanel.WIDTH) {
			x = -430;
			y = (int)(Math.random() * (MainPanel.y2 - MainPanel.y1 - SIZE))+ (int)MainPanel.y1;
			//y = MainPanel.y[MainPanel.i];
		}
		// 自機と敵の当たり判定
		if(Self.sx + Self.WIDTH > ex &&
			Self.sx < ex + SIZE &&
				Self.sy + Self.HEIGHT > ey &&
				Self.sy < ey + SIZE) {
					// 敵に触れるとシーンをGameOve　rへ
					MainPanel.scene = 2;
			}
	}
	// 敵の描画
	public void draw(Graphics g) {
		//色を黒に
		g.setColor(Color.BLACK);
		// 敵の四角形を描画
		g.drawRect(x, y, SIZE, SIZE);
	}
}
