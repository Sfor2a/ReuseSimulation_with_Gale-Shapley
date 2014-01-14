package simulation;

import housedata.HAdata;
import housedata.Housedata;

public class Exchange {

	public Exchange(Housedata A1, Housedata A2, HAdata HPA, int TermCount, CostAndRangeRankingList lowScore ) {
		HAdata F1 = HPA; //����̂��̉Ƌ�������Ă���
		int k = Integer.MAX_VALUE;
		for ( int i = 0; i < A2.getFurnitureList().size(); i++ ) {
			HAdata F2 = A2.getFurnitureList().get(i);
			if ( F2 == F1 )  k = i;
		}
		
		//�①�ɂ̐��𒲂ׂ�@�Q���ȏ�Ȃ����ȁ[�H
		int Count = 0;
		for ( int i = 0; i < A1.getFurnitureList().size(); i++ ) {
			if ( HPA.getName().equals( A1.getFurnitureList().get( i ).getName() ) ) Count += 1;
		}
		if ( k != Integer.MAX_VALUE && !( Count >= 2 ) ) { //�①�ɂ����݂��Ȃ��ꍇ���Ȃ��A���݂��Ă��A�①�ɂ��Q�ȏ�����Ă�ƂȂ��������O���
			int str6 = A2.getFurnitureList ().get ( k ).getTermValue();
			if ( F1.getExchangecount() <= TermCount ) { //���̉Ƌ�͂P�^�[���ɂP�x���������o���Ȃ���
				F1.setExchangecount ( F1.getExchangecount() + 1 );//�����񐔉��Z
				A2.getFurnitureList().remove ( k ); //���̉Ƌ�͏�����
				A1.getFurnitureList().add ( F1 ); //�Ƌ�ǉ������
				A1.setCoin ( A1.getCoin() - str6 );//A1�̉Ƃ���R�C�����ւ���
				A2.setCoin ( A2.getCoin() + str6 );//�R�C�����������
				System.out.println( A1.getName () + "������<->������" + A2.getName() );
				System.out.println( str6 + "������<->���炤" + F1.getName () );
				//System.out.print( lowScore.getCost() + ":");
				//System.out.println( lowScore.getRange() );
				
			}
		}
	}

}
