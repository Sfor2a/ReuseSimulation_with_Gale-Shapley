package housedata;

import java.util.ArrayList;
import java.util.List;

public class HousesWish extends HouseElements {
	private static int WLNum = 0;
	private List < WishvValue > WishValList = new ArrayList <> (); //�ϋv�x�Ɖ��i�̃��X�g
	
	//�Q�b�^�[
	public List< WishvValue > getWishValueList () {
		return WishValList;
	}
	//�Z�b�^�[
	public void setValuelist ( WishvValue WVL ) {
		WishValList.add ( WVL );
	}
	
	public HousesWish ( String nam, int Dur, int cost, Housedata HD, String spec ) { //�E�B�b�V�����X�g�������
		ID = WLNum++;
		setName ( nam ); //�E�B�b�V�����X�g�ɂ����̖��O���Ȃ���ΐV���������̖��O�����
		new WishvValue ( this, Dur, cost, spec );//n%�ȏ�̉��l�ŉ��~�Ɣ��f
		HD.setWishList ( this );
	}	
}