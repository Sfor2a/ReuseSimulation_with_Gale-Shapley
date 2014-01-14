package mapdata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class ConnectPoint extends ConnectPoint_Elements {
	private List < Point > PointList = new ArrayList <> (); //ポイントそのもののリスト
	private List < Routedata > RouteList = new ArrayList <> (); //道のリスト

	//セッター部
	public void setPoint ( Point Pit ) { //ポイントリストのセッター
		PointList.add ( Pit );
	}
	public void setRoute ( Routedata Rte ) { //道リストのセッター
		RouteList.add ( Rte );
	}
	//ここまでセッター部
	
	//ゲッター部
	public List < Point > getPitList () { //PointListそのものをもってくるゲッター
		List < Point > PitList = PointList; 
		return PitList;	
	}
	public List < Routedata > getRteList () { //RouteListそのものをもってくるゲッター
		List < Routedata > RteList = RouteList; 
		return RteList;	
	}
	public Point getPoint ( int num ) { //指定番号のポイントのゲッター
		Point Pit = PointList.get ( num );
		return Pit;
	}
	public Routedata getRoute ( int num ) { //指定番号の道のゲッター
		Routedata Rte = RouteList.get ( num );
		return Rte;
	}
	//ここまでゲッター部
	
	private void Flug ( int num, int lineNum, String str ) { //フラグによるエラー処理
		switch ( num ) {
			case 1:
				System.out.print ( "Error: 複数のマップ名が記述されています、どれか一つに統一してください" );
				break;
			case 2:
				System.out.print ( "Error: 同じ名前のポイントがすでに存在しています" );
				break;
			case 3:
				System.out.print ( "Error: 同じ経路の道がすでに存在しています" );
				break;
			case 4:
				System.out.print ( "Error: 存在しないポイントを参照しようとする道です。　道の名前を修正するか、綴りを確認してください" );
				break;
			case 5:
				System.out.print ( "Error: 読み込みに関係のない行が挿入されています。　この行は読み飛ばします。　スペルミス、書式をチェックしてください" );
				break;
			case 6:
				System.out.println ( "Error: 道の記述書式に問題があります つなげられるポイントは道一本につき、二つです" );
				break;
			default:
				System.out.print ( "Error: その他のエラーが発生しています。　該当する行を確認するか、管理者に問い合わせてください" );
				break;
		}
		System.out.println ( "( 行番号" + lineNum + ": " + str + " )" ); //エラーメッセージを出した後は、必ずそのエラーのある行をしめす
	}
	
	private Point findNamedPoint ( List < Point > Pit, String Name, String str, int CountLine ) { //同じ名前のポイントを見つけるメソッド
		Point C = null; //用意してあげないとEclipseがエラー吐く
		for ( Iterator < Point > i = Pit.iterator (); i.hasNext (); ) { //PointListの中身全部に対して検索をかける
			Point B = i.next ();
			if ( Name.equals ( B.getName () ) ) return C = B; //同じポイントを見つけたらCへ代入しこれを返す
		}
		if ( C == null ) Flug ( 4, CountLine, str ); //nullの場合のエラー処理、フラグをエラー処理メソッドへ投げる
		return C;
	}
	
	private int PointDuplicationSearch ( String PitDupName ) { //ポイント名の重複検索
		List < Point > SearchList = getPitList (); //既存のポイントリストを読み込む
		int findExistingName = -1; //既存名前判定フラグ
		if ( SearchList.size () != 0 ) { //一番最初に検出したポイント名はデフォルトで追加
			for ( int i = 0; i < SearchList.size (); i++ ) { //すべてのポイントの名前に関して総当たりで検索をかける
				if ( SearchList.get ( i ).getName ().equals ( PitDupName ) ) {
					findExistingName = 1; //既存の名前があれば
					break;
				}
				else findExistingName = -1; //なければ-1を返す
			}
		}
		return findExistingName;
	}
	
	private int RouteDuplicationSearch( String RteDupNameIn, String RteDupNameOut ) { //道名の重複検索
		List < Routedata > SearchList = getRteList (); //既存のポイントリストを読み込む
		int findExistingName = -1; //既存名前判定フラグ
		if ( SearchList.size () != 0 ) { //一番最初に検出した道名はデフォルトで追加
			for ( int i = 0; i < SearchList.size (); i++ ) { //すべてのポイントの名前に関して総当たりで検索をかける
				if ( SearchList.get ( i ).getName ().equals ( RteDupNameIn + ", " + RteDupNameOut ) ) {
					findExistingName = 1; //既存の名前があれば
					break;
				}
				else findExistingName = -1; //なければ-1を返す
			}
		}
		return findExistingName;
	}
	
	//各読み込み関数
	private void reedingMapName ( String str, int CountLine) { //ライフサイクル名の読み込み
		String[] strAry = str.split ( " ", 2 );
		if ( getName () != null ) Flug ( 1, CountLine, str ); //すでに名前がはいっている段階でLifeCycleName行をよんだらエラーを出す
		else setName ( strAry[1] ); //行頭の"MapName "を除外し、ヌルの場合名前へ代入
	}
	
	private void reedingPointName ( String str, int CountLine ) { //ポイント名の読み込み
		String[] strAry = str.split( ", ", 5 );
		if (  PointDuplicationSearch ( strAry[1] ) == -1 )
			new Point ( this, strAry[1], Integer.parseInt( strAry[2] ), Integer.parseInt( strAry[3] ), Integer.parseInt( strAry[4] ) ); //行頭の"Point "を除外しポイント名前を取得し、ポイントをその名前と座標で作成
		else Flug ( 2, CountLine, str ); //エラー処理
	}
	
	private void reedingRouteName ( String str, int CountLine ) { //道名の読み込み
		String[] strAry = str.split( " ", 3 );
		String strRoute = strAry[1]; //行頭の"Route "を除外
		String[] RouteName = strRoute.split ( ",", 0 ); //配列RouteNameへ","で分割したものを代入
		if ( RouteName.length < 3 ) { //道は二つのポイントしかむすばないよ
			Point fromPoint = findNamedPoint ( PointList, RouteName[0], str, CountLine ); //検索をかけて代入
			Point toPoint = findNamedPoint ( PointList, RouteName[1], str, CountLine ); //検索をかけて代入				
			if ( fromPoint != null & toPoint != null ){ //nullでない場合に道を作る nullよけ
				if ( RouteDuplicationSearch( RouteName[0], RouteName[1] ) == -1 ) { //重複判定
					Routedata A = new Routedata (this, fromPoint, toPoint, Integer.parseInt( strAry[2] ) ); //道を作成
					fromPoint.setRoutedataOut ( A ); //RouteOutへ代入
					toPoint.setRoutedataIn ( A ); //RouteInへ代入
				}
				else Flug (3, CountLine, str); //重複道を発見の場合のエラー
			}
		}
		else Flug ( 6, CountLine, str ); //道の書式エラー排除
	}
	//ここまで読み込み関数
	
	public void ConnectfromFile ( String DataFolder ) { //引数にファイルの位置を投げるとファイルからライフサイクルを生成
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
					if ( str.startsWith ( "MapName" ) ) reedingMapName ( str, CountLine ); //ライフサイクル名の読み込み
					else if ( str.startsWith ( "Point" ) ) reedingPointName ( str, CountLine ); //ポイントの読み込み
					else if ( str.startsWith ( "Route" ) ) reedingRouteName ( str, CountLine ); //道の読み込み
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
