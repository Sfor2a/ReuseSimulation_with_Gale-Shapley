package simulation;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteOutData {
	
	File file = new File ( ".\\recycle\\WriteOut\\WritedOut_data.csv" ); //�������ރt�@�C����
	
	public WriteOutData ( int SellNum, int BuyNum ) { //����������]�l��
		PrintWriter pw;
		try {
			pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file, true ) ) );
			pw.println ( ",���肽���Ƃ̐�," + SellNum+ ",���������Ƃ̐�," + BuyNum + "," );
			pw.close();
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //println�̗p��
	}
	public void WriteOut ( Score ReuseTarget ) {
		PrintWriter pw;
		try {
			pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file, true ) ) );
			pw.println ( "," + ReuseTarget.getBuyHouse().getName() + "," +ReuseTarget.getSellHouse().getName() + "," + ReuseTarget.getDist() + "," + ReuseTarget.getScoreforSell()+ "," );
			pw.close();
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //println�̗p��
	}
	public void Adjust( int SellNum, int BuyNum ) { //���������Q���l��
		PrintWriter pw;
		try {
			pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file, true ) ) );
			pw.println ( ",���ꂽ�Ƃ̐�," + SellNum+ ",�������Ƃ̐�," + BuyNum + "," );
			pw.println ( ",������肷���,�����,�����܂ł̋���,�������i," );
			pw.close();
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //println�̗p��
	}
	public void None() { //����Ȃ��̏����o��
		PrintWriter pw;
		try {
			pw = new PrintWriter (new BufferedWriter ( new FileWriter ( file, true ) ) );
			pw.println ( "����Ȃ�" );
			pw.close();
		} catch ( IOException e ) {
			System.err.println ( "File cannot be Writed." );
		} //println�̗p
	}
}

