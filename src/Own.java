import java.applet.Applet;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Own extends Applet implements KeyListener{
    // 自機の位置の左上の座標(x, y)
    private int x, y;
    //自機の幅, 高さ(WIDTH, HEIGHT)
    private int WIDTH = 22, HEIGHT = 40;
    // 自機の速度 (vx, vy)
    protected int vx, vy;
    //キー押下フラグ
    private boolean keyLeft, keyRight;
    //自機画像(22*40px)
    private Image own;

    public void init() {
    	// キーフラグをクリア
    	keyLeft = keyRight = false;
    	// 画像読み込み
    	own = getImage(getDocumentBase(), "Own.gif");
    	// キー入力の受け付け開始
    	addKeyListener(this);
    	// フォーカスを要求
    	requestFocus();
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
     	if (keyLeft) {
     		x -= vx;
     		if (x < 0) x = 0;
     	}
     	// 右移動
     	if (keyRight) {
     		x += vx;
     		if (x > MainPanel.WIDTH - WIDTH) x = MainPanel.WIDTH - WIDTH;
     	}

     // 上または下のLineに当たったらy方向速度の符号を反転させる
        if (y < MainPanel.y1 || y > MainPanel.y2 - HEIGHT) {
            vy = -vy;
        }
    }


    // キーが押されたときの処理
    public void keyPressed(KeyEvent e) {
    	switch (e.getKeyCode()) {
			// 左キーが押されたとき
    		case KeyEvent.VK_LEFT: keyLeft = true; break;
    		// 右キーが押されたとき
    		case KeyEvent.VK_RIGHT: keyRight = true; break;
    	}
    }
 // キーが離されたときの処理
    public void keyReleased(KeyEvent e) {
    	switch (e.getKeyCode()) {
    		// 左キーが離されたとき
    		case KeyEvent.VK_LEFT: keyLeft = false; break;
    		// 右キーが離されたとき
    		case KeyEvent.VK_RIGHT: keyRight = false; break;
    	}
    }

    // キーがタイプされたときの処理
    public void keyTyped(KeyEvent e) {}

    public void paint(Graphics g) {
    	g.drawImage(own, x, y, this);
    }
}

