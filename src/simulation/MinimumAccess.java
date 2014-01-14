package simulation;

import mapdata.ConnectPoint;
import mapdata.Rangefinder;

public class MinimumAccess {
	int Length = 0; //距離
	
	public MinimumAccess ( int[][] RouteArray, int N, int Start, int Goal, ConnectPoint CP ) { //最短を求める
		
		new Rangefinder (CP, RouteArray); //最短経路を探索するための行列作成
		
		boolean Visited[] = new boolean[N]; //最短距離が確定した地点
		int Dist[] = new int[N]; //最初からそこまでの距離
		int prev[] = new int[N]; //まえにいた地点
		int nodes[] = new int[N]; //街番号
		for ( int i = 0; i < N; i++ ) {
			Visited[i] = false; //全ての街をいってない状態にする
			Dist[i] = Integer.MAX_VALUE; //全ての街までの距離を無限にする(2147483647
			nodes[i] = i; //街番号を代入			
		}
		Dist[Start] = 0; //最初の地点なので当然距離は0になる
		prev[Start] = Start; // 最初に選択した町の前の町は無いので、とりあえず自分の町を入れておく
		int pos = Start; //現在地を設定
		MinimumRouteSearch ( Visited, RouteArray, Dist, prev, pos, N, nodes ); //最小経路探索
		
		//イカコンストラクタ終了まで表示のコマンド、ぶっちゃけ一部のぞいてもおｋ　つまり経路の表示　距離自体はDistに格納されてるので問題なし
		@SuppressWarnings("unused")
		String str = Start + "から" + Goal + "までの最短ルートは";
		int node = prev[Goal];
		String track = Integer.toString ( Goal );
		for ( int i = 0; i < N; i++ ) {
			track += node;
			if ( node == Start ) break;
			node = prev[node];
		}
		for ( int i = track.length () - 1; i >= 0; i--) str += " => " + track.charAt ( i );
		//if ( node == Start ) System.out.println ( str );
		//else System.out.println ( "ルートなし" );
		//System.out.println ( "距離"+ Dist[Goal] );
		Length = Dist[Goal]; //距離参照用
	}
	
	public int getLength () {
		return Length;		
	}
	
	private void MinimumRouteSearch ( boolean[] Visited, int[][] RouteArray, int[] Dist, int[] prev, int pos, int N, int[] nodes ) {
		while (true) {
			Visited[pos] = true; //この処理をするとVisitedフラグを立てる
			for ( int x = 0; x < N; x++ ) {
				if ( Visited[x] ) continue; //すでにいった街ならこの処理はスルー
				if ( RouteArray[pos][x] > 0) { //経路がある場合この処理をやれ
					int d = Dist[pos] + RouteArray[pos][x]; //現在地から次の地点までの距離
					if ( d < Dist[x] ) { //今の地点 + 次の地点が元々入っていた距離より小さければ更新
						Dist[x] = d;
						prev[x] = pos;
					}	
				}
			}
			//まだ訪れていない町の中で、スタート地点からの距離が最小になる町を選ぶ
			int next = -1; //次の街探索フラグ
			int nextDist = Integer.MAX_VALUE;
			for ( int node: nodes ) { 
				if ( Visited[node] ) continue;
				if ( Dist[node] < nextDist ) {
					next = node;
					nextDist = Dist[node];
				}
			}
			if ( next == -1 ) break;
			else pos = next;			
		}
	}
}
