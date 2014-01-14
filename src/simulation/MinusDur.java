package simulation;

import java.util.ArrayList;
import java.util.List;

import newbuy.buybuy;
import housedata.HAdata;
import housedata.Housedata;


public class MinusDur {	
	List < HAdata > doubleHPAList = new ArrayList <> (); //�_�u��Ǘ����X�g
	
	//�Q�b�^�[�Z�b�^�[
	private List < HAdata > getdoubleList() {
		return doubleHPAList;
	}
	private void setdoubleList ( HAdata HPA ) {
		doubleHPAList.add ( HPA );
	}
	
	
	public MinusDur () {
	}
	
	public void Minus ( Main RF ) {
		int HouseNumber = RF.getHouseList ().size (); //�Ƃ̐�
		for ( int i = 0; i< HouseNumber; i++ ) { //�S���̉Ƃɑ�������
			Housedata TAISYOUHOUSE = RF.getHouseList ().get ( i ); //���̉Ƌ�������Ă����
			List < HAdata > iFurnitureList = TAISYOUHOUSE.getHAList (); //��������������
			for ( int j = 0; j < iFurnitureList.size (); j++ ) { //���̉Ƃ̉Ƌ�ɑ�������
				HAdata TaisyouHPA = iFurnitureList.get ( j );//�ΏۉƋ�
				MinusDurability ( TaisyouHPA ); //�ϋv�x���Y���\�b�h
				NewHPABuy ( TaisyouHPA, RF, i, j, iFurnitureList, TAISYOUHOUSE ); //�ϋv�x0�̉Ƌ���������Ĕp�����āA���̕����w��(TaisyouHPA���Y�����ǂ������ׂ�
				int DA = doubleHPASearch ( TaisyouHPA, TAISYOUHOUSE, iFurnitureList ); //�_�u�茟��
				FireSale ( DA, TAISYOUHOUSE, iFurnitureList, TaisyouHPA, i, j, RF ); //�①�ɂ��_�u���Ă�Αϋv�x�̒Ⴂ����������ݒ肷��
				doubleHPAList.clear(); //��ƏI����_�u�����X�g�̓N���A����		
			}
		}
	}

	private void FireSale ( int j, Housedata tAISYOUHOUSE, List<HAdata> iFurnitureList, HAdata taisyouHPA, int k, int l, Main RF ) { //�ϋv�x�̒Ⴂ���𓊂����肵�܂�
		if ( j >= 2 ) { //�_�u���Ă�Ƃ��̂ݓ������\�b�h
			int Dur = Integer.MAX_VALUE; //�ϋv�x�����p�̃L�[
			int II = Integer.MAX_VALUE; //�L���p��i
			for ( int i = 0; i < getdoubleList().size(); i++ ) {
				if ( getdoubleList().get(i).getDurability()< Dur ) {
					Dur = getdoubleList().get(i).getDurability();
					II = i; //����w�ϋv�x���Ⴂ�ق���i���o���Ă���
				}
			}
			int ID = getdoubleList().get(II).getID(); //���̂������イ�ǂ̒Ⴂ���̂h�c�������Ă���
			for ( int i = 0; i < iFurnitureList.size(); i++ ) {
				if ( ID == iFurnitureList.get(i).getID() ) { //���Ȃ��h�c�̉Ɠd�𔭌��セ�̉Ɠd�͓�������ɏ���
					//System.out.println(Dur+ " + " + II + " + " + j + " + " + ID + " " + tAISYOUHOUSE.getName() );
					int Val = (int) (iFurnitureList.get(i).getTermValue() * 0.3); //1���œ�������
					RF.getHouseList ().get ( k ).getHAList ().get ( l ).setTermValue( Val );
				}
			}
		}
	}

	private int doubleHPASearch( HAdata taisyouHPA, Housedata tAISYOUHOUSE, List<HAdata> iFurnitureList ) { //�������ΏۉƋ�_�u���Ă��邩����
		String SearchKey = taisyouHPA.getName(); 
		//�_�u�肵��x����
		int j = 0; //���Ԃ�v�Z�p
		for ( int i = 0; i < iFurnitureList.size(); i++ ) {
			String SearchName = iFurnitureList.get(i).getName();
			if ( SearchKey.equals ( SearchName ) ) { //�ΏۉƋ�̖��O�Ɠ������̂������
				j += 1; //���Z����i�܂�taisyouHPA�Ɠ������m�n���Ȃ炸���邩��P�ȏ�ɂ͂Ȃ�
				setdoubleList ( iFurnitureList.get(i) ); //�_�u���Ă�΃_�u�胊�X�g�ɒǉ�
			}
		}
		return j;
	}

	private void NewHPABuy( HAdata tAISYOU, Main RF, int i, int j, List < HAdata > iFurnitureList, Housedata TAISYOUHOUSE ) { //�V�K�w��
		int NowDur = tAISYOU.getDurability(); //�����Ώۂ̉Ƌ�̑ϋv�x
		if ( NowDur <= 0 ) {
			System.out.println( TAISYOUHOUSE.getName() + "��" + tAISYOU.getName () + "���ϋv�x0�ɂȂ�܂���" );
			System.out.println( "�V�K��" + tAISYOU.getName () + "���w�����܂�" );
			new buybuy ( RF, i, j ); //���ۂɍw������N���X�𓮂���
			iFurnitureList.remove ( j ); //�ϋv�x�O�ɂȂ��Ă�Ƌ�͔p������
		}
	}

	private void MinusDurability ( HAdata TaisyouHPA ) { //�ϋv�x���炷��
		if ( TaisyouHPA.getDurability () > 0 ) { //�ϋv�x��0���傫���Ƃ����� 
			int NowDur = TaisyouHPA.getDurability () - TaisyouHPA.getMinusDur (); //�ϋv�x�����炷
			Double onepValue = (double) (TaisyouHPA.getMaxValue () / 100); //1%�A�^���̃R�X�g�����߂�
			int NowValue = (int) (onepValue * NowDur); //�ϋv�n�������Ēl�i�ɂ���
			TaisyouHPA.setDurability ( NowDur ); //�ϋv�x��
			TaisyouHPA.setTermValue ( NowValue ); //���i��ݒ�
		}
	}
}


