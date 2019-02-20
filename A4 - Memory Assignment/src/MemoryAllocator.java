import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class MemoryAllocator{
	public static void main(String[] args){
		BufferedReader b = null;
		HashMap<String,Vector<Integer>> hm = new HashMap<String,Vector<Integer>>();
		Vector<String> vectorString = new Vector<String>();
		try {
			String textString;
			int counter=0;
			b = new BufferedReader(new FileReader("statement.txt"));
			while ((textString = b.readLine()) != null) {
				if (textString.equals(""))
					continue;
				counter++;
				String[] l = textString.trim().replaceAll("[^a-zA-Z0-9 ]"," ").toLowerCase().split("\\s+");
				for (int i=0;i<l.length;i++){
					if (hm.get(l[i])==null){
						Vector<Integer> v = new Vector<Integer>();
						v.add(counter);
						hm.put(l[i],v);
						vectorString.add(l[i]);
					}
					else{
						hm.get(l[i]).add(counter);
					}	
				}
			}
			int[][] arr = new int[vectorString.size()][vectorString.size()];
			for (int k=0;k<arr.length;k++){
				for (int j=0;j<vectorString.size();j++){
					arr[k][j] = 0;
				}
			}
			int count = 0;
			for (int i=0;i<vectorString.size();i++){
				for (int j=0;j<=i;j++){
					Vector<Integer> ai = hm.get(vectorString.get(i));
					int h=0;
					for (int l=ai.get(0);l<=ai.get(ai.size()-1);l++){
						if (arr[j][l-1]!=0){
							h++;	
							break;
						}	
					}
					if (h==0){
						int m=0;
						for (int s=0;s<vectorString.size();s++){
							if (arr[j][s]!=0){
								m++;
								break;
							}
						}
						if (m==0)
							count++;
						for (int l=ai.get(0);l<=ai.get(ai.size()-1);l++){
							arr[j][l-1]=1;
						}
						break;
					}

				}
			}
			System.out.println("Number of minimum memory locations : "+count);
			// Iterator<String> it = hm.keySet().iterator();
			// while (it.hasNext()){
			// 	String tmp = it.next();
			// 	System.out.println("Key : "+tmp);
			// 	Iterator<Integer> ist = hm.get(tmp).iterator();
			// 	System.out.print("Value : ");
			// 	while (ist.hasNext()){
			// 		Integer tsp = ist.next();
			// 		System.out.print(tsp+"  ");
			// 	}
			// 	System.out.println("");
			// }
 		}
		catch (IOException e) {
			System.out.println("Sorry, the file 'statement.txt' not found!");
		}	
	}
}		