import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.io.InputStream;

public class Strings {
	// フォントの種類, 大きさ
	private String FONT;
	private int SIZE;
	// 文字列の始点の座標 (x,y)
	private int x, y;
	// 描画する文字列
	private String str;
	private String color;

	/*public void font(){
		Font DSEG = createFont("DSEG7Classic-Bold.ttf");
		GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(DSEG);
	}*/

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
		//}
		// 色を設定
		if(color.equals("GREEN")){
			g2.setColor(Color.GREEN);
		}else{
			g2.setColor(Color.WHITE);
		}

		// 文字列を描画
		g2.drawString(str, x, y);
	}
	public Font createFont(String filename){
		Font font = null;
		InputStream is = null;
		try {
		is = getClass().getResourceAsStream(filename);
		font = Font.createFont(Font.TRUETYPE_FONT, is);
		is.close();
		}catch(IOException e){
			e.printStackTrace();
		}catch(FontFormatException e){
			e.printStackTrace();
		}
		return font;
	}
}

