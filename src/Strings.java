import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Strings {
	// フォントの種類, 大きさ
	private String FONT;
	private int SIZE;
	// 線の始点と終点の座標 (x,y)
    private int x, y;
    // 描画する文字列
    private String str;

    // コンストラクタ（新しい線オブジェクトを作る工場）
    public Strings(String str, int x, int y, String FONT, int SIZE) {
        // 線の属性を設定
    	this.str = str;
        this.x = x;
        this.y = y;
        this.FONT = FONT;
        this.SIZE = SIZE;

    }

    //線の描画
    public void paintComponent(Graphics g){
    	Graphics2D g2 = (Graphics2D)g;
    	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
    			RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

    	Font font = new Font(FONT, Font.PLAIN, SIZE);
    	g2.setFont(font);

    	g2.drawString(str, x, y);
    }
}
