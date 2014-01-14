package simulation;

import housedata.HPAdata;
import housedata.Housedata;

public class Exchange {

	public Exchange(Housedata A1, Housedata A2, HPAdata HPA, int TermCount, CostAndRangeRankingList lowScore ) {
		HPAdata F1 = HPA; //相手のその家具をもってくる
		int k = Integer.MAX_VALUE;
		for ( int i = 0; i < A2.getFurnitureList().size(); i++ ) {
			HPAdata F2 = A2.getFurnitureList().get(i);
			if ( F2 == F1 )  k = i;
		}
		
		//冷蔵庫の数を調べる　２こ以上ないかなー？
		int Count = 0;
		for ( int i = 0; i < A1.getFurnitureList().size(); i++ ) {
			if ( HPA.getName().equals( A1.getFurnitureList().get( i ).getName() ) ) Count += 1;
		}
		if ( k != Integer.MAX_VALUE && !( Count >= 2 ) ) { //冷蔵庫が存在しない場合しない、存在しても、冷蔵庫を２個以上もってる家なら交換から外れる
			int str6 = A2.getFurnitureList ().get ( k ).getTermValue();
			if ( F1.getExchangecount() <= TermCount ) { //その家具は１タームに１度しか交換出来ないよ
				F1.setExchangecount ( F1.getExchangecount() + 1 );//交換回数加算
				A2.getFurnitureList().remove ( k ); //その家具は消える
				A1.getFurnitureList().add ( F1 ); //家具が追加される
				A1.setCoin ( A1.getCoin() - str6 );//A1の家からコインがへって
				A2.setCoin ( A2.getCoin() + str6 );//コインがたされる
				System.out.println( A1.getName () + "交換元<->交換先" + A2.getName() );
				System.out.println( str6 + "払って<->もらう" + F1.getName () );
				//System.out.print( lowScore.getCost() + ":");
				//System.out.println( lowScore.getRange() );
				
			}
		}
	}

}
