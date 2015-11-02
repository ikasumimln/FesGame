import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

public class Line {
	// 線の太さ
	private static final float WIDTH = 3.0f;
	// 線の始点と終点の座標 (x1, y1, x2, y2)
	private double x1, y1, x2, y2;
	// 線の色
	private String color;

	// コンストラクタ（新しい線オブジェクトを作る工場）
	public Line(double x1, double y1, double x2, double y2, String color) {
		// 線の属性を設定
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.color = color;
	}

	// 線の描画
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		// アンチエイリアスを有効化
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// 色の指定
		g2.setPaint(Color.getColor(color));
		// 太さの指定
		BasicStroke wideStroke = new BasicStroke(WIDTH);
		g2.setStroke(wideStroke);
		// 線を描画
		g2.draw(new Line2D.Double(x1, y1, x2, y2));
	}
}

