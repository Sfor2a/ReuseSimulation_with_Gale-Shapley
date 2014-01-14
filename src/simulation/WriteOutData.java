package simulation;

import housedata.HPAdata;
import housedata.ReadFile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteOutData {
	public WriteOutData() {
		
	}
	public void WriteOut ( CostAndRangeRanking CARR, int Term ) {
		File file = new File ( ".\\recycle\\WriteOut\\WritedOut" + Term + "_data.txt" ); //書き込むファイル名
		ReadFile RR = CARR.getRF();
		PrintWriter pw;
		try {
			pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file ) ) );
			pw.println( "=============" + Term + "ターム目=============" );
			for ( int i = 0; i < RR.getHouseList().size(); i++  ) {
				pw.println ( RR.getHouseList().get(i).getName() );
				pw.println ( "存在家電" );
				for ( int j = 0; j < RR.getHouseList().get(i).getFurnitureList().size(); j++ ) {
					HPAdata A1 = RR.getHouseList().get(i).getFurnitureList().get(j);
					pw.print ( "ID： " + A1.getID() + " " );
					pw.print ( "名前： " + A1.getName() + " " );
					pw.print ( "耐久度： " + A1.getDurability() + " " );
					pw.print ( "現在の値段" + A1.getTermValue() + " " );
					pw.print ( "交換回数" + A1.getExchangecount() + " " );
					pw.println();
				}
			}
			pw.close();
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //printlnの用意
		
		
		try {
			for ( int i = 0; i < RR.getHouseList().size(); i++  ) {
				for ( int j = 0; j < RR.getHouseList().get(i).getFurnitureList().size(); j++ ) {
					HPAdata A1 = RR.getHouseList().get(i).getFurnitureList().get(j);
					File file1 = new File ( ".\\recycle\\WriteOut\\WritedOutlog"+ A1.getID() +"_data.txt" ); //書き込むファイル名
					pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file1, true ) ) );
					pw.println( "=============" + Term + "ターム目=============" );
					pw.println( RR.getHouseList().get(i).getName() );
					pw.close();
				}
			}
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //printlnの用意
		
	}
}

