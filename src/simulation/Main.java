package simulation;

public class Main {

	public static void main(String[] args) {
		CostAndRangeRanking CRR = new CostAndRangeRanking ();
		
		for ( int i = 0; i<25; i++ ) {
			System.out.println(i+"ターン目");
			CRR.CARRCreate(i); //ランクを作り、交換し
			WriteOutData WOR = new WriteOutData();
			WOR.WriteOut ( CRR, i ); //結果を出す
		}
	}

}
