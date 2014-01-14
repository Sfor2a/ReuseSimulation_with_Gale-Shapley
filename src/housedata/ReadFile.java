package housedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ReadFile {
	
	private List < Housedata > HouseList = new ArrayList <> (); //�Ƃ̃��X�g 
	
	public void setHouseList ( Housedata Hus ) { //�n�E�X���X�g�Z�b�^�[
		HouseList.add ( Hus ); 
	}
	public List < Housedata > getHouseList () { //House���X�g���̂��̂̃Q�b�^�[
		return HouseList;
	}
	public Housedata getHouseData ( int num ) { //�w��ԍ��̉Ƃ̃Q�b�^�[
		Housedata Hse = HouseList.get ( num );
		return Hse;
	}
	
	
	
	
	//�G���[������
	private void Flug ( int num, int lineNum, String str ) { //�t���O�ɂ��G���[����
		switch ( num ) {
			case 1:
				System.out.print ( "Error: �����̃��C�t�T�C�N�������L�q����Ă��܂��A�ǂꂩ��ɓ��ꂵ�Ă�������" );
				break;
			case 2:
				System.out.print ( "Error: �������O�̃X�e�[�W�����łɑ��݂��Ă��܂�" );
				break;
			case 3:
				System.out.print ( "Error: �����o�H�̃p�X�����łɑ��݂��Ă��܂�" );
				break;
			case 4:
				System.out.print ( "Error: ���݂��Ȃ��X�e�[�W���Q�Ƃ��悤�Ƃ���p�X�ł��B�@�p�X�̖��O���C�����邩�A�Ԃ���m�F���Ă�������" );
				break;
			case 5:
				System.out.print ( "Error: �ǂݍ��݂Ɋ֌W�̂Ȃ��s���}������Ă��܂��B�@���̍s�͓ǂݔ�΂��܂��B�@�X�y���~�X�A�������`�F�b�N���Ă�������" );
				break;
			case 6:
				System.out.println ( "Error: �p�X�̋L�q�����ɖ�肪����܂� �Ȃ�����X�e�[�W�̓p�X��{�ɂ��A��ł�" );
				break;
			default:
				System.out.print ( "Error: ���̑��̃G���[���������Ă��܂��B�@�Y������s���m�F���邩�A�Ǘ��҂ɖ₢���킹�Ă�������" );
				break;
		}
		System.out.println ( "( �s�ԍ�" + lineNum + ": " + str + " )" ); //�G���[���b�Z�[�W���o������́A�K�����̃G���[�̂���s�����߂�
	}
	//��������
	
	private void reedingHouseName ( String str, int countLine ) { //�Ƃ̖��O��ǂݍ��񂾂�@�Ƃ������
		try {
			String StAry[] = str.split ( " ", 2 ); //"Housename "������
			String Housename= ".\\recycle\\housedata\\" + StAry[1] + "_data.txt";
			//�t�@�C�����[�_�[
			 File file = new File ( Housename );
			 BufferedReader br = new BufferedReader ( new FileReader ( file ) );
			//�����܂Ńt�@�C�����[�_�[
			 
			 String str1 = "0" ; //�ǂݎ��pString�֐� �������k���ɂ���Ǝ~�܂�i������O���j
			 int CountLine_1 = 0; //�s�ԍ����Z�ϐ��i�G���[�����p�j
			 String[] HouseAry = new String[2];
			
			//�ǂݍ��݃��\�b�h
			while ( str1 != null ) { //EOF��null�Ƃ��Č��o�A����܂ł͓ǂݍ��ݑ�����
				str1 = br.readLine (); CountLine_1++; //��s��ǂݍ��� �ǂݍ��ނ��тɎ���if���V�[�P���X�Ŕ���A�����ɍs�ԍ����Z
				if (str1 != null) { //�ǂݍ��ݍs��null�łȂ���΂��̃V�[�P���X�����s�A�s���Ŕ��ʂ���
					if ( str1.startsWith ( "Name " ) ) {
						String[] strAry = str1.split ( " ", 2 );
						HouseAry[0] = strAry[1];
					}
					else if ( str1.startsWith ( "Coin " ) ) {
						String[] strAry = str1.split ( " ", 2 );
						HouseAry[1] = strAry[1];		
					}
					else if ( str1.startsWith ( "//" ) ); //�R�����g�������o����Ɖ������Ȃ�
					else Flug ( 5, CountLine_1, str1 ); //��̕��͂�ǂݍ��ނƃG���[��\�����Ė���
				}			
			}
			//�����܂œǂݍ��݃��\�b�h
			br.close (); //�t�@�C�������
			new Housedata ( HouseAry[0], Integer.parseInt ( HouseAry[1] ), this ); //�Ƃ̍쐬
		} catch ( FileNotFoundException e ) { //��O����
			System.err.println ( e.getMessage () ); //�t�@�C���Ȃ��̗�O	
			System.exit ( 0 );
		} catch ( IOException e ) {
		    System.err.println ( e.getMessage () ); //IO�G���[��O
		    System.exit ( 0 );
		}
	}
	
	//�f�[�^�ǂݍ��݁@�������O����Ăяo�����
	public void CreatefromFile ( String DataFolder ) { //�����Ƀt�@�C���̈ʒu�𓊂���ƃt�@�C�����烉�C�t�T�C�N���𐶐� �����ɓn�����͉̂Ƃ̃��X�g
		try {
			//�t�@�C�����[�_�[
			 File file = new File ( DataFolder );
			 BufferedReader br = new BufferedReader ( new FileReader ( file ) );
			//�����܂Ńt�@�C�����[�_�[
			 
			 String str = "0" ; //�ǂݎ��pString�֐� �������k���ɂ���Ǝ~�܂�i������O���j
			 int CountLine = 0; //�s�ԍ����Z�ϐ��i�G���[�����p�j
			
			//�ǂݍ��݃��\�b�h
			while ( str != null ) { //EOF��null�Ƃ��Č��o�A����܂ł͓ǂݍ��ݑ�����
				str = br.readLine (); CountLine++; //��s��ǂݍ��� �ǂݍ��ނ��тɎ���if���V�[�P���X�Ŕ���A�����ɍs�ԍ����Z
				if (str != null) { //�ǂݍ��ݍs��null�łȂ���΂��̃V�[�P���X�����s�A�s���Ŕ��ʂ���
					if ( str.startsWith ( "HouseName " ) ) reedingHouseName ( str, CountLine ); //�Ƃ̖��O�̓ǂݍ���
					else if ( str.startsWith ( "//" ) ); //�R�����g�������o����Ɖ������Ȃ�
					else Flug ( 5, CountLine, str ); //��̕��͂�ǂݍ��ނƃG���[��\�����Ė���
				}			
			}
			//�����܂œǂݍ��݃��\�b�h
			
			br.close(); //�t�@�C�������			
		} catch ( FileNotFoundException e ) { //��O����
			System.err.println ( e.getMessage () ); //�t�@�C���Ȃ��̗�O	
			System.exit ( 0 );
		} catch ( IOException e ) {
		    System.err.println ( e.getMessage () ); //IO�G���[��O
		    System.exit ( 0 );
		}
	}

}
