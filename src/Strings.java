import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Strings {
	// フォントの種類, 大きさ
	private String FONT;
	private int SIZE;
	// 文字列の始点の座標 (x,y)
	private int x, y;
	// 描画する文字列
	private String str;
	private String color;

	// コンストラクタ（新しい線オブジェクトを作る工場）
	public Strings(String str, int x, int y, String FONT, int SIZE, String color) {
		// 線の属性を設定
		this.str = str;
		this.x = x;
		this.y = y;
		this.FONT = FONT;
		this.SIZE = SIZE;
		this.color = color;

	}

	// 文字列の描画
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		// アンチエイリアスを有効化
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		// フォントを設定
		Font font = new Font(FONT, Font.PLAIN, SIZE);
		g2.setFont(font);
		// 色を設定
		g2.setColor(Color.getColor(color));
		// 文字列を描画
		g2.drawString(str, x, y);
	}
}
