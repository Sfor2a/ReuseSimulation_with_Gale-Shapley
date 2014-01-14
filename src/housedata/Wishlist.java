package housedata;

import java.util.ArrayList;
import java.util.List;

public class Wishlist extends HouseElements {
	private static int WLNum = 0;
	private List < WishValueList > WishValList = new ArrayList <> (); //耐久度と価格のリスト
	
	//ゲッター
	public List< WishValueList > getWishValueList () {
		return WishValList;
	}
	//セッター
	public void setValuelist ( WishValueList WVL ) {
		WishValList.add ( WVL );
	}
	
	public Wishlist ( String nam, int Dur, int cost, Housedata HD, String spec ) { //ウィッシュリストをつくるよ
		ID = WLNum++;
		setName ( nam ); //ウィッシュリストにかぐの名前がなければ新しくかぐの名前を作る
		new WishValueList ( this, Dur, cost, spec );//n%以上の価値で何円と判断
		HD.setWishList ( this );
	}	
}