import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class StreamN {
	public static String str;
	private static File file = new File("score_easy.txt");
	private static String path = file.getAbsolutePath();
	public static void read(){
		try{
			// FileReaderオブジェクトの生成
		    FileReader fr = new FileReader(path);
		    // ファイル読み込み
			BufferedReader br = new BufferedReader(fr);
			// テキスト出力
			while((str = br.readLine()) != null) {
				MainPanel.hiscoreN = Double.parseDouble(str);
				MainPanel.hitimeN = str;
			}
			br.close();
		} catch(FileNotFoundException e) {
			System.out.println(e);
		} catch(IOException e) {
			System.out.println(e);
		}
	}
	public static void write(String s) {
        try {
        	// FileWriterオブジェクトの生成
        	FileWriter fw = new FileWriter(path);
        	// ファイル出力
			fw.write(s);
			// ファイルを閉じる
			fw.close();
        } catch(FileNotFoundException e) {
			System.out.println(e);
		} catch(IOException e) {
			System.out.println(e);
		}
	}
}