package simulation;

import housedata.HAdata;
import housedata.Housedata;
import housedata.HAsWishValue;

public class ExchangeHPASearch {
	private int Cost = ( Integer.MAX_VALUE ) / 1000;
	private HAdata HPA1;
	
	public ExchangeHPASearch ( Housedata A1, Housedata A2 ) {
		FurnitureSearch (A1, A2);
	}
	
	//�Q�b�^�[
	public int getCost() {
		return Cost;
	}
	public HAdata getHPA() {
		return HPA1;
	}
	public void setCost ( int co ) {
		Cost = co;
	}
	public void setHPAdata ( HAdata HPA ) {
		HPA1 = HPA;
	}
	
	
	private void ExchangeFurnitureS ( Housedata A1, Housedata A2, int i, int j, int k ) { //�ΏۉƋ���X�y�b�N�ōi�ꂽ��
		HAsWishValue A1WVL = A1.getWishList().get(i).getWishValueList().get(k);
		int str1 = A1WVL.getDurability(); //�����̊�]�ϋv�x��
		int str2 = A2.getFurnitureList ().get ( j ).getDurability(); //����̑ϋv�x
		int str3 = A1WVL.getCost(); //���̂Ƃ��̊�]���i 
		int str4 = A2.getFurnitureList ().get ( j ).getTermValue(); //����̌��݉Ƌ�i
		if ( str2 >= str1 && str3 >= str4 ) {
			setHPAdata ( A2.getFurnitureList ().get ( j ) );	
			setCost ( str4 );
		}
	}
	
	private void FurnitureSpec ( Housedata A1, Housedata A2, int i, int j ) { //�����̉Ƌ�̑I��
		for ( int k = 0; k < A1.getWishList ().get ( i ).getWishValueList ().size (); k++ ) { //���̉Ɠd�̃E�B�b�V�����X�g�̒�����
			HAsWishValue A1WVL = A1.getWishList().get(i).getWishValueList().get(k);
			int str1 = Integer.parseInt ( A1WVL.getSpec() ); //���̃X�y�b�N�������Ă���
			int str2 = Integer.parseInt ( A2.getFurnitureList().get(j).getSpec() ); //��������ẴX�y�b�N�������Ă���
			int maxspecstr1 = ( int ) ( str1 * 1.1 );
			int minspecstr1 = ( int ) ( str1 * 0.9 );
			if ( maxspecstr1 > str2 && minspecstr1 < str2 ) { //����̉Ƌ�̃X�y�b�N�Ǝ����̂��̂Ƃ̃X�y�b�N�̍����P���ȓ��Ȃ�
				ExchangeFurnitureS ( A1, A2, i, j, k );
			}
		}
	}
	
	private void FurnitureSearch ( Housedata A1, Housedata A2 ) { //�Ƌ�̑������胁�\�b�h
		for ( int i = 0; i < A1.getWishList().size(); i++ ) {
			for ( int j = 0; j < A2.getFurnitureList().size(); j++ ) {
				String str1 = A1.getWishList ().get ( i ).getName ();
				String str2 = A2.getFurnitureList().get(j).getName();
				if ( str1.equals ( str2 ) ) FurnitureSpec( A1, A2, i, j );
			}
		}

	}
}
