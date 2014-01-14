package simulation;

import housedata.HAdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteOutData {
	public WriteOutData() {
		
	}
	public void WriteOut ( CostAndRangeRanking CARR, int Term ) {
		File file = new File ( ".\\recycle\\WriteOut\\WritedOut" + Term + "_data.txt" ); //�������ރt�@�C����
		Main RR = CARR.getRF();
		PrintWriter pw;
		try {
			pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file ) ) );
			pw.println( "=============" + Term + "�^�[����=============" );
			for ( int i = 0; i < RR.getHouseList().size(); i++  ) {
				pw.println ( RR.getHouseList().get(i).getName() );
				pw.println ( "���݉Ɠd" );
				for ( int j = 0; j < RR.getHouseList().get(i).getFurnitureList().size(); j++ ) {
					HAdata A1 = RR.getHouseList().get(i).getFurnitureList().get(j);
					pw.print ( "ID�F " + A1.getID() + " " );
					pw.print ( "���O�F " + A1.getName() + " " );
					pw.print ( "�ϋv�x�F " + A1.getDurability() + " " );
					pw.print ( "���݂̒l�i" + A1.getTermValue() + " " );
					pw.print ( "������" + A1.getExchangecount() + " " );
					pw.println();
				}
			}
			pw.close();
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //println�̗p��
		
		
		try {
			for ( int i = 0; i < RR.getHouseList().size(); i++  ) {
				for ( int j = 0; j < RR.getHouseList().get(i).getFurnitureList().size(); j++ ) {
					HAdata A1 = RR.getHouseList().get(i).getFurnitureList().get(j);
					File file1 = new File ( ".\\recycle\\WriteOut\\WritedOutlog"+ A1.getID() +"_data.txt" ); //�������ރt�@�C����
					pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file1, true ) ) );
					pw.println( "=============" + Term + "�^�[����=============" );
					pw.println( RR.getHouseList().get(i).getName() );
					pw.close();
				}
			}
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //println�̗p��
		
	}
}

