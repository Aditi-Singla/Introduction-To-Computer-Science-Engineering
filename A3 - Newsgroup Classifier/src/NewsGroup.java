import java.util.*;
public class NewsGroup{
	String name;
	int no_of_docs;
	float prior;
	String text;
	int denom;
	HashMap<String,Double> wordList;
	public NewsGroup(String n){
		name = n;
		no_of_docs = 0;
		prior = 0;
		text = "";
		denom = 0;
		wordList = new HashMap<String,Double>();
	}
	public void addDoc(String s){
		no_of_docs ++ ;
		text = text + " " + s; 
	}

}