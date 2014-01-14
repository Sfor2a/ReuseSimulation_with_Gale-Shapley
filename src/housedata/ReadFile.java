package housedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {
	
	private List < Housedata > HouseList = new ArrayList <> (); //家のリスト 
	
	public void setHouseList ( Housedata Hus ) { //ハウスリストセッター
		HouseList.add ( Hus ); 
	}
	public List < Housedata > getHouseList () { //Houseリストそのもののゲッター
		return HouseList;
	}
	public Housedata getHouseData ( int num ) { //指定番号の家のゲッター
		Housedata Hse = HouseList.get ( num );
		return Hse;
	}
	
	
	
	
	//エラー処理部
	private void Flug ( int num, int lineNum, String str ) { //フラグによるエラー処理
		switch ( num ) {
			case 1:
				System.out.print ( "Error: 複数のライフサイクル名が記述されています、どれか一つに統一してください" );
				break;
			case 2:
				System.out.print ( "Error: 同じ名前のステージがすでに存在しています" );
				break;
			case 3:
				System.out.print ( "Error: 同じ経路のパスがすでに存在しています" );
				break;
			case 4:
				System.out.print ( "Error: 存在しないステージを参照しようとするパスです。　パスの名前を修正するか、綴りを確認してください" );
				break;
			case 5:
				System.out.print ( "Error: 読み込みに関係のない行が挿入されています。　この行は読み飛ばします。　スペルミス、書式をチェックしてください" );
				break;
			case 6:
				System.out.println ( "Error: パスの記述書式に問題があります つなげられるステージはパス一本につき、二つです" );
				break;
			default:
				System.out.print ( "Error: その他のエラーが発生しています。　該当する行を確認するか、管理者に問い合わせてください" );
				break;
		}
		System.out.println ( "( 行番号" + lineNum + ": " + str + " )" ); //エラーメッセージを出した後は、必ずそのエラーのある行をしめす
	}
	//実処理部
	
	private void reedingHouseName ( String str, int countLine ) { //家の名前を読み込んだよ　家をつくるよ
		try {
			String StAry[] = str.split ( " ", 2 ); //"Housename "を除去
			String Housename= ".\\recycle\\housedata\\" + StAry[1] + "_data.txt";
			//ファイルリーダー
			 File file = new File ( Housename );
			 BufferedReader br = new BufferedReader ( new FileReader ( file ) );
			//ここまでファイルリーダー
			 
			 String str1 = "0" ; //読み取り用String関数 ここをヌルにすると止まる（当たり前だ）
			 int CountLine_1 = 0; //行番号加算変数（エラー処理用）
			 String[] HouseAry = new String[2];
			
			//読み込みメソッド
			while ( str1 != null ) { //EOFをnullとして検出、それまでは読み込み続ける
				str1 = br.readLine (); CountLine_1++; //一行を読み込む 読み込むたびに次のif文シーケンスで判定、同時に行番号加算
				if (str1 != null) { //読み込み行がnullでなければこのシーケンスを実行、行頭で判別する
					if ( str1.startsWith ( "Name " ) ) {
						String[] strAry = str1.split ( " ", 2 );
						HouseAry[0] = strAry[1];
					}
					else if ( str1.startsWith ( "Coin " ) ) {
						String[] strAry = str1.split ( " ", 2 );
						HouseAry[1] = strAry[1];		
					}
					else if ( str1.startsWith ( "//" ) ); //コメント文を検出すると何もしない
					else Flug ( 5, CountLine_1, str1 ); //謎の文章を読み込むとエラーを表示して無視
				}			
			}
			//ここまで読み込みメソッド
			br.close (); //ファイルを閉じる
			new Housedata ( HouseAry[0], Integer.parseInt ( HouseAry[1] ), this ); //家の作成
		} catch ( FileNotFoundException e ) { //例外処理
			System.err.println ( e.getMessage () ); //ファイルなしの例外	
			System.exit ( 0 );
		} catch ( IOException e ) {
		    System.err.println ( e.getMessage () ); //IOエラー例外
		    System.exit ( 0 );
		}
	}
	
	//データ読み込み　こいつが外から呼び出される
	public void CreatefromFile ( String DataFolder ) { //引数にファイルの位置を投げるとファイルからライフサイクルを生成 こいつに渡されるのは家のリスト
		try {
			//ファイルリーダー
			 File file = new File ( DataFolder );
			 BufferedReader br = new BufferedReader ( new FileReader ( file ) );
			//ここまでファイルリーダー
			 
			 String str = "0" ; //読み取り用String関数 ここをヌルにすると止まる（当たり前だ）
			 int CountLine = 0; //行番号加算変数（エラー処理用）
			
			//読み込みメソッド
			while ( str != null ) { //EOFをnullとして検出、それまでは読み込み続ける
				str = br.readLine (); CountLine++; //一行を読み込む 読み込むたびに次のif文シーケンスで判定、同時に行番号加算
				if (str != null) { //読み込み行がnullでなければこのシーケンスを実行、行頭で判別する
					if ( str.startsWith ( "HouseName " ) ) reedingHouseName ( str, CountLine ); //家の名前の読み込み
					else if ( str.startsWith ( "//" ) ); //コメント文を検出すると何もしない
					else Flug ( 5, CountLine, str ); //謎の文章を読み込むとエラーを表示して無視
				}			
			}
			//ここまで読み込みメソッド
			
			br.close(); //ファイルを閉じる			
		} catch ( FileNotFoundException e ) { //例外処理
			System.err.println ( e.getMessage () ); //ファイルなしの例外	
			System.exit ( 0 );
		} catch ( IOException e ) {
		    System.err.println ( e.getMessage () ); //IOエラー例外
		    System.exit ( 0 );
		}
	}

}
