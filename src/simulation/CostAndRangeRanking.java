package simulation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



import housedata.HPAdata;
import housedata.Housedata;
import housedata.ReadFile;
import mapdata.ConnectPoint;

public class CostAndRangeRanking {
	private List < CostAndRangeRankingList > CARRList = new ArrayList <> ();
	private HPAdata HPA2;
	private ReadFile RF;
	private int HouseNumber;
	private ConnectPoint CP;

	//ゲッター・セッター
	public List<CostAndRangeRankingList> getCARRList() {
		return CARRList;
	}
	public void setCARRList(CostAndRangeRankingList costAndRangeRankingList) {
		CARRList.add (costAndRangeRankingList );
	}
	public HPAdata getHPA2 () {
		return HPA2;
	}
	public void setHPA2 ( HPAdata HPAAA ) {
		HPA2 = HPAAA;
	}
	public ReadFile getRF () {
		return RF;
	}

	public CostAndRangeRanking () {
		//MDD = new MinusDur();
		RF = new ReadFile();
		RF.CreatefromFile( ".\\recycle\\Houselist.txt" ); //家具リストの作成
		HouseNumber = RF.getHouseList ().size (); //家の数
		CP = new ConnectPoint(); //地図モデル作成
		CP.ConnectfromFile(".\\recycle\\Maptokyo.txt"); //地図モデル作成
		
	}
	
	
	@SuppressWarnings("unchecked")
	public void CARRCreate ( int TermCount ) {		
		HouseSearchNoneandDouble ( HouseNumber, RF, CP ); //さきにダブりとなしの家の交換を実施
		CostAndRangeRankingList LowScore; //ハイスコアな家を用意しておく
		List < CostAndRangeRankingList > Narabikae = getCARRList();
		Collections.sort( Narabikae, new RankingComparator() ); //スコアの低い者が上にくる（つまりハイスコア
		for ( int i = 0; i < getCARRList().size(); i++ ) {
			LowScore = getCARRList().get(i);
			if ( LowScore.getHPA() != null ) {
				new Exchange ( LowScore.getHouseC1(), LowScore.getHouseC2(), LowScore.getHPA(), TermCount, LowScore ); //ハイスコアなもので交換するよ
			}
		}
		
		
		HouseSearch ( HouseNumber, RF, CP ); //次に通常同志で実施
		Narabikae = getCARRList();
		Collections.sort( Narabikae, new RankingComparator() ); //スコアの低い者が上にくる（つまりハイスコア
		for ( int i = 0; i < getCARRList().size(); i++ ) {
			LowScore = getCARRList().get(i);			
			if ( LowScore.getHPA() != null ) {
				new Exchange ( LowScore.getHouseC1(), LowScore.getHouseC2(), LowScore.getHPA(), TermCount, LowScore ); //ハイスコアなもので交換するよ
			}
		}
		MinusDur MDD = new MinusDur();
		MDD.Minus(RF);
	}
	private void HouseSearchNoneandDouble ( int HouseNumber, ReadFile RF, ConnectPoint CP  ) { //ナイ人とだぶってる人どうしとをまずやる
		for ( int i = 0; i < HouseNumber; i++ ) {
			Housedata A1 = null;
			Housedata A2 = null;
			if ( RF.getHouseList ().get ( i ).FurnitureList.size() == 0 ) A1 = RF.getHouseList ().get ( i ); //交換の主体
			for ( int j = 0; j < HouseNumber; j++ ) {
				if ( RF.getHouseList ().get ( i ).FurnitureList.size() >= 2 ) A2 = RF.getHouseList ().get ( j ); //相手先
			}
			if ( A1 != null && A2 != null  ) {
				int Range = MARange ( A1, A2, CP );
				int Cost = FurnitureCost ( A1, A2 );
				new CostAndRangeRankingList ( Cost, Range, A1, A2, this, getHPA2() );
			}
		}
	}
	private void HouseSearch ( int HouseNumber, ReadFile RF, ConnectPoint CP ) { //家の総当たりメソッド
		for ( int i = 0; i< HouseNumber; i++ ) { //交換商品の検索
			for ( int j = 0; j < HouseNumber; j++ ) {
				Housedata A1 = RF.getHouseList ().get ( i ); //交換の主体
				Housedata A2 = RF.getHouseList ().get ( j ); //相手先
				if ( A1 != A2 && A2 != A1 ) { //iとjが違うときだけ交換するよ
					int Range = MARange ( A1, A2, CP );
					int Cost = FurnitureCost ( A1, A2 );
					new CostAndRangeRankingList ( Cost, Range, A1, A2, this, getHPA2() );
				}
			}
		}
	}
	
 	private int MARange ( Housedata B1, Housedata B2, ConnectPoint CP ) { //最短距離を返すよ あるいみただのゲッター
		int PointNum = CP.getPitList().size(); //地図モデルの行列
		
		int[][] RouteArray = new int [PointNum][PointNum]; //地図行列の初期化
		for ( int i=0; i< PointNum; i++ ) {
			for ( int j =0; j< PointNum; j++) {
				RouteArray[i][j] = 0;
			}
		}
		
		String Name1 = B1.getName(); //家の名前を取得
		String Name2 = B2.getName();
		
		for ( int i = 0; i < CP.getPitList().size(); i++ ) { //家の名前からルートを調べる
			if ( Name1.equals ( CP.getPitList().get(i).getName() ) ) {
				for ( int j = 0; j < CP.getPitList().size(); j++ ) {
					if ( Name2.equals ( CP.getPitList().get(j).getName() ) ) {
						MinimumAccess MA = new MinimumAccess ( RouteArray, PointNum, i, j, CP ); //行列とつなぎ先をいれて、距離とルートを返す
						return MA.getLength();
					}
				}
			}
		}
		return 0;
	}
	
	private int FurnitureCost ( Housedata A1, Housedata A2 ) { //家具のコストを返すよ
		ExchangeHPASearch EFS = new ExchangeHPASearch ( A1, A2 );
		setHPA2 ( EFS.getHPA() );
		return EFS.getCost();
	}
}


class CostAndRangeRankingList {
	private int Cost;
	private int Range;
	private Housedata C1;
	private Housedata C2;
	private int Score;
	private HPAdata HPA1;
	
	public int getCost() {
		return Cost;
	}
	public void setCost(int cost) {
		Cost = cost;
	}
	public void setScore(int score) {
		Score = score;
	}
	public int getRange() {
		return Range;
	}
	public int getScore() {
		return Score;
	}
	public HPAdata getHPA () {
		return HPA1;
	}
	public void setRange(int range) {
		Range = range;
	}
	public Housedata getHouseC1() {
		return C1;
	}
	public Housedata getHouseC2() {
		return C2;
	}
	public void setA1(Housedata a1) {
		C1 = a1;
	}
	public void setA2(Housedata a2) {
		C2 = a2;
		}
	public void setHPA ( HPAdata HPAA ) {
		HPA1 = HPAA;
	}
	
	public CostAndRangeRankingList ( int Co, int Ra, Housedata a1, Housedata a2, CostAndRangeRanking CARR, HPAdata HPA3 ) {
		setCost ( Co );
		setRange ( Ra );
		setA1 ( a1 );
		setA2 ( a2 );
		setScore ( Co * 1 );
		setHPA( HPA3 );
		CARR.setCARRList( this );
	}
}

@SuppressWarnings("rawtypes")
class RankingComparator implements java.util.Comparator {
	public int compare ( Object s, Object t ) {
		return ( ( CostAndRangeRankingList ) s ).getScore() - ( ( CostAndRangeRankingList ) t ).getScore();
	}
}
