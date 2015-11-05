import java.awt.Container;

import javax.swing.JFrame;

public class FesGame extends JFrame {

	public FesGame() {
		// タイトルを設定
		setTitle("仮完成");
		// サイズ変更禁止
		setResizable(false);

		// パネルを作成してフレームに追加
		MainPanel panel = new MainPanel();
		Container contentPane = getContentPane();
		contentPane.add(panel);
		// パネルサイズに合わせてフレームサイズを自動設定
		pack();
	}

	public static void main(String[] args) {
		// フレームをセット
		FesGame frame = new FesGame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
