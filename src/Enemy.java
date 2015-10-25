import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	// 敵の大きさ
    public static final int SIZE = 30;
    // 敵の位置 (x, y) □の左上の座標
    private int x, y;
    // 敵の速度 (vx, vy)
    protected int vx, vy;

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

        //初期座標をランダムに
		if (x >= MainPanel.WIDTH) {
			y = (int)(Math.random() * 5);
			x = -SIZE;
		}
        /* 左または右に当たったらx方向速度の符号を反転させる
        if (x < 0 || x > MainPanel.WIDTH - SIZE) {
            vx = -vx;
        }
        */
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, SIZE, SIZE);
    }
}
