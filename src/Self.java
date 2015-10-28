import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;

public class Self extends Applet{
    // 自機の位置の左上の座標(x, y)
    private int x, y;
    //自機の幅, 高さ(WIDTH, HEIGHT)
    public static int WIDTH = 22, HEIGHT = 40;
    // 自機の速度 (vx, vy)
    protected int vx, vy;
    //自機画像(22*40px)
    private Image Self;

    public static int sx, sy;

    public void init() {
    	//画像読み込み(動かない)
    	Self = getImage(getCodeBase(), "Self.gif");
    }
    // コンストラクタ（新しい自機オブジェクトを作る工場）
    public Self(int x, int y, int vx, int vy) {
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
        sx = x;
        sy = y;
    }

    public void draw(Graphics g) {
    	//一時的に自機は四角形で代用
    	g.drawRect(x, y, WIDTH, HEIGHT);
    }
}

