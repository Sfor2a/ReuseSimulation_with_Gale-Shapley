package simulation;

public class Main {

	public static void main(String[] args) {
		CostAndRangeRanking CRR = new CostAndRangeRanking ();
		
		for ( int i = 0; i<25; i++ ) {
			System.out.println(i+"�^�[����");
			CRR.CARRCreate(i); //�����N�����A������
			WriteOutData WOR = new WriteOutData();
			WOR.WriteOut ( CRR, i ); //���ʂ��o��
		}
	}

}
