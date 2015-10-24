import java.awt.Color;
import java.awt.Graphics;

public class Enemy {
	// 敵の大きさ
    private static final int SIZE = 30;
    // 敵の位置 (x, y) □の左上の座標
    private int x, y;
    // 敵の速度 (vx, vy)
    protected int vx, vy;

    // コンストラクタ（新しい敵オブジェクトを作る工場）
    public Enemy(int x, int y, int vx, int vy) {
        // 敵の属性を設定
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void move() {
    	// 敵を速度分だけ移動させる
        x += vx;
        y += vy;

     // 左または右に当たったらx方向速度の符号を反転させる
        if (x < 0 || x > MainPanel.WIDTH - SIZE) {
            vx = -vx;
        }

     // 上または下に当たったらy方向速度の符号を反転させる
        if (y < 0 || y > MainPanel.HEIGHT - SIZE) {
            vy = -vy;
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawRect(x, y, SIZE, SIZE);
    }
}
