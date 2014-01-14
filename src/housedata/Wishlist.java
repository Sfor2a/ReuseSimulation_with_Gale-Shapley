package housedata;

import java.util.ArrayList;
import java.util.List;

public class Wishlist extends HouseElements {
	private static int WLNum = 0;
	private List < WishValueList > WishValList = new ArrayList <> (); //�ϋv�x�Ɖ��i�̃��X�g
	
	//�Q�b�^�[
	public List< WishValueList > getWishValueList () {
		return WishValList;
	}
	//�Z�b�^�[
	public void setValuelist ( WishValueList WVL ) {
		WishValList.add ( WVL );
	}
	
	public Wishlist ( String nam, int Dur, int cost, Housedata HD, String spec ) { //�E�B�b�V�����X�g�������
		ID = WLNum++;
		setName ( nam ); //�E�B�b�V�����X�g�ɂ����̖��O���Ȃ���ΐV���������̖��O�����
		new WishValueList ( this, Dur, cost, spec );//n%�ȏ�̉��l�ŉ��~�Ɣ��f
		HD.setWishList ( this );
	}	
}