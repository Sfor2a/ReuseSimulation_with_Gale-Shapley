package simulation;

import housedata.HouseElements;
import housedata.Housedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mapdata.Point;
import mapdata.Routedata;

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
