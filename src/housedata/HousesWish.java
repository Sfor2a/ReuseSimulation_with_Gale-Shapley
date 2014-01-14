package housedata;

import java.util.ArrayList;
import java.util.List;

public class HousesWish extends HouseElements {
	private static int WLNum = 0;
	private List < HAsWishValue > WishValList = new ArrayList <> (); //耐久度と価格のリスト
	
	//ゲッター
	public List< HAsWishValue > getWishValueList () {
		return WishValList;
	}
	//セッター
	public void setValuelist ( HAsWishValue WVL ) {
		WishValList.add ( WVL );
	}
	
	public HousesWish ( String nam, int Dur, int cost, Housedata HD, String spec ) { //ウィッシュリストをつくるよ
		ID = WLNum++;
		setName ( nam ); //ウィッシュリストにかぐの名前がなければ新しくかぐの名前を作る
		new HAsWishValue ( this, Dur, cost, spec );//n%以上の価値で何円と判断
		HD.setWishList ( this );
	}	
}