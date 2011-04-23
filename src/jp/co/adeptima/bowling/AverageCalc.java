package jp.co.adeptima.bowling;



public class AverageCalc {
	public int score1;
	public int score2;

	public AverageCalc(int a, int b){
		score1 = a;
		score2 = b;
	}
	
	
	public int average(){
		return (score1+score2)/2;
		
	}
}
