package simulation;

import housedata.HPAdata;
import housedata.Housedata;
import housedata.WishValueList;

public class ExchangeHPASearch {
	private int Cost = ( Integer.MAX_VALUE ) / 1000;
	private HPAdata HPA1;
	
	public ExchangeHPASearch ( Housedata A1, Housedata A2 ) {
		FurnitureSearch (A1, A2);
	}
	
	//ゲッター
	public int getCost() {
		return Cost;
	}
	public HPAdata getHPA() {
		return HPA1;
	}
	public void setCost ( int co ) {
		Cost = co;
	}
	public void setHPAdata ( HPAdata HPA ) {
		HPA1 = HPA;
	}
	
	
	private void ExchangeFurnitureS ( Housedata A1, Housedata A2, int i, int j, int k ) { //対象家具をスペックで絞れたら
		WishValueList A1WVL = A1.getWishList().get(i).getWishValueList().get(k);
		int str1 = A1WVL.getDurability(); //自分の希望耐久度帯
		int str2 = A2.getFurnitureList ().get ( j ).getDurability(); //相手の耐久度
		int str3 = A1WVL.getCost(); //そのときの希望価格 
		int str4 = A2.getFurnitureList ().get ( j ).getTermValue(); //相手の現在家具価格
		if ( str2 >= str1 && str3 >= str4 ) {
			setHPAdata ( A2.getFurnitureList ().get ( j ) );	
			setCost ( str4 );
		}
	}
	
	private void FurnitureSpec ( Housedata A1, Housedata A2, int i, int j ) { //交換の家具の選定
		for ( int k = 0; k < A1.getWishList ().get ( i ).getWishValueList ().size (); k++ ) { //その家電のウィッシュリストの中から
			WishValueList A1WVL = A1.getWishList().get(i).getWishValueList().get(k);
			int str1 = Integer.parseInt ( A1WVL.getSpec() ); //そのスペックをもってくる
			int str2 = Integer.parseInt ( A2.getFurnitureList().get(j).getSpec() ); //一個ずつあいてのスペックをもってきて
			int maxspecstr1 = ( int ) ( str1 * 1.1 );
			int minspecstr1 = ( int ) ( str1 * 0.9 );
			if ( maxspecstr1 > str2 && minspecstr1 < str2 ) { //相手の家具のスペックと自分のものとのスペックの差が１割以内なら
				ExchangeFurnitureS ( A1, A2, i, j, k );
			}
		}
	}
	
	private void FurnitureSearch ( Housedata A1, Housedata A2 ) { //家具の総当たりメソッド
		for ( int i = 0; i < A1.getWishList().size(); i++ ) {
			for ( int j = 0; j < A2.getFurnitureList().size(); j++ ) {
				String str1 = A1.getWishList ().get ( i ).getName ();
				String str2 = A2.getFurnitureList().get(j).getName();
				if ( str1.equals ( str2 ) ) FurnitureSpec( A1, A2, i, j );
			}
		}

	}
}
