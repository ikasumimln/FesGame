import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Own extends Applet{
    // 自機の位置の左上の座標(x, y)
    private int x, y;
    //自機の幅, 高さ(WIDTH, HEIGHT)
    private int WIDTH = 22, HEIGHT = 35;
    // 自機の速度 (vx, vy)
    protected int vx, vy;
    //自機画像(22*40px)
    private Image own;

    public void init() {
    	//画像読み込み(動かない)
    	own = getImage(getCodeBase(), "Own.gif");
    }
    // コンストラクタ（新しい自機オブジェクトを作る工場）
    public Own(int x, int y, int vx, int vy) {
        // 敵の属性を設定
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
    }

    public void move() {
    	// 敵を速度分だけ移動させる
        y += vy;

        // 左移動
     	if (MainPanel.keyLeft) {
     		x -= vx;
     	}
     	// 右移動
     	if (MainPanel.keyRight) {
     		x += vx;
     	}

     // 上または下のLineに当たったらy方向速度の符号を反転させる
        if (y < MainPanel.y1 || y > MainPanel.y2 - HEIGHT) {
            vy = -vy;
        }
    }

    public void draw(Graphics g) {
    	//一時的に自機は四角形で代用
    	g.drawRect(x, y, WIDTH, HEIGHT);
    }
}

