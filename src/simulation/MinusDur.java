package simulation;

import java.util.ArrayList;
import java.util.List;

import newbuy.buybuy;
import housedata.HPAdata;
import housedata.Housedata;
import housedata.ReadFile;


public class MinusDur {	
	List < HPAdata > doubleHPAList = new ArrayList <> (); //ダブり管理リスト
	
	//ゲッターセッター
	private List < HPAdata > getdoubleList() {
		return doubleHPAList;
	}
	private void setdoubleList ( HPAdata HPA ) {
		doubleHPAList.add ( HPA );
	}
	
	
	public MinusDur () {
	}
	
	public void Minus ( ReadFile RF ) {
		int HouseNumber = RF.getHouseList ().size (); //家の数
		for ( int i = 0; i< HouseNumber; i++ ) { //全部の家に総当たり
			Housedata TAISYOUHOUSE = RF.getHouseList ().get ( i ); //その家具が所属している家
			List < HPAdata > iFurnitureList = TAISYOUHOUSE.getFurnitureList (); //長文書き換えｗ
			for ( int j = 0; j < iFurnitureList.size (); j++ ) { //その家の家具に総当たり
				HPAdata TaisyouHPA = iFurnitureList.get ( j );//対象家具
				MinusDurability ( TaisyouHPA ); //耐久度減産メソッド
				NewHPABuy ( TaisyouHPA, RF, i, j, iFurnitureList, TAISYOUHOUSE ); //耐久度0の家具をさがして廃棄して、その分を購入(TaisyouHPAが該当かどうか調べる
				int DA = doubleHPASearch ( TaisyouHPA, TAISYOUHOUSE, iFurnitureList ); //ダブり検索
				FireSale ( DA, TAISYOUHOUSE, iFurnitureList, TaisyouHPA, i, j, RF ); //冷蔵庫がダブってれば耐久度の低い方を安売り設定する
				doubleHPAList.clear(); //作業終了後ダブリリストはクリアする		
			}
		}
	}

	private void FireSale ( int j, Housedata tAISYOUHOUSE, List<HPAdata> iFurnitureList, HPAdata taisyouHPA, int k, int l, ReadFile RF ) { //耐久度の低い方を投げ売りします
		if ( j >= 2 ) { //ダブってるときのみ動くメソッド
			int Dur = Integer.MAX_VALUE; //耐久度検索用のキー
			int II = Integer.MAX_VALUE; //記憶用のi
			for ( int i = 0; i < getdoubleList().size(); i++ ) {
				if ( getdoubleList().get(i).getDurability()< Dur ) {
					Dur = getdoubleList().get(i).getDurability();
					II = i; //より一層耐久度が低いほうのiを覚えておく
				}
			}
			int ID = getdoubleList().get(II).getID(); //そのたいきゅうどの低い方のＩＤをもってくる
			for ( int i = 0; i < iFurnitureList.size(); i++ ) {
				if ( ID == iFurnitureList.get(i).getID() ) { //おなじＩＤの家電を発見後その家電は投げ売りに処す
					//System.out.println(Dur+ " + " + II + " + " + j + " + " + ID + " " + tAISYOUHOUSE.getName() );
					int Val = (int) (iFurnitureList.get(i).getTermValue() * 0.3); //1割で投げうる
					RF.getHouseList ().get ( k ).getFurnitureList ().get ( l ).setTermValue( Val );
				}
			}
		}
	}

	private int doubleHPASearch( HPAdata taisyouHPA, Housedata tAISYOUHOUSE, List<HPAdata> iFurnitureList ) { //投げた対象家具がダブっているか検索
		String SearchKey = taisyouHPA.getName(); 
		//ダブりしらベンぞ
		int j = 0; //だぶり計算用
		for ( int i = 0; i < iFurnitureList.size(); i++ ) {
			String SearchName = iFurnitureList.get(i).getName();
			if ( SearchKey.equals ( SearchName ) ) { //対象家具の名前と同じものがあれば
				j += 1; //加算する（つまりtaisyouHPAと同じモノハかならずあるから１以上にはなる
				setdoubleList ( iFurnitureList.get(i) ); //ダブってればダブりリストに追加
			}
		}
		return j;
	}

	private void NewHPABuy( HPAdata tAISYOU, ReadFile RF, int i, int j, List < HPAdata > iFurnitureList, Housedata TAISYOUHOUSE ) { //新規購入
		int NowDur = tAISYOU.getDurability(); //検索対象の家具の耐久度
		if ( NowDur <= 0 ) {
			System.out.println( TAISYOUHOUSE.getName() + "の" + tAISYOU.getName () + "が耐久度0になりました" );
			System.out.println( "新規に" + tAISYOU.getName () + "を購入します" );
			new buybuy ( RF, i, j ); //実際に購入するクラスを動かす
			iFurnitureList.remove ( j ); //耐久度０になってる家具は廃棄する
		}
	}

	private void MinusDurability ( HPAdata TaisyouHPA ) { //耐久度減らすよ
		if ( TaisyouHPA.getDurability () > 0 ) { //耐久度が0より大きいときだけ 
			int NowDur = TaisyouHPA.getDurability () - TaisyouHPA.getMinusDur (); //耐久度を減らす
			Double onepValue = (double) (TaisyouHPA.getMaxValue () / 100); //1%アタリのコストを求める
			int NowValue = (int) (onepValue * NowDur); //耐久地を書けて値段にする
			TaisyouHPA.setDurability ( NowDur ); //耐久度と
			TaisyouHPA.setTermValue ( NowValue ); //価格を設定
		}
	}
}


